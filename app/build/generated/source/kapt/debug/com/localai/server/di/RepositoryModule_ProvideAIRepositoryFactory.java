package com.localai.server.di;

import android.content.Context;
import com.localai.server.domain.repository.AIRepository;
import com.localai.server.engine.LlamaEngine;
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
public final class RepositoryModule_ProvideAIRepositoryFactory implements Factory<AIRepository> {
  private final Provider<Context> contextProvider;

  private final Provider<LlamaEngine> engineProvider;

  public RepositoryModule_ProvideAIRepositoryFactory(Provider<Context> contextProvider,
      Provider<LlamaEngine> engineProvider) {
    this.contextProvider = contextProvider;
    this.engineProvider = engineProvider;
  }

  @Override
  public AIRepository get() {
    return provideAIRepository(contextProvider.get(), engineProvider.get());
  }

  public static RepositoryModule_ProvideAIRepositoryFactory create(
      Provider<Context> contextProvider, Provider<LlamaEngine> engineProvider) {
    return new RepositoryModule_ProvideAIRepositoryFactory(contextProvider, engineProvider);
  }

  public static AIRepository provideAIRepository(Context context, LlamaEngine engine) {
    return Preconditions.checkNotNullFromProvides(RepositoryModule.INSTANCE.provideAIRepository(context, engine));
  }
}
