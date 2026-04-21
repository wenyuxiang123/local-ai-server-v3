package com.localai.server.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.localai.server.domain.model.GenerateConfig
import com.localai.server.domain.model.ModelConfig
import com.localai.server.domain.repository.AIRepository
import com.localai.server.service.AIService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AIRepository
) : ViewModel() {
    
    // UI状态
    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state.asStateFlow()
    
    // 单次事件
    private val _effect = MutableSharedFlow<MainEffect>()
    val effect: SharedFlow<MainEffect> = _effect
    
    init {
        checkServiceStatus()
        loadAvailableModels()
    }
    
    /**
     * 处理用户意图
     */
    fun onIntent(intent: MainIntent) {
        when (intent) {
            is MainIntent.StartService -> startService()
            is MainIntent.StopService -> stopService()
            is MainIntent.LoadModel -> loadModel(intent.path)
            is MainIntent.DownloadModel -> downloadModel(intent.url, intent.name)
            is MainIntent.DeleteModel -> deleteModel(intent.path)
            is MainIntent.ClearError -> clearError()
        }
    }
    
    private fun checkServiceStatus() {
        viewModelScope.launch {
            AIService.isRunning.collect { running ->
                _state.update { it.copy(serviceRunning = running) }
            }
        }
        
        viewModelScope.launch {
            AIService.modelLoaded.collect { loaded ->
                _state.update { it.copy(modelLoaded = loaded) }
            }
        }
        
        viewModelScope.launch {
            AIService.statusMessage.collect { message ->
                _state.update { it.copy(statusMessage = message) }
            }
        }
    }
    
    private fun loadAvailableModels() {
        viewModelScope.launch {
            val models = repository.getAvailableModels()
            _state.update { it.copy(availableModels = models) }
        }
    }
    
    private fun startService() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            
            val address = repository.startServer()
            
            _state.update { 
                it.copy(
                    isLoading = false,
                    serverAddress = address,
                    serviceRunning = true
                )
            }
            
            _effect.emit(MainEffect.ShowToast("服务已启动: $address"))
        }
    }
    
    private fun stopService() {
        viewModelScope.launch {
            repository.stopServer()
            
            _state.update { 
                it.copy(
                    serviceRunning = false,
                    modelLoaded = false,
                    serverAddress = ""
                )
            }
            
            _effect.emit(MainEffect.ShowToast("服务已停止"))
        }
    }
    
    private fun loadModel(path: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            
            repository.loadModel(path)
                .onSuccess { config ->
                    _state.update { 
                        it.copy(
                            isLoading = false,
                            modelLoaded = true,
                            modelConfig = config,
                            selectedModelPath = path
                        )
                    }
                    _effect.emit(MainEffect.ShowToast("模型加载成功: ${config.name}"))
                }
                .onFailure { e ->
                    _state.update { 
                        it.copy(
                            isLoading = false,
                            error = e.message
                        )
                    }
                    _effect.emit(MainEffect.ShowError(e.message ?: "模型加载失败"))
                }
        }
    }
    
    private fun downloadModel(url: String, name: String) {
        viewModelScope.launch {
            _state.update { it.copy(isDownloading = true, downloadProgress = 0) }
            
            repository.downloadModel(url) { progress ->
                _state.update { it.copy(downloadProgress = progress) }
            }
                .onSuccess { file ->
                    _state.update { 
                        it.copy(
                            isDownloading = false,
                            downloadProgress = 100
                        )
                    }
                    loadAvailableModels()
                    _effect.emit(MainEffect.ShowToast("下载完成: ${file.name}"))
                }
                .onFailure { e ->
                    _state.update { 
                        it.copy(
                            isDownloading = false,
                            downloadProgress = 0,
                            error = "下载失败: ${e.message}"
                        )
                    }
                    _effect.emit(MainEffect.ShowError("下载失败: ${e.message}"))
                }
        }
    }
    
    private fun deleteModel(path: String) {
        viewModelScope.launch {
            repository.deleteModel(path)
                .onSuccess {
                    loadAvailableModels()
                    _effect.emit(MainEffect.ShowToast("模型已删除"))
                }
                .onFailure { e ->
                    _effect.emit(MainEffect.ShowError("删除失败: ${e.message}"))
                }
        }
    }
    
    private fun clearError() {
        _state.update { it.copy(error = null) }
    }
}

/**
 * UI状态
 */
data class MainState(
    val isLoading: Boolean = false,
    val serviceRunning: Boolean = false,
    val modelLoaded: Boolean = false,
    val serverAddress: String = "",
    val statusMessage: String = "",
    val modelConfig: ModelConfig? = null,
    val availableModels: List<ModelConfig> = emptyList(),
    val selectedModelPath: String = "",
    val isDownloading: Boolean = false,
    val downloadProgress: Int = 0,
    val error: String? = null
)

/**
 * 用户意图
 */
sealed class MainIntent {
    object StartService : MainIntent()
    object StopService : MainIntent()
    data class LoadModel(val path: String) : MainIntent()
    data class DownloadModel(val url: String, val name: String) : MainIntent()
    data class DeleteModel(val path: String) : MainIntent()
    object ClearError : MainIntent()
}

/**
 * 单次事件
 */
sealed class MainEffect {
    data class ShowToast(val message: String) : MainEffect()
    data class ShowError(val message: String) : MainEffect()
}
