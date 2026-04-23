package com.localai.server.data.repository;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class ChatApiService_Factory implements Factory<ChatApiService> {
  @Override
  public ChatApiService get() {
    return newInstance();
  }

  public static ChatApiService_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static ChatApiService newInstance() {
    return new ChatApiService();
  }

  private static final class InstanceHolder {
    private static final ChatApiService_Factory INSTANCE = new ChatApiService_Factory();
  }
}
