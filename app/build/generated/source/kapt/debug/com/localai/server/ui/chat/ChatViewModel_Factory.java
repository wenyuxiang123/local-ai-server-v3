package com.localai.server.ui.chat;

import com.localai.server.data.repository.ChatRepository;
import com.localai.server.engine.LlamaEngine;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class ChatViewModel_Factory implements Factory<ChatViewModel> {
  private final Provider<ChatRepository> chatRepositoryProvider;

  private final Provider<LlamaEngine> engineProvider;

  public ChatViewModel_Factory(Provider<ChatRepository> chatRepositoryProvider,
      Provider<LlamaEngine> engineProvider) {
    this.chatRepositoryProvider = chatRepositoryProvider;
    this.engineProvider = engineProvider;
  }

  @Override
  public ChatViewModel get() {
    return newInstance(chatRepositoryProvider.get(), engineProvider.get());
  }

  public static ChatViewModel_Factory create(Provider<ChatRepository> chatRepositoryProvider,
      Provider<LlamaEngine> engineProvider) {
    return new ChatViewModel_Factory(chatRepositoryProvider, engineProvider);
  }

  public static ChatViewModel newInstance(ChatRepository chatRepository, LlamaEngine engine) {
    return new ChatViewModel(chatRepository, engine);
  }
}
