package com.localai.server.data.repository;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import com.localai.server.domain.model.ModelConfig;
import com.localai.server.domain.model.ServerStatus;
import com.localai.server.domain.repository.AIRepository;
import com.localai.server.domain.repository.DownloadProgress;
import com.localai.server.engine.LlamaEngine;
import com.localai.server.service.AIService;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.Dispatchers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import java.io.File;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0007\u0018\u0000 /2\u00020\u0001:\u0001/B\u0019\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J$\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\f0\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0015\u0010\u0016J$\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u00122\u0006\u0010\u0019\u001a\u00020\bH\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001a\u0010\u001bJ8\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\f0\u00122\u0006\u0010\u001d\u001a\u00020\b2\u0012\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020\u00180\u001fH\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b!\u0010\"J\u0014\u0010#\u001a\b\u0012\u0004\u0012\u00020%0$H\u0096@\u00a2\u0006\u0002\u0010&J\b\u0010\'\u001a\u00020(H\u0016J\b\u0010)\u001a\u00020*H\u0016J$\u0010+\u001a\b\u0012\u0004\u0012\u00020%0\u00122\u0006\u0010\u0019\u001a\u00020\bH\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b,\u0010\u001bJ\b\u0010-\u001a\u00020\bH\u0016J\b\u0010.\u001a\u00020\u0018H\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u000b\u001a\u00020\f8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000e\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u00060"}, d2 = {"Lcom/localai/server/data/repository/AIRepositoryImpl;", "Lcom/localai/server/domain/repository/AIRepository;", "context", "Landroid/content/Context;", "engine", "Lcom/localai/server/engine/LlamaEngine;", "(Landroid/content/Context;Lcom/localai/server/engine/LlamaEngine;)V", "builtInModelName", "", "client", "Lokhttp3/OkHttpClient;", "modelDir", "Ljava/io/File;", "getModelDir", "()Ljava/io/File;", "modelDir$delegate", "Lkotlin/Lazy;", "copyModelFromUri", "Lkotlin/Result;", "uri", "Landroid/net/Uri;", "copyModelFromUri-gIAlu-s", "(Landroid/net/Uri;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteModel", "", "path", "deleteModel-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "downloadModel", "url", "progress", "Lkotlin/Function1;", "Lcom/localai/server/domain/repository/DownloadProgress;", "downloadModel-0E7RQCE", "(Ljava/lang/String;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAvailableModels", "", "Lcom/localai/server/domain/model/ModelConfig;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getServerStatus", "Lcom/localai/server/domain/model/ServerStatus;", "isBuiltInModelReady", "", "loadModel", "loadModel-gIAlu-s", "startServer", "stopServer", "Companion", "app_debug"})
public final class AIRepositoryImpl implements com.localai.server.domain.repository.AIRepository {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private final com.localai.server.engine.LlamaEngine engine = null;
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String TAG = "AIRepositoryImpl";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String BUILT_IN_MODEL_URL = "https://hf-mirror.com/prithivMLmods/Qwen3-1.7B-GGUF/resolve/main/Qwen3_1.7B.Q4_K_M.gguf";
    @org.jetbrains.annotations.NotNull
    private final kotlin.Lazy modelDir$delegate = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String builtInModelName = "Qwen3_1.7B.Q4_K_M.gguf";
    @org.jetbrains.annotations.NotNull
    private final okhttp3.OkHttpClient client = null;
    @org.jetbrains.annotations.NotNull
    public static final com.localai.server.data.repository.AIRepositoryImpl.Companion Companion = null;
    
    @javax.inject.Inject
    public AIRepositoryImpl(@dagger.hilt.android.qualifiers.ApplicationContext
    @org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    com.localai.server.engine.LlamaEngine engine) {
        super();
    }
    
    private final java.io.File getModelDir() {
        return null;
    }
    
    @java.lang.Override
    public boolean isBuiltInModelReady() {
        return false;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object getAvailableModels(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.localai.server.domain.model.ModelConfig>> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public java.lang.String startServer() {
        return null;
    }
    
    @java.lang.Override
    public void stopServer() {
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public com.localai.server.domain.model.ServerStatus getServerStatus() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/localai/server/data/repository/AIRepositoryImpl$Companion;", "", "()V", "BUILT_IN_MODEL_URL", "", "TAG", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}