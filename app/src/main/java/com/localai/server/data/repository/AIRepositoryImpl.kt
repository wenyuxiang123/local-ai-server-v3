package com.localai.server.data.repository

import android.content.Context
import android.net.Uri
import com.localai.server.domain.model.ModelConfig
import com.localai.server.domain.model.ServerStatus
import com.localai.server.domain.repository.AIRepository
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
    
    private val modelDir: File by lazy {
        File(context.filesDir, "models").apply { mkdirs() }
    }
    
    private val builtInModelName = "qwen3-1.7b-q4_k_m.gguf"
    
    /**
     * 检查并复制内置模型
     */
    suspend fun ensureBuiltInModel(): File? = withContext(Dispatchers.IO) {
        val targetFile = File(modelDir, builtInModelName)
        
        // 如果已存在，直接返回
        if (targetFile.exists()) {
            return@withContext targetFile
        }
        
        // 从assets复制
        try {
            context.assets.open(builtInModelName).use { input ->
                targetFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            targetFile
        } catch (e: Exception) {
            null
        }
    }
    
    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(120, TimeUnit.SECONDS)
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
    
    override suspend fun downloadModel(url: String, progress: (Int) -> Unit): Result<File> = withContext(Dispatchers.IO) {
        try {
            val fileName = url.substringAfterLast("/")
            val targetFile = File(modelDir, fileName)
            
            // 如果文件已存在，直接返回
            if (targetFile.exists()) {
                return@withContext Result.success(targetFile)
            }
            
            val request = Request.Builder().url(url).build()
            
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    return@withContext Result.failure(Exception("下载失败: HTTP ${response.code}"))
                }
                
                val total = response.body?.contentLength() ?: -1L
                var downloaded = 0L
                
                response.body?.byteStream()?.use { input ->
                    targetFile.outputStream().use { output ->
                        val buffer = ByteArray(8192)
                        var read: Int
                        while (input.read(buffer).also { read = it } != -1) {
                            output.write(buffer, 0, read)
                            downloaded += read
                            if (total > 0) {
                                progress((downloaded * 100 / total).toInt())
                            }
                        }
                    }
                }
            }
            
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
        return ServerStatus(
            isRunning = AIService.isRunning.value,
            modelLoaded = AIService.modelLoaded.value,
            loadedModel = engine.getLoadedModelName(),
            address = null,
            uptime = 0
        )
    }
}
