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

// 预定义可用模型
val AVAILABLE_MODELS = listOf(
    ModelInfo(
        name = "Qwen3-1.7B-Q4_K_M",
        url = "https://hf-mirror.com/Qwen/Qwen3-1.7B-GGUF/resolve/main/qwen3-1.7b-q4_k_m.gguf",
        size = "~1.1GB",
        description = "轻量级模型，速度快，适合对话"
    ),
    ModelInfo(
        name = "Qwen2.5-3B-Q4_K_M",
        url = "https://hf-mirror.com/Qwen/Qwen2.5-3B-GGUF/resolve/main/qwen2.5-3b-q4_k_m.gguf",
        size = "~2.0GB",
        description = "中等规模，效果更好"
    ),
    ModelInfo(
        name = "gemma-2b-it-q4_k_m",
        url = "https://hf-mirror.com/google/gemma-2b-it-GGUF/resolve/main/gemma-2b-it.q4_k_m.gguf",
        size = "~1.5GB",
        description = "Google开源模型"
    )
)
