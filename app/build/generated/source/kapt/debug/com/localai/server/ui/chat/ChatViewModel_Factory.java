package com.localai.server.ui.chat;

import com.localai.server.data.repository.ChatApiService;
import com.localai.server.data.repository.ChatRepository;
import com.localai.server.domain.repository.AIRepository;
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

  private final Provider<ChatApiService> chatApiServiceProvider;

  private final Provider<AIRepository> aiRepositoryProvider;

  public ChatViewModel_Factory(Provider<ChatRepository> chatRepositoryProvider,
      Provider<ChatApiService> chatApiServiceProvider,
      Provider<AIRepository> aiRepositoryProvider) {
    this.chatRepositoryProvider = chatRepositoryProvider;
    this.chatApiServiceProvider = chatApiServiceProvider;
    this.aiRepositoryProvider = aiRepositoryProvider;
  }

  @Override
  public ChatViewModel get() {
    return newInstance(chatRepositoryProvider.get(), chatApiServiceProvider.get(), aiRepositoryProvider.get());
  }

  public static ChatViewModel_Factory create(Provider<ChatRepository> chatRepositoryProvider,
      Provider<ChatApiService> chatApiServiceProvider,
      Provider<AIRepository> aiRepositoryProvider) {
    return new ChatViewModel_Factory(chatRepositoryProvider, chatApiServiceProvider, aiRepositoryProvider);
  }

  public static ChatViewModel newInstance(ChatRepository chatRepository,
      ChatApiService chatApiService, AIRepository aiRepository) {
    return new ChatViewModel(chatRepository, chatApiService, aiRepository);
  }
}
