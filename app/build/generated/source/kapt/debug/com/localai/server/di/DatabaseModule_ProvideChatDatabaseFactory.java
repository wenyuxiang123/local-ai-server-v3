package com.localai.server.di;

import android.content.Context;
import com.localai.server.data.local.ChatDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class DatabaseModule_ProvideChatDatabaseFactory implements Factory<ChatDatabase> {
  private final Provider<Context> contextProvider;

  public DatabaseModule_ProvideChatDatabaseFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public ChatDatabase get() {
    return provideChatDatabase(contextProvider.get());
  }

  public static DatabaseModule_ProvideChatDatabaseFactory create(
      Provider<Context> contextProvider) {
    return new DatabaseModule_ProvideChatDatabaseFactory(contextProvider);
  }

  public static ChatDatabase provideChatDatabase(Context context) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideChatDatabase(context));
  }
}
