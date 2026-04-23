package com.localai.server.ui.chat;

import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.android.material.navigation.NavigationView;
import com.localai.server.databinding.ActivityChatBinding;
import com.localai.server.databinding.NavConversationsContentBinding;
import dagger.hilt.android.AndroidEntryPoint;

@dagger.hilt.android.AndroidEntryPoint
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0002J\b\u0010\u0013\u001a\u00020\u0012H\u0002J\b\u0010\u0014\u001a\u00020\u0012H\u0016J\u0012\u0010\u0015\u001a\u00020\u00122\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0014J\b\u0010\u0018\u001a\u00020\u0012H\u0002J\b\u0010\u0019\u001a\u00020\u0012H\u0002J\b\u0010\u001a\u001a\u00020\u0012H\u0002J\u0018\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u000b\u001a\u00020\f8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000e\u00a8\u0006 "}, d2 = {"Lcom/localai/server/ui/chat/ChatActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/localai/server/databinding/ActivityChatBinding;", "conversationAdapter", "Lcom/localai/server/ui/chat/ConversationAdapter;", "conversationsBinding", "Lcom/localai/server/databinding/NavConversationsContentBinding;", "messageAdapter", "Lcom/localai/server/ui/chat/MessageAdapter;", "viewModel", "Lcom/localai/server/ui/chat/ChatViewModel;", "getViewModel", "()Lcom/localai/server/ui/chat/ChatViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "observeEffects", "", "observeState", "onBackPressed", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "sendMessage", "setupDrawer", "setupViews", "showDeleteConfirmation", "conversationId", "", "title", "", "app_debug"})
public final class ChatActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.localai.server.databinding.ActivityChatBinding binding;
    private com.localai.server.databinding.NavConversationsContentBinding conversationsBinding;
    @org.jetbrains.annotations.NotNull
    private final kotlin.Lazy viewModel$delegate = null;
    private com.localai.server.ui.chat.MessageAdapter messageAdapter;
    private com.localai.server.ui.chat.ConversationAdapter conversationAdapter;
    
    public ChatActivity() {
        super();
    }
    
    private final com.localai.server.ui.chat.ChatViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupDrawer() {
    }
    
    private final void setupViews() {
    }
    
    private final void sendMessage() {
    }
    
    private final void showDeleteConfirmation(long conversationId, java.lang.String title) {
    }
    
    private final void observeState() {
    }
    
    private final void observeEffects() {
    }
    
    @java.lang.Override
    public void onBackPressed() {
    }
}