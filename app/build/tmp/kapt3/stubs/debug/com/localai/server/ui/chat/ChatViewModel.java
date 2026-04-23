package com.localai.server.ui.chat;

import androidx.lifecycle.ViewModel;
import com.localai.server.data.local.entity.Conversation;
import com.localai.server.data.local.entity.Message;
import com.localai.server.data.repository.ChatApiService;
import com.localai.server.data.repository.ChatMessage;
import com.localai.server.data.repository.ChatRepository;
import com.localai.server.domain.repository.AIRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.*;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0006\u0010#\u001a\u00020$J\u0006\u0010%\u001a\u00020$J\u0006\u0010&\u001a\u00020$J\u000e\u0010\'\u001a\u00020$2\u0006\u0010(\u001a\u00020\u000bJ\u0006\u0010)\u001a\u00020$J\u0016\u0010*\u001a\u00020$2\u0006\u0010(\u001a\u00020\u000b2\u0006\u0010+\u001a\u00020,J\u000e\u0010-\u001a\u00020$2\u0006\u0010(\u001a\u00020\u000bJ\u000e\u0010.\u001a\u00020$2\u0006\u0010/\u001a\u00020,J\u0006\u00100\u001a\u00020$R\u0016\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00100\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u00100\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0019\u0010\u0019\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0018R\u0017\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000e0\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u001d\u0010\u001f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00100\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0018R\u0017\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00130\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0018\u00a8\u00061"}, d2 = {"Lcom/localai/server/ui/chat/ChatViewModel;", "Landroidx/lifecycle/ViewModel;", "chatRepository", "Lcom/localai/server/data/repository/ChatRepository;", "chatApiService", "Lcom/localai/server/data/repository/ChatApiService;", "aiRepository", "Lcom/localai/server/domain/repository/AIRepository;", "(Lcom/localai/server/data/repository/ChatRepository;Lcom/localai/server/data/repository/ChatApiService;Lcom/localai/server/domain/repository/AIRepository;)V", "_currentConversationId", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_effect", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "Lcom/localai/server/ui/chat/ChatEffect;", "_messages", "", "Lcom/localai/server/data/local/entity/Message;", "_uiState", "Lcom/localai/server/ui/chat/ChatUiState;", "conversations", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/localai/server/data/local/entity/Conversation;", "getConversations", "()Lkotlinx/coroutines/flow/StateFlow;", "currentConversationId", "getCurrentConversationId", "effect", "Lkotlinx/coroutines/flow/SharedFlow;", "getEffect", "()Lkotlinx/coroutines/flow/SharedFlow;", "messages", "getMessages", "uiState", "getUiState", "clearMessages", "", "closeDrawer", "createConversation", "deleteConversation", "conversationId", "openDrawer", "renameConversation", "newTitle", "", "selectConversation", "sendMessage", "content", "toggleDrawer", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class ChatViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.localai.server.data.repository.ChatRepository chatRepository = null;
    @org.jetbrains.annotations.NotNull
    private final com.localai.server.data.repository.ChatApiService chatApiService = null;
    @org.jetbrains.annotations.NotNull
    private final com.localai.server.domain.repository.AIRepository aiRepository = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.localai.server.ui.chat.ChatUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.localai.server.ui.chat.ChatUiState> uiState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.localai.server.data.local.entity.Conversation>> conversations = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.localai.server.data.local.entity.Message>> _messages = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.localai.server.data.local.entity.Message>> messages = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Long> _currentConversationId = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Long> currentConversationId = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableSharedFlow<com.localai.server.ui.chat.ChatEffect> _effect = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.SharedFlow<com.localai.server.ui.chat.ChatEffect> effect = null;
    
    @javax.inject.Inject
    public ChatViewModel(@org.jetbrains.annotations.NotNull
    com.localai.server.data.repository.ChatRepository chatRepository, @org.jetbrains.annotations.NotNull
    com.localai.server.data.repository.ChatApiService chatApiService, @org.jetbrains.annotations.NotNull
    com.localai.server.domain.repository.AIRepository aiRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.localai.server.ui.chat.ChatUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.localai.server.data.local.entity.Conversation>> getConversations() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.localai.server.data.local.entity.Message>> getMessages() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Long> getCurrentConversationId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.SharedFlow<com.localai.server.ui.chat.ChatEffect> getEffect() {
        return null;
    }
    
    /**
     * Create new conversation
     */
    public final void createConversation() {
    }
    
    /**
     * Select conversation and load messages
     */
    public final void selectConversation(long conversationId) {
    }
    
    /**
     * Delete conversation
     */
    public final void deleteConversation(long conversationId) {
    }
    
    /**
     * Rename conversation
     */
    public final void renameConversation(long conversationId, @org.jetbrains.annotations.NotNull
    java.lang.String newTitle) {
    }
    
    /**
     * Send user message and get AI response
     */
    public final void sendMessage(@org.jetbrains.annotations.NotNull
    java.lang.String content) {
    }
    
    /**
     * Clear current messages
     */
    public final void clearMessages() {
    }
    
    /**
     * Toggle drawer
     */
    public final void toggleDrawer() {
    }
    
    public final void openDrawer() {
    }
    
    public final void closeDrawer() {
    }
}