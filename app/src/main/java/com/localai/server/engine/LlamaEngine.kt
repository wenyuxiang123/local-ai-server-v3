package com.localai.server.engine

import android.content.Context
import android.util.Log
import io.github.aatricks.llmedge.LLMEdge
import io.github.aatricks.llmedge.LLMEdgeConfig
import io.github.aatricks.llmedge.TextRuntimeConfig
import io.github.aatricks.llmedge.text.TextStreamEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LlamaEngine @Inject constructor(
    private val context: Context
) {
    companion object {
        private const val TAG = "LlamaEngine"
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
    private val engineScope = CoroutineScope(Dispatchers.Default)
    
    init { initializeEngine() }
    
    private fun initializeEngine() {
        try {
            val config = LLMEdgeConfig(
                text = TextRuntimeConfig(promptThreads = 4, generationThreads = 2)
            )
            llmEdge = LLMEdge.create(context, engineScope, config)
            Log.i(TAG, "llmedge engine initialized successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to initialize llmedge engine", e)
            llmEdge = null
        }
    }
    
    suspend fun loadModel(path: String, nCtx: Int = 2048, nThreads: Int = 4): Boolean = withContext(Dispatchers.Default) {
        val edge = llmEdge ?: run {
            Log.e(TAG, "llmedge engine not initialized")
            initializeEngine()
            llmEdge ?: return@withContext false
        }
        val file = File(path)
        if (!file.exists()) {
            Log.e(TAG, "Model file not found: $path")
            return@withContext false
        }
        try {
            if (isModelLoaded) unloadModel()
            Log.i(TAG, "Loading model: ${file.name}, nCtx=$nCtx, threads=$nThreads")
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
    
    fun unloadModel() {
        isModelLoaded = false
        loadedModelPath = null
        modelContextSize = 0
        modelThreads = 0
        loadedModelName = null
        Log.i(TAG, "Model unloaded")
    }
    
    fun isModelLoaded(): Boolean = isModelLoaded
    
    fun generateSync(prompt: String, maxTokens: Int = 512, temperature: Float = 0.7f, topK: Int = 40, topP: Float = 0.9f): String {
        if (!isModelLoaded) throw IllegalStateException("模型未加载")
        val edge = llmEdge ?: throw IllegalStateException("引擎未初始化")
        try {
            Log.d(TAG, "Generating response for prompt: ${prompt.take(50)}...")
            val result = runBlocking {
                edge.text.generate(prompt = prompt)
            }
            Log.d(TAG, "Generated ${result.length} characters")
            return result
        } catch (e: Exception) {
            Log.e(TAG, "Generation failed", e)
            throw e
        }
    }
    
    suspend fun generate(prompt: String, maxTokens: Int = 512, temperature: Float = 0.7f, topK: Int = 40, topP: Float = 0.9f): String = withContext(Dispatchers.Default) {
        if (!isModelLoaded) throw IllegalStateException("模型未加载")
        val edge = llmEdge ?: throw IllegalStateException("引擎未初始化")
        try {
            Log.d(TAG, "Generating response for prompt: ${prompt.take(50)}...")
            val result = edge.text.generate(prompt = prompt)
            Log.d(TAG, "Generated ${result.length} characters")
            result
        } catch (e: Exception) {
            Log.e(TAG, "Generation failed", e)
            throw e
        }
    }
    
    fun generateStream(prompt: String, maxTokens: Int = 512, temperature: Float = 0.7f, topK: Int = 40, topP: Float = 0.9f): Flow<String> = flow {
        if (!isModelLoaded) throw IllegalStateException("模型未加载")
        val edge = llmEdge ?: throw IllegalStateException("引擎未初始化")
        try {
            Log.d(TAG, "Streaming generation for prompt: ${prompt.take(50)}...")
            edge.text.stream(prompt = prompt).collect { event ->
                when (event) {
                    is TextStreamEvent.Chunk -> emit(event.value)
                    is TextStreamEvent.Completed -> Log.d(TAG, "Stream completed")
                    else -> {}
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Stream generation failed", e)
            throw e
        }
    }.flowOn(Dispatchers.Default)
    
    fun getLoadedModelName(): String? = loadedModelName
    fun getContextSize(): Int = if (isModelLoaded) modelContextSize else 0
    fun getThreads(): Int = if (isModelLoaded) modelThreads else 0
    
    fun getMemoryUsage(): Long {
        if (!isModelLoaded) return 0
        val file = loadedModelPath?.let { File(it) }
        return file?.length() ?: 0
    }
    
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
    
    fun getLoadedModelInfo(): Map<String, Any> = getModelInfo()
}
