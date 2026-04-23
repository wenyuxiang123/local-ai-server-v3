package com.localai.server;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import com.localai.server.databinding.ActivityMainBinding;
import com.localai.server.engine.LlamaEngine;
import com.localai.server.ui.chat.ChatActivity;
import com.localai.server.ui.main.MainEffect;
import com.localai.server.ui.main.MainIntent;
import com.localai.server.ui.main.MainState;
import com.localai.server.ui.main.MainViewModel;
import dagger.hilt.android.AndroidEntryPoint;

@dagger.hilt.android.AndroidEntryPoint
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 $2\u00020\u0001:\u0001$B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0002J\b\u0010\u0014\u001a\u00020\u0013H\u0002J\u0010\u0010\u0015\u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\b\u0010\u0018\u001a\u00020\u0013H\u0002J\b\u0010\u0019\u001a\u00020\u0013H\u0002J\u0012\u0010\u001a\u001a\u00020\u00132\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0014J\b\u0010\u001d\u001a\u00020\u0013H\u0002J\u0010\u0010\u001e\u001a\u00020\u00132\u0006\u0010\u001f\u001a\u00020\bH\u0002J\u0010\u0010 \u001a\u00020\u00132\u0006\u0010\u001f\u001a\u00020\bH\u0002J\u0010\u0010!\u001a\u00020\u00132\u0006\u0010\"\u001a\u00020#H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010\f\u001a\u00020\r8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006%"}, d2 = {"Lcom/localai/server/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/localai/server/databinding/ActivityMainBinding;", "requestPermissionLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "", "", "selectModelLauncher", "selectedModelUri", "Landroid/net/Uri;", "viewModel", "Lcom/localai/server/ui/main/MainViewModel;", "getViewModel", "()Lcom/localai/server/ui/main/MainViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "checkNativeLibraries", "", "checkPermissions", "formatSpeed", "bytesPerSecond", "", "observeEffects", "observeState", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "setupViews", "showError", "message", "showToast", "updateUI", "state", "Lcom/localai/server/ui/main/MainState;", "Companion", "app_debug"})
public final class MainActivity extends androidx.appcompat.app.AppCompatActivity {
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String TAG = "MainActivity";
    private com.localai.server.databinding.ActivityMainBinding binding;
    @org.jetbrains.annotations.NotNull
    private final kotlin.Lazy viewModel$delegate = null;
    @org.jetbrains.annotations.Nullable
    private android.net.Uri selectedModelUri;
    @org.jetbrains.annotations.NotNull
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String[]> requestPermissionLauncher = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String[]> selectModelLauncher = null;
    @org.jetbrains.annotations.NotNull
    public static final com.localai.server.MainActivity.Companion Companion = null;
    
    public MainActivity() {
        super();
    }
    
    private final com.localai.server.ui.main.MainViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void checkNativeLibraries() {
    }
    
    private final void checkPermissions() {
    }
    
    private final void setupViews() {
    }
    
    private final void observeState() {
    }
    
    private final void observeEffects() {
    }
    
    private final void updateUI(com.localai.server.ui.main.MainState state) {
    }
    
    private final java.lang.String formatSpeed(long bytesPerSecond) {
        return null;
    }
    
    private final void showToast(java.lang.String message) {
    }
    
    private final void showError(java.lang.String message) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/localai/server/MainActivity$Companion;", "", "()V", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}