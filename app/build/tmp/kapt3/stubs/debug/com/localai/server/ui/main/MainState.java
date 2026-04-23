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

/**
 * UI状态
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b4\b\u0086\b\u0018\u00002\u00020\u0001B\u00cd\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\b\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0011\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0011\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0015\u0012\b\b\u0002\u0010\u0016\u001a\u00020\b\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u0011\u0012\b\b\u0002\u0010\u0019\u001a\u00020\b\u0012\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\b\u00a2\u0006\u0002\u0010\u001bJ\t\u00101\u001a\u00020\u0003H\u00c6\u0003J\t\u00102\u001a\u00020\u0003H\u00c6\u0003J\t\u00103\u001a\u00020\u0011H\u00c6\u0003J\t\u00104\u001a\u00020\u0003H\u00c6\u0003J\t\u00105\u001a\u00020\u0011H\u00c6\u0003J\t\u00106\u001a\u00020\u0015H\u00c6\u0003J\t\u00107\u001a\u00020\bH\u00c6\u0003J\t\u00108\u001a\u00020\u0003H\u00c6\u0003J\t\u00109\u001a\u00020\u0011H\u00c6\u0003J\t\u0010:\u001a\u00020\bH\u00c6\u0003J\u000b\u0010;\u001a\u0004\u0018\u00010\bH\u00c6\u0003J\t\u0010<\u001a\u00020\u0003H\u00c6\u0003J\t\u0010=\u001a\u00020\u0003H\u00c6\u0003J\t\u0010>\u001a\u00020\u0003H\u00c6\u0003J\t\u0010?\u001a\u00020\bH\u00c6\u0003J\t\u0010@\u001a\u00020\bH\u00c6\u0003J\u000b\u0010A\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003J\u000f\u0010B\u001a\b\u0012\u0004\u0012\u00020\u000b0\rH\u00c6\u0003J\t\u0010C\u001a\u00020\bH\u00c6\u0003J\u00d1\u0001\u0010D\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\b2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b2\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\r2\b\b\u0002\u0010\u000e\u001a\u00020\b2\b\b\u0002\u0010\u000f\u001a\u00020\u00032\b\b\u0002\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u00032\b\b\u0002\u0010\u0013\u001a\u00020\u00112\b\b\u0002\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\b2\b\b\u0002\u0010\u0017\u001a\u00020\u00032\b\b\u0002\u0010\u0018\u001a\u00020\u00112\b\b\u0002\u0010\u0019\u001a\u00020\b2\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\bH\u00c6\u0001J\u0013\u0010E\u001a\u00020\u00032\b\u0010F\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010G\u001a\u00020\u0011H\u00d6\u0001J\t\u0010H\u001a\u00020\bH\u00d6\u0001R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u0013\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001fR\u0011\u0010\u0014\u001a\u00020\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0011\u0010\u0016\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0013\u0010\u001a\u001a\u0004\u0018\u00010\b\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010$R\u0011\u0010\u000f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010&R\u0011\u0010\u0012\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010&R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010&R\u0011\u0010\u0017\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010&R\u0013\u0010\n\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010(R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010&R\u0011\u0010\u0018\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010\u001fR\u0011\u0010\u0019\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010$R\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010&R\u0011\u0010\u000e\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010$R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010$R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u0010&R\u0011\u0010\t\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u0010$\u00a8\u0006I"}, d2 = {"Lcom/localai/server/ui/main/MainState;", "", "isLoading", "", "serviceRunning", "modelLoaded", "modelReady", "serverAddress", "", "statusMessage", "modelConfig", "Lcom/localai/server/domain/model/ModelConfig;", "availableModels", "", "selectedModelPath", "isDownloading", "downloadProgress", "", "isDownloadingModel", "downloadPercent", "downloadSpeed", "", "downloadStatus", "isModelLoading", "modelLoadingProgress", "modelLoadingStatus", "error", "(ZZZZLjava/lang/String;Ljava/lang/String;Lcom/localai/server/domain/model/ModelConfig;Ljava/util/List;Ljava/lang/String;ZIZIJLjava/lang/String;ZILjava/lang/String;Ljava/lang/String;)V", "getAvailableModels", "()Ljava/util/List;", "getDownloadPercent", "()I", "getDownloadProgress", "getDownloadSpeed", "()J", "getDownloadStatus", "()Ljava/lang/String;", "getError", "()Z", "getModelConfig", "()Lcom/localai/server/domain/model/ModelConfig;", "getModelLoaded", "getModelLoadingProgress", "getModelLoadingStatus", "getModelReady", "getSelectedModelPath", "getServerAddress", "getServiceRunning", "getStatusMessage", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class MainState {
    private final boolean isLoading = false;
    private final boolean serviceRunning = false;
    private final boolean modelLoaded = false;
    private final boolean modelReady = false;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String serverAddress = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String statusMessage = null;
    @org.jetbrains.annotations.Nullable
    private final com.localai.server.domain.model.ModelConfig modelConfig = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.localai.server.domain.model.ModelConfig> availableModels = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String selectedModelPath = null;
    private final boolean isDownloading = false;
    private final int downloadProgress = 0;
    private final boolean isDownloadingModel = false;
    private final int downloadPercent = 0;
    private final long downloadSpeed = 0L;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String downloadStatus = null;
    private final boolean isModelLoading = false;
    private final int modelLoadingProgress = 0;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String modelLoadingStatus = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String error = null;
    
    public MainState(boolean isLoading, boolean serviceRunning, boolean modelLoaded, boolean modelReady, @org.jetbrains.annotations.NotNull
    java.lang.String serverAddress, @org.jetbrains.annotations.NotNull
    java.lang.String statusMessage, @org.jetbrains.annotations.Nullable
    com.localai.server.domain.model.ModelConfig modelConfig, @org.jetbrains.annotations.NotNull
    java.util.List<com.localai.server.domain.model.ModelConfig> availableModels, @org.jetbrains.annotations.NotNull
    java.lang.String selectedModelPath, boolean isDownloading, int downloadProgress, boolean isDownloadingModel, int downloadPercent, long downloadSpeed, @org.jetbrains.annotations.NotNull
    java.lang.String downloadStatus, boolean isModelLoading, int modelLoadingProgress, @org.jetbrains.annotations.NotNull
    java.lang.String modelLoadingStatus, @org.jetbrains.annotations.Nullable
    java.lang.String error) {
        super();
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    public final boolean getServiceRunning() {
        return false;
    }
    
    public final boolean getModelLoaded() {
        return false;
    }
    
    public final boolean getModelReady() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getServerAddress() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getStatusMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.localai.server.domain.model.ModelConfig getModelConfig() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.localai.server.domain.model.ModelConfig> getAvailableModels() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getSelectedModelPath() {
        return null;
    }
    
    public final boolean isDownloading() {
        return false;
    }
    
    public final int getDownloadProgress() {
        return 0;
    }
    
    public final boolean isDownloadingModel() {
        return false;
    }
    
    public final int getDownloadPercent() {
        return 0;
    }
    
    public final long getDownloadSpeed() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getDownloadStatus() {
        return null;
    }
    
    public final boolean isModelLoading() {
        return false;
    }
    
    public final int getModelLoadingProgress() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getModelLoadingStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getError() {
        return null;
    }
    
    public MainState() {
        super();
    }
    
    public final boolean component1() {
        return false;
    }
    
    public final boolean component10() {
        return false;
    }
    
    public final int component11() {
        return 0;
    }
    
    public final boolean component12() {
        return false;
    }
    
    public final int component13() {
        return 0;
    }
    
    public final long component14() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component15() {
        return null;
    }
    
    public final boolean component16() {
        return false;
    }
    
    public final int component17() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component18() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component19() {
        return null;
    }
    
    public final boolean component2() {
        return false;
    }
    
    public final boolean component3() {
        return false;
    }
    
    public final boolean component4() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.localai.server.domain.model.ModelConfig component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.localai.server.domain.model.ModelConfig> component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.localai.server.ui.main.MainState copy(boolean isLoading, boolean serviceRunning, boolean modelLoaded, boolean modelReady, @org.jetbrains.annotations.NotNull
    java.lang.String serverAddress, @org.jetbrains.annotations.NotNull
    java.lang.String statusMessage, @org.jetbrains.annotations.Nullable
    com.localai.server.domain.model.ModelConfig modelConfig, @org.jetbrains.annotations.NotNull
    java.util.List<com.localai.server.domain.model.ModelConfig> availableModels, @org.jetbrains.annotations.NotNull
    java.lang.String selectedModelPath, boolean isDownloading, int downloadProgress, boolean isDownloadingModel, int downloadPercent, long downloadSpeed, @org.jetbrains.annotations.NotNull
    java.lang.String downloadStatus, boolean isModelLoading, int modelLoadingProgress, @org.jetbrains.annotations.NotNull
    java.lang.String modelLoadingStatus, @org.jetbrains.annotations.Nullable
    java.lang.String error) {
        return null;
    }
    
    @java.lang.Override
    public boolean equals(@org.jetbrains.annotations.Nullable
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        return null;
    }
}