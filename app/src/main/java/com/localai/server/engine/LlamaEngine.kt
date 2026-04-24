package com.localai.server.engine

import android.content.Context
import android.util.Log
import org.codeshipping.llamakotlin.LlamaModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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
                Log.i(TAG, "Initializing llama engine...")
                // llama-kotlin-android 自动加载 native 库
                true
            } catch (e: Exception) {
                Log.e(TAG, "Failed to initialize llama engine", e)
                false
            }
        }
        
        fun getLoadError(): String? = null
    }
    
    private var model: LlamaModel? = null
    private var isModelLoaded = false
    private var loadedModelPath: String? = null
    private var loadedModelName: String? = null
    
    init {
        Log.i(TAG, "LlamaEngine initialized")
    }
    
    suspend fun loadModel(path: String, nCtx: Int = 2048, nThreads: Int = 4): Boolean = withContext(Dispatchers.Default) {
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
            
            Log.i(TAG, "Loading model: ${file.name}, size=${file.length() / 1024 / 1024}MB")
            
            // 使用 llama-kotlin-android 加载模型
            model = LlamaModel.load(path) {
                contextSize = nCtx
                threads = nThreads
                threadsBatch = nThreads
                temperature = 0.7f
                topP = 0.9f
                topK = 40
                maxTokens = 512
                useMmap = true
                useMlock = false
            }
            
            isModelLoaded = true
            loadedModelPath = path
            loadedModelName = file.name
            
            Log.i(TAG, "Model loaded successfully: ${file.name}")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Failed to load model", e)
            isModelLoaded = false
            model = null
            false
        }
    }
    
    fun unloadModel() {
        try {
            model?.close()
        } catch (e: Exception) {
            Log.e(TAG, "Error closing model", e)
        }
        model = null
        isModelLoaded = false
        loadedModelPath = null
        loadedModelName = null
        Log.i(TAG, "Model unloaded")
    }
    
    fun isModelLoaded(): Boolean = isModelLoaded
    
    suspend fun generate(
        prompt: String, 
        maxTokens: Int = 512, 
        temperature: Float = 0.7f, 
        topK: Int = 40, 
        topP: Float = 0.9f
    ): String = withContext(Dispatchers.Default) {
        if (!isModelLoaded) throw IllegalStateException("模型未加载")
        val currentModel = model ?: throw IllegalStateException("引擎未初始化")
        
        try {
            Log.d(TAG, "Generating response for prompt: ${prompt.take(50)}...")
            val result = currentModel.generate(prompt)
            Log.d(TAG, "Generated ${result.length} characters")
            result
        } catch (e: Exception) {
            Log.e(TAG, "Generation failed", e)
            throw e
        }
    }
    
    fun generateStream(
        prompt: String, 
        maxTokens: Int = 512, 
        temperature: Float = 0.7f, 
        topK: Int = 40, 
        topP: Float = 0.9f
    ): Flow<String> {
        if (!isModelLoaded) throw IllegalStateException("模型未加载")
        val currentModel = model ?: throw IllegalStateException("引擎未初始化")
        
        Log.d(TAG, "Streaming generation for prompt: ${prompt.take(50)}...")
        return currentModel.generateStream(prompt)
    }
    
    fun getLoadedModelName(): String? = loadedModelName
    
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
            "memoryUsage" to getMemoryUsage(),
            "engine" to "llama-kotlin-android (llama.cpp)",
            "mmap" to true
        )
    }
    
    fun getLoadedModelInfo(): Map<String, Any> = getModelInfo()
}
