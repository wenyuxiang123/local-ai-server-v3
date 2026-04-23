package com.localai.server.data.repository;

import android.content.Context;
import com.localai.server.engine.LlamaEngine;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class AIRepositoryImpl_Factory implements Factory<AIRepositoryImpl> {
  private final Provider<Context> contextProvider;

  private final Provider<LlamaEngine> engineProvider;

  public AIRepositoryImpl_Factory(Provider<Context> contextProvider,
      Provider<LlamaEngine> engineProvider) {
    this.contextProvider = contextProvider;
    this.engineProvider = engineProvider;
  }

  @Override
  public AIRepositoryImpl get() {
    return newInstance(contextProvider.get(), engineProvider.get());
  }

  public static AIRepositoryImpl_Factory create(Provider<Context> contextProvider,
      Provider<LlamaEngine> engineProvider) {
    return new AIRepositoryImpl_Factory(contextProvider, engineProvider);
  }

  public static AIRepositoryImpl newInstance(Context context, LlamaEngine engine) {
    return new AIRepositoryImpl(context, engine);
  }
}
