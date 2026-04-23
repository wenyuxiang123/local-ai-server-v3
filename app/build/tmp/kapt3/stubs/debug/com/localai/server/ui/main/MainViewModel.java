package com.localai.server.ui.main;

import androidx.lifecycle.ViewModel;
import com.localai.server.data.repository.AIRepositoryImpl;
import com.localai.server.domain.model.GenerateConfig;
import com.localai.server.domain.model.ModelConfig;
import com.localai.server.domain.repository.AIRepository;
import com.localai.server.domain.repository.DownloadProgress;
import com.localai.server.service.AIService;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.SharedFlow;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0013\u001a\u00020\u0014H\u0002J\b\u0010\u0015\u001a\u00020\u0014H\u0002J\b\u0010\u0016\u001a\u00020\u0014H\u0002J\u0010\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0018\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u00192\u0006\u0010\u001c\u001a\u00020\u0019H\u0002J\b\u0010\u001d\u001a\u00020\u0014H\u0002J\u0010\u0010\u001e\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u000e\u0010\u001f\u001a\u00020\u00142\u0006\u0010 \u001a\u00020!J\b\u0010\"\u001a\u00020\u0014H\u0002J\b\u0010#\u001a\u00020\u0014H\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\n0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006$"}, d2 = {"Lcom/localai/server/ui/main/MainViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/localai/server/domain/repository/AIRepository;", "(Lcom/localai/server/domain/repository/AIRepository;)V", "_effect", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "Lcom/localai/server/ui/main/MainEffect;", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/localai/server/ui/main/MainState;", "effect", "Lkotlinx/coroutines/flow/SharedFlow;", "getEffect", "()Lkotlinx/coroutines/flow/SharedFlow;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "checkAndDownloadModel", "", "checkServiceStatus", "clearError", "deleteModel", "path", "", "downloadModel", "url", "name", "loadAvailableModels", "loadModel", "onIntent", "intent", "Lcom/localai/server/ui/main/MainIntent;", "startService", "stopService", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class MainViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.localai.server.domain.repository.AIRepository repository = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.localai.server.ui.main.MainState> _state = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.localai.server.ui.main.MainState> state = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableSharedFlow<com.localai.server.ui.main.MainEffect> _effect = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.SharedFlow<com.localai.server.ui.main.MainEffect> effect = null;
    
    @javax.inject.Inject
    public MainViewModel(@org.jetbrains.annotations.NotNull
    com.localai.server.domain.repository.AIRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.localai.server.ui.main.MainState> getState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.SharedFlow<com.localai.server.ui.main.MainEffect> getEffect() {
        return null;
    }
    
    /**
     * 检查并下载内置模型
     */
    private final void checkAndDownloadModel() {
    }
    
    /**
     * 处理用户意图
     */
    public final void onIntent(@org.jetbrains.annotations.NotNull
    com.localai.server.ui.main.MainIntent intent) {
    }
    
    private final void checkServiceStatus() {
    }
    
    private final void loadAvailableModels() {
    }
    
    private final void startService() {
    }
    
    private final void stopService() {
    }
    
    private final void loadModel(java.lang.String path) {
    }
    
    private final void downloadModel(java.lang.String url, java.lang.String name) {
    }
    
    private final void deleteModel(java.lang.String path) {
    }
    
    private final void clearError() {
    }
}