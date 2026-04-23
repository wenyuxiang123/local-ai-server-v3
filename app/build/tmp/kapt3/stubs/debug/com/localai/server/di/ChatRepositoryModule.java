package com.localai.server.di;

import android.content.Context;
import androidx.room.Room;
import com.localai.server.data.local.ChatDatabase;
import com.localai.server.data.local.dao.ConversationDao;
import com.localai.server.data.local.dao.MessageDao;
import com.localai.server.data.repository.AIRepositoryImpl;
import com.localai.server.data.repository.ChatRepository;
import com.localai.server.data.repository.ChatRepositoryImpl;
import com.localai.server.domain.repository.AIRepository;
import com.localai.server.engine.LlamaEngine;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.Dispatchers;
import java.io.File;
import javax.inject.Singleton;

@dagger.Module
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\'\u00a8\u0006\u0007"}, d2 = {"Lcom/localai/server/di/ChatRepositoryModule;", "", "()V", "bindChatRepository", "Lcom/localai/server/data/repository/ChatRepository;", "chatRepositoryImpl", "Lcom/localai/server/data/repository/ChatRepositoryImpl;", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public abstract class ChatRepositoryModule {
    
    public ChatRepositoryModule() {
        super();
    }
    
    @dagger.Binds
    @javax.inject.Singleton
    @org.jetbrains.annotations.NotNull
    public abstract com.localai.server.data.repository.ChatRepository bindChatRepository(@org.jetbrains.annotations.NotNull
    com.localai.server.data.repository.ChatRepositoryImpl chatRepositoryImpl);
}