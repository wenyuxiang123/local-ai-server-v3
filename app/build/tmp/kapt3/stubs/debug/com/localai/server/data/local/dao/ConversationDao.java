package com.localai.server.data.local.dao;

import androidx.room.*;
import com.localai.server.data.local.entity.Conversation;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\fH\'J\u0018\u0010\u000e\u001a\u0004\u0018\u00010\u00052\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0018\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\f2\u0006\u0010\b\u001a\u00020\tH\'J\u0016\u0010\u0010\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J \u0010\u0012\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\u0013\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\u0014J(\u0010\u0015\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u0013\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\u0018\u00a8\u0006\u0019"}, d2 = {"Lcom/localai/server/data/local/dao/ConversationDao;", "", "deleteConversation", "", "conversation", "Lcom/localai/server/data/local/entity/Conversation;", "(Lcom/localai/server/data/local/entity/Conversation;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteConversationById", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllConversations", "Lkotlinx/coroutines/flow/Flow;", "", "getConversationById", "getConversationByIdFlow", "insertConversation", "updateConversation", "updateTimestamp", "timestamp", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateTitle", "title", "", "(JLjava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao
public abstract interface ConversationDao {
    
    @androidx.room.Query(value = "SELECT * FROM conversations ORDER BY updatedAt DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.localai.server.data.local.entity.Conversation>> getAllConversations();
    
    @androidx.room.Query(value = "SELECT * FROM conversations WHERE id = :id")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getConversationById(long id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.localai.server.data.local.entity.Conversation> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM conversations WHERE id = :id")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<com.localai.server.data.local.entity.Conversation> getConversationByIdFlow(long id);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertConversation(@org.jetbrains.annotations.NotNull
    com.localai.server.data.local.entity.Conversation conversation, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateConversation(@org.jetbrains.annotations.NotNull
    com.localai.server.data.local.entity.Conversation conversation, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteConversation(@org.jetbrains.annotations.NotNull
    com.localai.server.data.local.entity.Conversation conversation, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM conversations WHERE id = :id")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteConversationById(long id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE conversations SET updatedAt = :timestamp WHERE id = :id")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateTimestamp(long id, long timestamp, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE conversations SET title = :title, updatedAt = :timestamp WHERE id = :id")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateTitle(long id, @org.jetbrains.annotations.NotNull
    java.lang.String title, long timestamp, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}