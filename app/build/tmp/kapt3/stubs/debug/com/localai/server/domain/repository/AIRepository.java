package com.localai.server.domain.repository;

import android.net.Uri;
import com.localai.server.domain.model.ModelConfig;
import com.localai.server.domain.model.ServerStatus;
import java.io.File;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J$\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u00a6@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0007\u0010\bJ$\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u00032\u0006\u0010\u000b\u001a\u00020\fH\u00a6@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\r\u0010\u000eJ8\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0010\u001a\u00020\f2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\n0\u0012H\u00a6@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017H\u00a6@\u00a2\u0006\u0002\u0010\u0019J\b\u0010\u001a\u001a\u00020\u001bH&J\b\u0010\u001c\u001a\u00020\u001dH&J$\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00180\u00032\u0006\u0010\u000b\u001a\u00020\fH\u00a6@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001f\u0010\u000eJ\b\u0010 \u001a\u00020\fH&J\b\u0010!\u001a\u00020\nH&\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\""}, d2 = {"Lcom/localai/server/domain/repository/AIRepository;", "", "copyModelFromUri", "Lkotlin/Result;", "Ljava/io/File;", "uri", "Landroid/net/Uri;", "copyModelFromUri-gIAlu-s", "(Landroid/net/Uri;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteModel", "", "path", "", "deleteModel-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "downloadModel", "url", "progress", "Lkotlin/Function1;", "Lcom/localai/server/domain/repository/DownloadProgress;", "downloadModel-0E7RQCE", "(Ljava/lang/String;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAvailableModels", "", "Lcom/localai/server/domain/model/ModelConfig;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getServerStatus", "Lcom/localai/server/domain/model/ServerStatus;", "isBuiltInModelReady", "", "loadModel", "loadModel-gIAlu-s", "startServer", "stopServer", "app_debug"})
public abstract interface AIRepository {
    
    /**
     * 获取可用模型列表
     */
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getAvailableModels(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.localai.server.domain.model.ModelConfig>> $completion);
    
    /**
     * 检查内置模型是否存在
     */
    public abstract boolean isBuiltInModelReady();
    
    /**
     * 启动服务器
     */
    @org.jetbrains.annotations.NotNull
    public abstract java.lang.String startServer();
    
    /**
     * 停止服务器
     */
    public abstract void stopServer();
    
    /**
     * 获取服务器状态
     */
    @org.jetbrains.annotations.NotNull
    public abstract com.localai.server.domain.model.ServerStatus getServerStatus();
}