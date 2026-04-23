package com.localai.server.ui.chat;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.localai.server.data.local.entity.Conversation;
import com.localai.server.databinding.ItemConversationBinding;
import java.text.SimpleDateFormat;
import java.util.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u0002\u0012\b\u0012\u00060\u0003R\u00020\u00000\u0001:\u0002\u0011\u0012B-\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0002\u0010\bJ\u001c\u0010\t\u001a\u00020\u00062\n\u0010\n\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u001c\u0010\r\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\fH\u0016R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/localai/server/ui/chat/ConversationAdapter;", "Landroidx/recyclerview/widget/ListAdapter;", "Lcom/localai/server/data/local/entity/Conversation;", "Lcom/localai/server/ui/chat/ConversationAdapter$ConversationViewHolder;", "onItemClick", "Lkotlin/Function1;", "", "onDeleteClick", "(Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "onBindViewHolder", "holder", "position", "", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "ConversationDiffCallback", "ConversationViewHolder", "app_debug"})
public final class ConversationAdapter extends androidx.recyclerview.widget.ListAdapter<com.localai.server.data.local.entity.Conversation, com.localai.server.ui.chat.ConversationAdapter.ConversationViewHolder> {
    @org.jetbrains.annotations.NotNull
    private final kotlin.jvm.functions.Function1<com.localai.server.data.local.entity.Conversation, kotlin.Unit> onItemClick = null;
    @org.jetbrains.annotations.NotNull
    private final kotlin.jvm.functions.Function1<com.localai.server.data.local.entity.Conversation, kotlin.Unit> onDeleteClick = null;
    
    public ConversationAdapter(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.localai.server.data.local.entity.Conversation, kotlin.Unit> onItemClick, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.localai.server.data.local.entity.Conversation, kotlin.Unit> onDeleteClick) {
        super(null);
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public com.localai.server.ui.chat.ConversationAdapter.ConversationViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull
    com.localai.server.ui.chat.ConversationAdapter.ConversationViewHolder holder, int position) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\u0016J\u0018\u0010\b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\u0016\u00a8\u0006\t"}, d2 = {"Lcom/localai/server/ui/chat/ConversationAdapter$ConversationDiffCallback;", "Landroidx/recyclerview/widget/DiffUtil$ItemCallback;", "Lcom/localai/server/data/local/entity/Conversation;", "()V", "areContentsTheSame", "", "oldItem", "newItem", "areItemsTheSame", "app_debug"})
    static final class ConversationDiffCallback extends androidx.recyclerview.widget.DiffUtil.ItemCallback<com.localai.server.data.local.entity.Conversation> {
        
        public ConversationDiffCallback() {
            super();
        }
        
        @java.lang.Override
        public boolean areItemsTheSame(@org.jetbrains.annotations.NotNull
        com.localai.server.data.local.entity.Conversation oldItem, @org.jetbrains.annotations.NotNull
        com.localai.server.data.local.entity.Conversation newItem) {
            return false;
        }
        
        @java.lang.Override
        public boolean areContentsTheSame(@org.jetbrains.annotations.NotNull
        com.localai.server.data.local.entity.Conversation oldItem, @org.jetbrains.annotations.NotNull
        com.localai.server.data.local.entity.Conversation newItem) {
            return false;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/localai/server/ui/chat/ConversationAdapter$ConversationViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/localai/server/databinding/ItemConversationBinding;", "(Lcom/localai/server/ui/chat/ConversationAdapter;Lcom/localai/server/databinding/ItemConversationBinding;)V", "dateFormat", "Ljava/text/SimpleDateFormat;", "bind", "", "conversation", "Lcom/localai/server/data/local/entity/Conversation;", "app_debug"})
    public final class ConversationViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull
        private final com.localai.server.databinding.ItemConversationBinding binding = null;
        @org.jetbrains.annotations.NotNull
        private final java.text.SimpleDateFormat dateFormat = null;
        
        public ConversationViewHolder(@org.jetbrains.annotations.NotNull
        com.localai.server.databinding.ItemConversationBinding binding) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull
        com.localai.server.data.local.entity.Conversation conversation) {
        }
    }
}