package com.localai.server.data.repository;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import kotlinx.coroutines.Dispatchers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J\"\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\bJ4\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u000b2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u0014"}, d2 = {"Lcom/localai/server/data/repository/ChatApiService;", "", "()V", "client", "Lokhttp3/OkHttpClient;", "gson", "Lcom/google/gson/Gson;", "buildMessages", "", "Lcom/localai/server/data/repository/ChatMessage;", "userContent", "", "historyMessages", "sendMessage", "Lkotlin/Result;", "baseUrl", "messages", "sendMessage-0E7RQCE", "(Ljava/lang/String;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_debug"})
public final class ChatApiService {
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String TAG = "ChatApiService";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String DEFAULT_BASE_URL = "http://localhost:8080";
    @org.jetbrains.annotations.NotNull
    private final okhttp3.OkHttpClient client = null;
    @org.jetbrains.annotations.NotNull
    private final com.google.gson.Gson gson = null;
    @org.jetbrains.annotations.NotNull
    public static final com.localai.server.data.repository.ChatApiService.Companion Companion = null;
    
    @javax.inject.Inject
    public ChatApiService() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.localai.server.data.repository.ChatMessage> buildMessages(@org.jetbrains.annotations.NotNull
    java.lang.String userContent, @org.jetbrains.annotations.NotNull
    java.util.List<com.localai.server.data.repository.ChatMessage> historyMessages) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/localai/server/data/repository/ChatApiService$Companion;", "", "()V", "DEFAULT_BASE_URL", "", "TAG", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}