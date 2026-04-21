package com.localai.server.domain.repository

import com.localai.server.domain.model.ModelConfig
import com.localai.server.domain.model.ServerStatus
import java.io.File

interface AIRepository {
    /**
     * 获取可用模型列表
     */
    suspend fun getAvailableModels(): List<ModelConfig>
    
    /**
     * 下载模型
     */
    suspend fun downloadModel(url: String, progress: (Int) -> Unit): Result<File>
    
    /**
     * 加载模型
     */
    suspend fun loadModel(path: String): Result<ModelConfig>
    
    /**
     * 删除模型
     */
    suspend fun deleteModel(path: String): Result<Unit>
    
    /**
     * 启动服务器
     */
    fun startServer(): String
    
    /**
     * 停止服务器
     */
    fun stopServer()
    
    /**
     * 获取服务器状态
     */
    fun getServerStatus(): ServerStatus
}
