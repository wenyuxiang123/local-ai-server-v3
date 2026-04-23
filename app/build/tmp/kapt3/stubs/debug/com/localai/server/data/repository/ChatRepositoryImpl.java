package com.localai.server.data.repository;

import com.localai.server.data.local.dao.ConversationDao;
import com.localai.server.data.local.dao.MessageDao;
import com.localai.server.data.local.entity.Conversation;
import com.localai.server.data.local.entity.Message;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u001e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH\u0096@\u00a2\u0006\u0002\u0010\fJ\u0016\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000bH\u0096@\u00a2\u0006\u0002\u0010\u000fJ\u0016\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\u0013J\u0016\u0010\u0014\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\u0013J\u0014\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180\u00170\u0016H\u0016J\u0018\u0010\u0019\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00180\u00162\u0006\u0010\u0012\u001a\u00020\bH\u0016J\u001c\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001b0\u00170\u00162\u0006\u0010\t\u001a\u00020\bH\u0016J\u001e\u0010\u001c\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH\u0096@\u00a2\u0006\u0002\u0010\fJ\u001e\u0010\u001d\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000bH\u0096@\u00a2\u0006\u0002\u0010\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2 = {"Lcom/localai/server/data/repository/ChatRepositoryImpl;", "Lcom/localai/server/data/repository/ChatRepository;", "conversationDao", "Lcom/localai/server/data/local/dao/ConversationDao;", "messageDao", "Lcom/localai/server/data/local/dao/MessageDao;", "(Lcom/localai/server/data/local/dao/ConversationDao;Lcom/localai/server/data/local/dao/MessageDao;)V", "addAssistantMessage", "", "conversationId", "content", "", "(JLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createConversation", "title", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteConversation", "", "id", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteMessages", "getAllConversations", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/localai/server/data/local/entity/Conversation;", "getConversationById", "getMessagesByConversation", "Lcom/localai/server/data/local/entity/Message;", "sendMessage", "updateConversationTitle", "app_debug"})
public final class ChatRepositoryImpl implements com.localai.server.data.repository.ChatRepository {
    @org.jetbrains.annotations.NotNull
    private final com.localai.server.data.local.dao.ConversationDao conversationDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.localai.server.data.local.dao.MessageDao messageDao = null;
    
    @javax.inject.Inject
    public ChatRepositoryImpl(@org.jetbrains.annotations.NotNull
    com.localai.server.data.local.dao.ConversationDao conversationDao, @org.jetbrains.annotations.NotNull
    com.localai.server.data.local.dao.MessageDao messageDao) {
        super();
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public kotlinx.coroutines.flow.Flow<java.util.List<com.localai.server.data.local.entity.Conversation>> getAllConversations() {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public kotlinx.coroutines.flow.Flow<com.localai.server.data.local.entity.Conversation> getConversationById(long id) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object createConversation(@org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object updateConversationTitle(long id, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object deleteConversation(long id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public kotlinx.coroutines.flow.Flow<java.util.List<com.localai.server.data.local.entity.Message>> getMessagesByConversation(long conversationId) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object sendMessage(long conversationId, @org.jetbrains.annotations.NotNull
    java.lang.String content, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object addAssistantMessage(long conversationId, @org.jetbrains.annotations.NotNull
    java.lang.String content, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object deleteMessages(long conversationId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}