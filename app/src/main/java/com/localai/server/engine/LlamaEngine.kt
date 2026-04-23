package com.localai.server.engine

import android.content.Context
import android.util.Log
import io.github.aatricks.llmedge.LLMEdge
import io.github.aatricks.llmedge.ModelSpec
import io.github.aatricks.llmedge.ChatRequest
import io.github.aatricks.llmedge.Options
import io.github.aatricks.llmedge.EarlyExitError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Llama推理引擎 - llmedge 实现
 * 
 * 基于 llama.cpp 的本地推理引擎，支持：
 * - mmap 模型加载（默认启用）
 * - 流式输出
 * - Kotlin 协程支持
 */
@Singleton
class LlamaEngine @Inject constructor(
    private val context: Context
) {
    companion object {
        private const val TAG = "LlamaEngine"
        
        /**
         * 初始化 llmedge 引擎
         */
        fun loadLibraries(): Boolean {
            return try {
                Log.i(TAG, "Initializing llmedge engine...")
                true
            } catch (e: Exception) {
                Log.e(TAG, "Failed to initialize llmedge", e)
                false
            }
        }
        
        fun getLoadError(): String? = null
    }
    
    private var llmEdge: LLMEdge? = null
    private var isModelLoaded = false
    private var loadedModelPath: String? = null
    private var modelContextSize = 0
    private var modelThreads = 0
    private var loadedModelName: String? = null
    
    init {
        initializeEngine()
    }
    
    /**
     * 初始化 llmedge 引擎
     */
    private fun initializeEngine() {
        try {
            llmEdge = LLMEdge.create(context, null)
            Log.i(TAG, "llmedge engine initialized successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to initialize llmedge engine", e)
            llmEdge = null
        }
    }
    
    /**
     * 加载模型
     * @param path 模型文件路径
     * @param nCtx 上下文窗口大小
     * @param nThreads 推理线程数
     */
    suspend fun loadModel(path: String, nCtx: Int = 2048, nThreads: Int = 4): Boolean = withContext(Dispatchers.Default) {
        val edge = llmEdge ?: run {
            Log.e(TAG, "llmedge engine not initialized")
            initializeEngine()
            llmEdge ?: return@withContext false
        }
        
        // 检查文件存在
        val file = File(path)
        if (!file.exists()) {
            Log.e(TAG, "Model file not found: $path")
            return@withContext false
        }
        
        try {
            // 卸载旧模型
            if (isModelLoaded) {
                unloadModel()
            }
            
            Log.i(TAG, "Loading model: ${file.name}, nCtx=$nCtx, threads=$nThreads")
            
            // 创建模型规范
            val modelSpec = ModelSpec.local(
                id = "local_model",
                name = file.name,
                file = file
            )
            
            // 加载模型（mmap 默认启用）
            edge.models.load(modelSpec, nCtx, nThreads)
            
            isModelLoaded = true
            loadedModelPath = path
            modelContextSize = nCtx
            modelThreads = nThreads
            loadedModelName = file.name
            
            Log.i(TAG, "Model loaded successfully: ${file.name}")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Failed to load model", e)
            isModelLoaded = false
            false
        }
    }
    
    /**
     * 卸载模型
     */
    fun unloadModel() {
        try {
            llmEdge?.models?.unload()
        } catch (e: Exception) {
            Log.e(TAG, "Error unloading model", e)
        }
        isModelLoaded = false
        loadedModelPath = null
        modelContextSize = 0
        modelThreads = 0
        loadedModelName = null
        Log.i(TAG, "Model unloaded")
    }
    
    /**
     * 检查模型是否已加载
     */
    fun isModelLoaded(): Boolean = isModelLoaded
    
    /**
     * 生成文本（完整返回）
     * @param prompt 输入提示
     * @param maxTokens 最大生成长度
     * @param temperature 温度参数
     * @param topK Top-K 采样
     * @param topP Top-P 采样
     */
    suspend fun generate(
        prompt: String,
        maxTokens: Int = 512,
        temperature: Float = 0.7f,
        topK: Int = 40,
        topP: Float = 0.9f
    ): String = withContext(Dispatchers.Default) {
        if (!isModelLoaded) {
            throw IllegalStateException("模型未加载")
        }
        
        val edge = llmEdge ?: throw IllegalStateException("引擎未初始化")
        
        try {
            Log.d(TAG, "Generating response for prompt: ${prompt.take(50)}...")
            
            val responseBuilder = StringBuilder()
            
            // 构建聊天请求
            val chatRequest = ChatRequest(
                messages = listOf(
                    io.github.aatricks.llmedge.Message(
                        role = io.github.aatricks.llmedge.Role.USER,
                        content = prompt
                    )
                ),
                options = Options(
                    maxTokens = maxTokens,
                    temperature = temperature,
                    topK = topK,
                    topP = topP
                )
            )
            
            // 流式生成并收集结果
            edge.llm.chat(chatRequest).collect { response ->
                response.content?.let { content ->
                    responseBuilder.append(content)
                }
            }
            
            val result = responseBuilder.toString()
            Log.d(TAG, "Generated ${result.length} characters")
            result
        } catch (e: EarlyExitError) {
            // 正常结束
            Log.d(TAG, "Generation completed normally")
            ""
        } catch (e: Exception) {
            Log.e(TAG, "Generation failed", e)
            throw e
        }
    }
    
    /**
     * 生成文本（流式返回）
     * @param prompt 输入提示
     * @param maxTokens 最大生成长度
     * @param temperature 温度参数
     * @param topK Top-K 采样
     * @param topP Top-P 采样
     */
    fun generateStream(
        prompt: String,
        maxTokens: Int = 512,
        temperature: Float = 0.7f,
        topK: Int = 40,
        topP: Float = 0.9f
    ): Flow<String> = flow {
        if (!isModelLoaded) {
            throw IllegalStateException("模型未加载")
        }
        
        val edge = llmEdge ?: throw IllegalStateException("引擎未初始化")
        
        try {
            Log.d(TAG, "Streaming generation for prompt: ${prompt.take(50)}...")
            
            // 构建聊天请求
            val chatRequest = ChatRequest(
                messages = listOf(
                    io.github.aatricks.llmedge.Message(
                        role = io.github.aatricks.llmedge.Role.USER,
                        content = prompt
                    )
                ),
                options = Options(
                    maxTokens = maxTokens,
                    temperature = temperature,
                    topK = topK,
                    topP = topP
                )
            )
            
            // 流式生成
            edge.llm.chat(chatRequest).collect { response ->
                response.content?.let { content ->
                    emit(content)
                }
            }
        } catch (e: EarlyExitError) {
            // 正常结束
            Log.d(TAG, "Stream completed normally")
        } catch (e: Exception) {
            Log.e(TAG, "Stream generation failed", e)
            throw e
        }
    }.flowOn(Dispatchers.Default)
    
    /**
     * 获取已加载模型名称
     */
    fun getLoadedModelName(): String? = loadedModelName
    
    /**
     * 获取上下文大小
     */
    fun getContextSize(): Int = if (isModelLoaded) modelContextSize else 0
    
    /**
     * 获取线程数
     */
    fun getThreads(): Int = if (isModelLoaded) modelThreads else 0
    
    /**
     * 获取内存使用量
     */
    fun getMemoryUsage(): Long {
        if (!isModelLoaded) return 0
        val file = loadedModelPath?.let { File(it) }
        return file?.length() ?: 0
    }
    
    /**
     * 获取模型信息
     */
    fun getModelInfo(): Map<String, Any> {
        return mapOf(
            "loaded" to isModelLoaded,
            "name" to (loadedModelName ?: "未加载"),
            "path" to (loadedModelPath ?: ""),
            "contextSize" to modelContextSize,
            "threads" to modelThreads,
            "memoryUsage" to getMemoryUsage(),
            "engine" to "llmedge (llama.cpp)",
            "mmap" to true
        )
    }
}
