package com.localai.server.engine

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
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
        private var librariesLoaded = false
        private var loadError: String? = null
        
        fun loadLibraries(): Boolean {
            if (librariesLoaded) return loadError == null
            
            try {
                Log.i(TAG, "Loading native libraries...")
                System.loadLibrary("c++_shared")
                Log.d(TAG, "Loaded: c++_shared")
                System.loadLibrary("ggml-base")
                Log.d(TAG, "Loaded: ggml-base")
                System.loadLibrary("ggml-cpu")
                Log.d(TAG, "Loaded: ggml-cpu")
                System.loadLibrary("ggml")
                Log.d(TAG, "Loaded: ggml")
                System.loadLibrary("llama")
                Log.d(TAG, "Loaded: llama")
                System.loadLibrary("localai-jni")
                Log.d(TAG, "Loaded: localai-jni")
                librariesLoaded = true
                Log.i(TAG, "All native libraries loaded successfully")
                return true
            } catch (e: UnsatisfiedLinkError) {
                loadError = "Failed to load native library: ${e.message}"
                Log.e(TAG, loadError!!, e)
            } catch (e: Exception) {
                loadError = "Error loading libraries: ${e.message}"
                Log.e(TAG, loadError!!, e)
            }
            return false
        }
        
        fun getLoadError(): String? = loadError
    }
    
    private var isModelLoaded = false
    private var loadedModelPath: String? = null
    private var modelContextSize = 0
    private var modelThreads = 0
    
    // Native方法声明
    private external fun loadModelNative(path: String, nCtx: Int, nThreads: Int): Boolean
    private external fun unloadModelNative()
    private external fun isModelLoadedNative(): Boolean
    private external fun generateNative(
        prompt: String,
        maxTokens: Int,
        temperature: Float,
        topK: Int,
        topP: Float
    ): String
    private external fun getLoadedModelNameNative(): String
    private external fun getContextSizeNative(): Int
    private external fun getMemoryUsageNative(): Long
    
    /**
     * 加载模型
     * @param path 模型文件路径
     * @param nCtx 上下文长度
     * @param nThreads 线程数
     * @return 是否加载成功
     */
    suspend fun loadModel(path: String, nCtx: Int, nThreads: Int): Boolean = withContext(Dispatchers.Default) {
        // 先加载native库
        if (!loadLibraries()) {
            Log.e(TAG, "Native libraries not loaded: ${getLoadError()}")
            return@withContext false
        }
        
        // 检查文件存在
        val file = File(path)
        if (!file.exists()) {
            Log.e(TAG, "Model file not found: $path")
            return@withContext false
        }
        
        // 检查内存是否足够
        val freeMemory = Runtime.getRuntime().freeMemory()
        val modelSize = file.length()
        if (freeMemory < modelSize * 1.5) {
            System.gc()
            Thread.sleep(100)
        }
        
        // 卸载旧模型
        if (isModelLoaded) {
            unloadModel()
        }
        
        // 加载新模型
        val success = loadModelNative(path, nCtx, nThreads)
        
        if (success) {
            isModelLoaded = true
            loadedModelPath = path
            modelContextSize = nCtx
            modelThreads = nThreads
        }
        
        success
    }
    
    /**
     * 卸载模型
     */
    fun unloadModel() {
        if (isModelLoaded) {
            unloadModelNative()
            isModelLoaded = false
            loadedModelPath = null
            modelContextSize = 0
            modelThreads = 0
            System.gc()
        }
    }
    
    /**
     * 检查模型是否已加载
     */
    fun isModelLoaded(): Boolean = isModelLoaded && isModelLoadedNative()
    
    /**
     * 生成文本
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
        generateNative(prompt, maxTokens, temperature, topK, topP)
    }
    
    /**
     * 获取已加载模型名称
     */
    fun getLoadedModelName(): String? {
        return if (isModelLoaded) {
            val name = getLoadedModelNameNative()
            if (name.isNullOrEmpty()) loadedModelPath?.substringAfterLast("/") else name
        } else null
    }
    
    /**
     * 获取上下文大小
     */
    fun getContextSize(): Int = if (isModelLoaded) getContextSizeNative() else 0
    
    /**
     * 获取内存使用量
     */
    fun getMemoryUsage(): Long = if (isModelLoaded) getMemoryUsageNative() else 0
    
    /**
     * 获取模型配置信息
     */
    fun getModelInfo(): ModelInfo? {
        if (!isModelLoaded) return null
        return ModelInfo(
            path = loadedModelPath ?: "",
            name = getLoadedModelName() ?: "",
            contextSize = modelContextSize,
            threads = modelThreads,
            memoryUsage = getMemoryUsage()
        )
    }
}

data class ModelInfo(
    val path: String,
    val name: String,
    val contextSize: Int,
    val threads: Int,
    val memoryUsage: Long
)
