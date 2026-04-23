package com.localai.server.data.local.dao;

import androidx.room.*;
import com.localai.server.data.local.entity.Message;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0018\u0010\u000b\u001a\u0004\u0018\u00010\u00052\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\f\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u001c\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00100\u000f2\u0006\u0010\b\u001a\u00020\tH\'J\u001c\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00050\u00102\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006\u0013"}, d2 = {"Lcom/localai/server/data/local/dao/MessageDao;", "", "deleteMessage", "", "message", "Lcom/localai/server/data/local/entity/Message;", "(Lcom/localai/server/data/local/entity/Message;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteMessagesByConversation", "conversationId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getLastMessage", "getMessageCount", "", "getMessagesByConversation", "Lkotlinx/coroutines/flow/Flow;", "", "getMessagesByConversationSync", "insertMessage", "app_debug"})
@androidx.room.Dao
public abstract interface MessageDao {
    
    @androidx.room.Query(value = "SELECT * FROM messages WHERE conversationId = :conversationId ORDER BY createdAt ASC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.localai.server.data.local.entity.Message>> getMessagesByConversation(long conversationId);
    
    @androidx.room.Query(value = "SELECT * FROM messages WHERE conversationId = :conversationId ORDER BY createdAt ASC")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getMessagesByConversationSync(long conversationId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.localai.server.data.local.entity.Message>> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertMessage(@org.jetbrains.annotations.NotNull
    com.localai.server.data.local.entity.Message message, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Delete
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteMessage(@org.jetbrains.annotations.NotNull
    com.localai.server.data.local.entity.Message message, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM messages WHERE conversationId = :conversationId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteMessagesByConversation(long conversationId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM messages WHERE conversationId = :conversationId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getMessageCount(long conversationId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM messages WHERE conversationId = :conversationId ORDER BY createdAt DESC LIMIT 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getLastMessage(long conversationId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.localai.server.data.local.entity.Message> $completion);
}