package com.localai.server.data.repository;

import com.localai.server.data.local.entity.Conversation;
import com.localai.server.data.local.entity.Message;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u001e\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u00a6@\u00a2\u0006\u0002\u0010\u0007J\u0016\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0006H\u00a6@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0003H\u00a6@\u00a2\u0006\u0002\u0010\u000eJ\u0016\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\u0003H\u00a6@\u00a2\u0006\u0002\u0010\u000eJ\u0014\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u00120\u0011H&J\u0018\u0010\u0014\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00130\u00112\u0006\u0010\r\u001a\u00020\u0003H&J\u001c\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u00120\u00112\u0006\u0010\u0004\u001a\u00020\u0003H&J\u001e\u0010\u0017\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u00a6@\u00a2\u0006\u0002\u0010\u0007J\u001e\u0010\u0018\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0006H\u00a6@\u00a2\u0006\u0002\u0010\u0007\u00a8\u0006\u0019"}, d2 = {"Lcom/localai/server/data/repository/ChatRepository;", "", "addAssistantMessage", "", "conversationId", "content", "", "(JLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createConversation", "title", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteConversation", "", "id", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteMessages", "getAllConversations", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/localai/server/data/local/entity/Conversation;", "getConversationById", "getMessagesByConversation", "Lcom/localai/server/data/local/entity/Message;", "sendMessage", "updateConversationTitle", "app_debug"})
public abstract interface ChatRepository {
    
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.localai.server.data.local.entity.Conversation>> getAllConversations();
    
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<com.localai.server.data.local.entity.Conversation> getConversationById(long id);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object createConversation(@org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateConversationTitle(long id, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteConversation(long id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.localai.server.data.local.entity.Message>> getMessagesByConversation(long conversationId);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object sendMessage(long conversationId, @org.jetbrains.annotations.NotNull
    java.lang.String content, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object addAssistantMessage(long conversationId, @org.jetbrains.annotations.NotNull
    java.lang.String content, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteMessages(long conversationId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}