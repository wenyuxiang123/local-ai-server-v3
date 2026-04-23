package com.localai.server.di;

import com.localai.server.data.local.ChatDatabase;
import com.localai.server.data.local.dao.ConversationDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class DatabaseModule_ProvideConversationDaoFactory implements Factory<ConversationDao> {
  private final Provider<ChatDatabase> databaseProvider;

  public DatabaseModule_ProvideConversationDaoFactory(Provider<ChatDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public ConversationDao get() {
    return provideConversationDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideConversationDaoFactory create(
      Provider<ChatDatabase> databaseProvider) {
    return new DatabaseModule_ProvideConversationDaoFactory(databaseProvider);
  }

  public static ConversationDao provideConversationDao(ChatDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideConversationDao(database));
  }
}
