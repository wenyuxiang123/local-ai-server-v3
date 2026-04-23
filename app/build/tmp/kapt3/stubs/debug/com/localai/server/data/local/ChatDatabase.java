package com.localai.server.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.localai.server.data.local.dao.ConversationDao;
import com.localai.server.data.local.dao.MessageDao;
import com.localai.server.data.local.entity.Conversation;
import com.localai.server.data.local.entity.Message;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&\u00a8\u0006\b"}, d2 = {"Lcom/localai/server/data/local/ChatDatabase;", "Landroidx/room/RoomDatabase;", "()V", "conversationDao", "Lcom/localai/server/data/local/dao/ConversationDao;", "messageDao", "Lcom/localai/server/data/local/dao/MessageDao;", "Companion", "app_debug"})
@androidx.room.Database(entities = {com.localai.server.data.local.entity.Conversation.class, com.localai.server.data.local.entity.Message.class}, version = 1, exportSchema = true)
public abstract class ChatDatabase extends androidx.room.RoomDatabase {
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String DATABASE_NAME = "chat_database";
    @org.jetbrains.annotations.NotNull
    public static final com.localai.server.data.local.ChatDatabase.Companion Companion = null;
    
    public ChatDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public abstract com.localai.server.data.local.dao.ConversationDao conversationDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.localai.server.data.local.dao.MessageDao messageDao();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/localai/server/data/local/ChatDatabase$Companion;", "", "()V", "DATABASE_NAME", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}