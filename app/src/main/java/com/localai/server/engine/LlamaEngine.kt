package com.localai.server.engine

import android.content.Context
import android.util.Log
import io.aatricks.llmedge.SmolLM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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
                true
            } catch (e: Exception) {
                Log.e(TAG, "Failed to initialize llama engine", e)
                false
            }
        }
        
        fun getLoadError(): String? = null
    }
    
    private var smolLM: SmolLM? = null
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
            
            Log.i(TAG, "Loading model: ${file.name}")
            
            // 创建新的 SmolLM 实例并加载模型
            smolLM = SmolLM()
            smolLM!!.load(path)
            
            isModelLoaded = true
            loadedModelPath = path
            loadedModelName = file.name
            
            Log.i(TAG, "Model loaded successfully: ${file.name}")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Failed to load model", e)
            isModelLoaded = false
            smolLM = null
            false
        }
    }
    
    fun unloadModel() {
        try {
            smolLM?.close()
        } catch (e: Exception) {
            Log.e(TAG, "Error closing model", e)
        }
        smolLM = null
        isModelLoaded = false
        loadedModelPath = null
        loadedModelName = null
        Log.i(TAG, "Model unloaded")
    }
    
    fun isModelLoaded(): Boolean = isModelLoaded
    
    suspend fun generate(prompt: String, maxTokens: Int = 512, temperature: Float = 0.7f, topK: Int = 40, topP: Float = 0.9f): String = withContext(Dispatchers.Default) {
        if (!isModelLoaded) throw IllegalStateException("模型未加载")
        val smol = smolLM ?: throw IllegalStateException("引擎未初始化")
        
        try {
            Log.d(TAG, "Generating response for prompt: ${prompt.take(50)}...")
            val result = smol.generate(prompt, maxTokens)
            Log.d(TAG, "Generated ${result.length} characters")
            result
        } catch (e: Exception) {
            Log.e(TAG, "Generation failed", e)
            throw e
        }
    }
    
    fun generateStream(prompt: String, maxTokens: Int = 512, temperature: Float = 0.7f, topK: Int = 40, topP: Float = 0.9f): Flow<String> = flow {
        if (!isModelLoaded) throw IllegalStateException("模型未加载")
        val smol = smolLM ?: throw IllegalStateException("引擎未初始化")
        
        try {
            Log.d(TAG, "Streaming generation for prompt: ${prompt.take(50)}...")
            smol.stream(prompt, maxTokens).collect { chunk ->
                emit(chunk)
            }
            Log.d(TAG, "Stream completed")
        } catch (e: Exception) {
            Log.e(TAG, "Stream generation failed", e)
            throw e
        }
    }.flowOn(Dispatchers.Default)
    
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
            "engine" to "llmedge (llama.cpp)",
            "mmap" to true
        )
    }
    
    fun getLoadedModelInfo(): Map<String, Any> = getModelInfo()
}
