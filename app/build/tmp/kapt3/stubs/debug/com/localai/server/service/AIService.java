package com.localai.server.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;
import com.localai.server.App;
import com.localai.server.MainActivity;
import com.localai.server.R;
import com.localai.server.engine.LlamaEngine;
import dagger.hilt.android.AndroidEntryPoint;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@dagger.hilt.android.AndroidEntryPoint
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\b\u0007\u0018\u0000 #2\u00020\u0001:\u0002#$B\u0005\u00a2\u0006\u0002\u0010\u0002J \u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014H\u0002J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\b\u0010\u001a\u001a\u00020\u0010H\u0016J\b\u0010\u001b\u001a\u00020\u0010H\u0016J\"\u0010\u001c\u001a\u00020\u00142\b\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u001d\u001a\u00020\u00142\u0006\u0010\u001e\u001a\u00020\u0014H\u0016J\b\u0010\u001f\u001a\u00020\u0010H\u0002J\b\u0010 \u001a\u00020\u0010H\u0002J\u0010\u0010!\u001a\u00020\u00102\u0006\u0010\"\u001a\u00020\u0012H\u0002R\u0012\u0010\u0003\u001a\u00060\u0004R\u00020\u0000X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0005\u001a\u00020\u00068\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006%"}, d2 = {"Lcom/localai/server/service/AIService;", "Landroid/app/Service;", "()V", "binder", "Lcom/localai/server/service/AIService$LocalBinder;", "engine", "Lcom/localai/server/engine/LlamaEngine;", "getEngine", "()Lcom/localai/server/engine/LlamaEngine;", "setEngine", "(Lcom/localai/server/engine/LlamaEngine;)V", "notificationBuilder", "Landroidx/core/app/NotificationCompat$Builder;", "serviceScope", "Lkotlinx/coroutines/CoroutineScope;", "loadModelInternal", "", "path", "", "nCtx", "", "nThreads", "onBind", "Landroid/os/IBinder;", "intent", "Landroid/content/Intent;", "onCreate", "onDestroy", "onStartCommand", "flags", "startId", "setupNotification", "stopService", "updateNotification", "text", "Companion", "LocalBinder", "app_debug"})
public final class AIService extends android.app.Service {
    public static final int NOTIFICATION_ID = 1001;
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACTION_START = "com.localai.server.action.START";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACTION_STOP = "com.localai.server.action.STOP";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACTION_LOAD_MODEL = "com.localai.server.action.LOAD_MODEL";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACTION_UNLOAD_MODEL = "com.localai.server.action.UNLOAD_MODEL";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String EXTRA_MODEL_PATH = "model_path";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String EXTRA_N_CTX = "n_ctx";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String EXTRA_N_THREADS = "n_threads";
    @org.jetbrains.annotations.NotNull
    private static final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isRunning = null;
    @org.jetbrains.annotations.NotNull
    private static final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isRunning = null;
    @org.jetbrains.annotations.NotNull
    private static final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _modelLoaded = null;
    @org.jetbrains.annotations.NotNull
    private static final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> modelLoaded = null;
    @org.jetbrains.annotations.NotNull
    private static final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _statusMessage = null;
    @org.jetbrains.annotations.NotNull
    private static final kotlinx.coroutines.flow.StateFlow<java.lang.String> statusMessage = null;
    @javax.inject.Inject
    public com.localai.server.engine.LlamaEngine engine;
    @org.jetbrains.annotations.NotNull
    private final com.localai.server.service.AIService.LocalBinder binder = null;
    private androidx.core.app.NotificationCompat.Builder notificationBuilder;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.CoroutineScope serviceScope = null;
    @org.jetbrains.annotations.NotNull
    public static final com.localai.server.service.AIService.Companion Companion = null;
    
    public AIService() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.localai.server.engine.LlamaEngine getEngine() {
        return null;
    }
    
    public final void setEngine(@org.jetbrains.annotations.NotNull
    com.localai.server.engine.LlamaEngine p0) {
    }
    
    @java.lang.Override
    public void onCreate() {
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public android.os.IBinder onBind(@org.jetbrains.annotations.NotNull
    android.content.Intent intent) {
        return null;
    }
    
    @java.lang.Override
    public int onStartCommand(@org.jetbrains.annotations.Nullable
    android.content.Intent intent, int flags, int startId) {
        return 0;
    }
    
    private final void setupNotification() {
    }
    
    private final void updateNotification(java.lang.String text) {
    }
    
    private final void loadModelInternal(java.lang.String path, int nCtx, int nThreads) {
    }
    
    private final void stopService() {
    }
    
    @java.lang.Override
    public void onDestroy() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J*\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u00042\b\b\u0002\u0010\u001e\u001a\u00020\f2\b\b\u0002\u0010\u001f\u001a\u00020\fJ\u000e\u0010 \u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cJ\u000e\u0010!\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0086T\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00040\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0014R\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0014R\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00040\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0014\u00a8\u0006\""}, d2 = {"Lcom/localai/server/service/AIService$Companion;", "", "()V", "ACTION_LOAD_MODEL", "", "ACTION_START", "ACTION_STOP", "ACTION_UNLOAD_MODEL", "EXTRA_MODEL_PATH", "EXTRA_N_CTX", "EXTRA_N_THREADS", "NOTIFICATION_ID", "", "_isRunning", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_modelLoaded", "_statusMessage", "isRunning", "Lkotlinx/coroutines/flow/StateFlow;", "()Lkotlinx/coroutines/flow/StateFlow;", "modelLoaded", "getModelLoaded", "statusMessage", "getStatusMessage", "loadModel", "", "context", "Landroid/content/Context;", "path", "nCtx", "nThreads", "start", "stop", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isRunning() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getModelLoaded() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getStatusMessage() {
            return null;
        }
        
        public final void start(@org.jetbrains.annotations.NotNull
        android.content.Context context) {
        }
        
        public final void stop(@org.jetbrains.annotations.NotNull
        android.content.Context context) {
        }
        
        public final void loadModel(@org.jetbrains.annotations.NotNull
        android.content.Context context, @org.jetbrains.annotations.NotNull
        java.lang.String path, int nCtx, int nThreads) {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004J\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/localai/server/service/AIService$LocalBinder;", "Landroid/os/Binder;", "(Lcom/localai/server/service/AIService;)V", "getEngine", "Lcom/localai/server/engine/LlamaEngine;", "getService", "Lcom/localai/server/service/AIService;", "app_debug"})
    public final class LocalBinder extends android.os.Binder {
        
        public LocalBinder() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.localai.server.service.AIService getService() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.localai.server.engine.LlamaEngine getEngine() {
            return null;
        }
    }
}