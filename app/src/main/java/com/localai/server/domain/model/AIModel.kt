package com.localai.server.domain.model

data class ModelConfig(
    val name: String,
    val path: String,
    val contextSize: Int = 2048,
    val threads: Int = 4,
    val sizeBytes: Long = 0
)

data class GenerateConfig(
    val maxTokens: Int = 512,
    val temperature: Float = 0.7f,
    val topK: Int = 40,
    val topP: Float = 0.9f
)

data class ServerStatus(
    val isRunning: Boolean,
    val modelLoaded: Boolean,
    val loadedModel: String?,
    val address: String?,
    val uptime: Long
)

data class ModelInfo(
    val name: String,
    val url: String,
    val size: String,
    val description: String
)

// ModelScope mirror for China - using unsloth models
val AVAILABLE_MODELS = listOf(
    ModelInfo(
        name = "Qwen3-1.7B-Q4_K_M",
        url = "https://modelscope.cn/api/v1/models/unsloth/Qwen3-1.7B-GGUF/resolve/master/Qwen3-1.7B-Q4_K_M.gguf",
        size = "~1.1GB",
        description = "轻量级模型，速度快，适合对话"
    ),
    ModelInfo(
        name = "Qwen2.5-3B-Q4_K_M",
        url = "https://modelscope.cn/api/v1/models/unsloth/Qwen2.5-3B-GGUF/resolve/master/Qwen2.5-3B-Q4_K_M.gguf",
        size = "~2.0GB",
        description = "中等规模，效果更好"
    ),
    ModelInfo(
        name = "gemma-2b-it-Q4_K_M",
        url = "https://modelscope.cn/api/v1/models/unsloth/gemma-2b-it-GGUF/resolve/master/gemma-2b-it-Q4_K_M.gguf",
        size = "~1.5GB",
        description = "Google开源模型"
    )
)
