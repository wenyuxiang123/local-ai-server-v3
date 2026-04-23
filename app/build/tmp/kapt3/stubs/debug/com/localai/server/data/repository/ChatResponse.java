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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B)\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u00a2\u0006\u0002\u0010\tJ\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0011\u0010\u0011\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010\u0012\u001a\u0004\u0018\u00010\bH\u00c6\u0003J3\u0010\u0013\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\bH\u00c6\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0017\u001a\u00020\u0018H\u00d6\u0001J\t\u0010\u0019\u001a\u00020\u0003H\u00d6\u0001R\u0019\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u001a"}, d2 = {"Lcom/localai/server/data/repository/ChatResponse;", "", "id", "", "choices", "", "Lcom/localai/server/data/repository/Choice;", "error", "Lcom/localai/server/data/repository/ErrorResponse;", "(Ljava/lang/String;Ljava/util/List;Lcom/localai/server/data/repository/ErrorResponse;)V", "getChoices", "()Ljava/util/List;", "getError", "()Lcom/localai/server/data/repository/ErrorResponse;", "getId", "()Ljava/lang/String;", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class ChatResponse {
    @org.jetbrains.annotations.Nullable
    private final java.lang.String id = null;
    @org.jetbrains.annotations.Nullable
    private final java.util.List<com.localai.server.data.repository.Choice> choices = null;
    @org.jetbrains.annotations.Nullable
    private final com.localai.server.data.repository.ErrorResponse error = null;
    
    public ChatResponse(@org.jetbrains.annotations.Nullable
    java.lang.String id, @org.jetbrains.annotations.Nullable
    java.util.List<com.localai.server.data.repository.Choice> choices, @org.jetbrains.annotations.Nullable
    com.localai.server.data.repository.ErrorResponse error) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.List<com.localai.server.data.repository.Choice> getChoices() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.localai.server.data.repository.ErrorResponse getError() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.List<com.localai.server.data.repository.Choice> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.localai.server.data.repository.ErrorResponse component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.localai.server.data.repository.ChatResponse copy(@org.jetbrains.annotations.Nullable
    java.lang.String id, @org.jetbrains.annotations.Nullable
    java.util.List<com.localai.server.data.repository.Choice> choices, @org.jetbrains.annotations.Nullable
    com.localai.server.data.repository.ErrorResponse error) {
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