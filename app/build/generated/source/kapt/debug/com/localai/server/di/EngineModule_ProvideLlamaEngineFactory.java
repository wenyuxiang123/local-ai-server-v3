package com.localai.server.di;

import android.content.Context;
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
public final class EngineModule_ProvideLlamaEngineFactory implements Factory<LlamaEngine> {
  private final Provider<Context> contextProvider;

  public EngineModule_ProvideLlamaEngineFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public LlamaEngine get() {
    return provideLlamaEngine(contextProvider.get());
  }

  public static EngineModule_ProvideLlamaEngineFactory create(Provider<Context> contextProvider) {
    return new EngineModule_ProvideLlamaEngineFactory(contextProvider);
  }

  public static LlamaEngine provideLlamaEngine(Context context) {
    return Preconditions.checkNotNullFromProvides(EngineModule.INSTANCE.provideLlamaEngine(context));
  }
}
