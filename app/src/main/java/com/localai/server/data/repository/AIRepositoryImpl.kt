package com.localai.server.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.localai.server.domain.model.ModelConfig
import com.localai.server.domain.model.ServerStatus
import com.localai.server.domain.repository.AIRepository
import com.localai.server.domain.repository.DownloadProgress
import com.localai.server.engine.LlamaEngine
import com.localai.server.service.AIService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AIRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val engine: LlamaEngine
) : AIRepository {
    
    companion object {
        private const val TAG = "AIRepositoryImpl"
        private const val BUILT_IN_MODEL_URL = "https://modelscope.cn/api/v1/models/unsloth/Qwen3-1.7B-GGUF/resolve/master/Qwen3-1.7B-Q4_K_M.gguf"
    }
    
    private val modelDir: File by lazy {
        File(context.filesDir, "models").apply { mkdirs() }
    }
    
    private val builtInModelName = "Qwen3-1.7B-Q4_K_M.gguf"
    
    override fun isBuiltInModelReady(): Boolean {
        return File(modelDir, builtInModelName).exists()
    }
    
    private val client = OkHttpClient.Builder()
        .connectTimeout(120, TimeUnit.SECONDS)
        .readTimeout(300, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()
    
    override suspend fun getAvailableModels(): List<ModelConfig> = withContext(Dispatchers.IO) {
        modelDir.listFiles()
            ?.filter { it.extension == "gguf" }
            ?.map { file ->
                ModelConfig(
                    name = file.nameWithoutExtension,
                    path = file.absolutePath,
                    sizeBytes = file.length()
                )
            }
            ?.sortedByDescending { it.sizeBytes }
            ?: emptyList()
    }
    
    override suspend fun downloadModel(url: String, progress: (DownloadProgress) -> Unit): Result<File> = withContext(Dispatchers.IO) {
        try {
            val fileName = url.substringAfterLast("/")
            val targetFile = File(modelDir, fileName)
            
            // 如果文件已存在，直接返回
            if (targetFile.exists()) {
                Log.i(TAG, "Model already exists: ${targetFile.absolutePath}")
                return@withContext Result.success(targetFile)
            }
            
            Log.i(TAG, "Starting download: $url")
            val request = Request.Builder().url(url).build()
            
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    return@withContext Result.failure(Exception("下载失败: HTTP ${response.code}"))
                }
                
                val total = response.body?.contentLength() ?: -1L
                var downloaded = 0L
                var lastUpdateTime = System.currentTimeMillis()
                var lastDownloaded = 0L
                
                response.body?.byteStream()?.use { input ->
                    targetFile.outputStream().use { output ->
                        val buffer = ByteArray(8192)
                        var read: Int
                        while (input.read(buffer).also { read = it } != -1) {
                            output.write(buffer, 0, read)
                            downloaded += read
                            
                            // 每500ms更新一次进度
                            val currentTime = System.currentTimeMillis()
                            if (currentTime - lastUpdateTime >= 500) {
                                val timeDiff = currentTime - lastUpdateTime
                                val bytesDiff = downloaded - lastDownloaded
                                val speed = (bytesDiff * 1000 / timeDiff) // bytes per second
                                
                                val percent = if (total > 0) (downloaded * 100 / total).toInt() else 0
                                progress(DownloadProgress(percent, speed, downloaded, total))
                                
                                lastUpdateTime = currentTime
                                lastDownloaded = downloaded
                            }
                        }
                        
                        // 最终进度更新
                        val finalSpeed = if (downloaded > 0) downloaded * 1000 / (System.currentTimeMillis() - lastUpdateTime).coerceAtLeast(1) else 0
                        progress(DownloadProgress(100, finalSpeed, downloaded, total))
                    }
                }
            }
            
            Log.i(TAG, "Download completed: ${targetFile.absolutePath}")
            Result.success(targetFile)
        } catch (e: Exception) {
            Log.e(TAG, "Download failed", e)
            Result.failure(e)
        }
    }
    
    override suspend fun copyModelFromUri(uri: Uri): Result<File> = withContext(Dispatchers.IO) {
        try {
            val fileName = uri.lastPathSegment?.substringAfterLast("/") 
                ?: "model_${System.currentTimeMillis()}.gguf"
            val targetFile = File(modelDir, fileName)
            
            context.contentResolver.openInputStream(uri)?.use { input ->
                targetFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            } ?: return@withContext Result.failure(Exception("无法打开文件"))
            
            Result.success(targetFile)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun loadModel(path: String): Result<ModelConfig> = withContext(Dispatchers.Default) {
        try {
            val file = File(path)
            if (!file.exists()) {
                return@withContext Result.failure(Exception("模型文件不存在"))
            }
            
            // 计算最优线程数
            val threads = Runtime.getRuntime().availableProcessors().coerceIn(1, 4)
            
            // 加载模型
            val success = engine.loadModel(path, 2048, threads)
            
            if (success) {
                val config = ModelConfig(
                    name = file.nameWithoutExtension,
                    path = path,
                    threads = threads,
                    sizeBytes = file.length()
                )
                Result.success(config)
            } else {
                Result.failure(Exception("模型加载失败"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun deleteModel(path: String): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val file = File(path)
            if (file.exists()) {
                val deleted = file.delete()
                if (deleted) Result.success(Unit) else Result.failure(Exception("删除失败"))
            } else {
                Result.success(Unit)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override fun startServer(): String {
        AIService.start(context)
        
        // 获取设备IP地址
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as android.net.wifi.WifiManager
        val wifiInfo = wifiManager.connectionInfo
        val ipAddress = wifiInfo.ipAddress
        
        return if (ipAddress != 0) {
            String.format(
                "http://%d.%d.%d.%d:8080",
                ipAddress and 0xff,
                ipAddress shr 8 and 0xff,
                ipAddress shr 16 and 0xff,
                ipAddress shr 24 and 0xff
            )
        } else {
            "http://localhost:8080"
        }
    }
    
    override fun stopServer() {
        AIService.stop(context)
    }
    
    override fun getServerStatus(): ServerStatus {
        // 动态计算服务地址
        val serverAddress = if (AIService.isRunning.value) {
            val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as android.net.wifi.WifiManager
            val wifiInfo = wifiManager.connectionInfo
            val ipAddress = wifiInfo.ipAddress
            
            if (ipAddress != 0) {
                String.format(
                    "http://%d.%d.%d.%d:8080",
                    ipAddress and 0xff,
                    ipAddress shr 8 and 0xff,
                    ipAddress shr 16 and 0xff,
                    ipAddress shr 24 and 0xff
                )
            } else {
                "http://localhost:8080"
            }
        } else {
            null
        }
        
        return ServerStatus(
            isRunning = AIService.isRunning.value,
            modelLoaded = AIService.modelLoaded.value,
            loadedModel = engine.getLoadedModelName(),
            address = serverAddress,
            uptime = 0
        )
    }
}
