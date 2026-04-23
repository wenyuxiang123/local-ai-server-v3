package com.localai.server.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.localai.server.data.local.entity.Conversation
import com.localai.server.data.local.entity.Message
import com.localai.server.data.repository.ChatRepository
import com.localai.server.engine.LlamaEngine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val engine: LlamaEngine
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()
    
    val conversations: StateFlow<List<Conversation>> = chatRepository.getAllConversations()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()
    
    private val _currentConversationId = MutableStateFlow<Long?>(null)
    val currentConversationId: StateFlow<Long?> = _currentConversationId.asStateFlow()
    
    private val _effect = MutableSharedFlow<ChatEffect>()
    val effect: SharedFlow<ChatEffect> = _effect
    
    init {
        viewModelScope.launch {
            conversations.collect { list ->
                if (_currentConversationId.value == null && list.isNotEmpty()) {
                    selectConversation(list.first().id)
                }
            }
        }
    }
    
    fun createConversation() {
        viewModelScope.launch {
            try {
                val title = "新会话 ${System.currentTimeMillis() % 10000}"
                val id = chatRepository.createConversation(title)
                selectConversation(id)
                _effect.emit(ChatEffect.ScrollToBottom)
            } catch (e: Exception) {
                _effect.emit(ChatEffect.ShowError("创建会话失败: ${e.message}"))
            }
        }
    }
    
    fun selectConversation(conversationId: Long) {
        viewModelScope.launch {
            _currentConversationId.value = conversationId
            chatRepository.getMessagesByConversation(conversationId).collect { msgs ->
                _messages.value = msgs
            }
        }
    }
    
    fun deleteConversation(conversationId: Long) {
        viewModelScope.launch {
            try {
                chatRepository.deleteConversation(conversationId)
                if (_currentConversationId.value == conversationId) {
                    val remaining = conversations.value.filter { it.id != conversationId }
                    if (remaining.isNotEmpty()) {
                        selectConversation(remaining.first().id)
                    } else {
                        _currentConversationId.value = null
                        _messages.value = emptyList()
                    }
                }
                _effect.emit(ChatEffect.ShowMessage("会话已删除"))
            } catch (e: Exception) {
                _effect.emit(ChatEffect.ShowError("删除会话失败: ${e.message}"))
            }
        }
    }
    
    fun renameConversation(conversationId: Long, newTitle: String) {
        viewModelScope.launch {
            try {
                chatRepository.updateConversationTitle(conversationId, newTitle)
            } catch (e: Exception) {
                _effect.emit(ChatEffect.ShowError("重命名失败: ${e.message}"))
            }
        }
    }
    
    fun sendMessage(content: String) {
        val conversationId = _currentConversationId.value ?: return
        if (content.isBlank()) return
        
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true, error = null) }
                
                // 保存用户消息
                chatRepository.sendMessage(conversationId, content)
                _effect.emit(ChatEffect.ScrollToBottom)
                
                // 检查模型是否已加载
                if (!engine.isModelLoaded()) {
                    val errorMsg = "模型未加载，请返回主界面启动服务"
                    _uiState.update { it.copy(isLoading = false, error = errorMsg) }
                    chatRepository.addAssistantMessage(conversationId, "[错误] $errorMsg")
                    _effect.emit(ChatEffect.ScrollToBottom)
                    return@launch
                }
                
                // 构建上下文
                val recentMessages = _messages.value.takeLast(10)
                val prompt = buildPrompt(recentMessages, content)
                
                // 直接调用引擎推理（纯本地）
                val response = engine.generate(prompt, maxTokens = 512)
                
                // 保存 AI 回复
                chatRepository.addAssistantMessage(conversationId, response)
                
                // 更新会话标题
                if (_messages.value.size <= 1) {
                    val title = content.take(20).let { 
                        if (content.length > 20) "$it..." else it 
                    }
                    chatRepository.updateConversationTitle(conversationId, title)
                }
                
                _uiState.update { it.copy(isLoading = false) }
                _effect.emit(ChatEffect.ScrollToBottom)
                
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
                chatRepository.addAssistantMessage(conversationId, "[错误] ${e.message}")
                _effect.emit(ChatEffect.ScrollToBottom)
            }
        }
    }
    
    private fun buildPrompt(history: List<Message>, newUserMessage: String): String {
        val sb = StringBuilder()
        sb.append("You are a helpful assistant. 请用中文回复。\n\n")
        
        for (msg in history) {
            when (msg.role) {
                "user" -> sb.append("用户: ${msg.content}\n")
                "assistant" -> sb.append("助手: ${msg.content}\n")
            }
        }
        
        sb.append("用户: $newUserMessage\n")
        sb.append("助手: ")
        
        return sb.toString()
    }
    
    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}

data class ChatUiState(
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class ChatEffect {
    object ScrollToBottom : ChatEffect()
    data class ShowError(val message: String) : ChatEffect()
    data class ShowMessage(val message: String) : ChatEffect()
}
