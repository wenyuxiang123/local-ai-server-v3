package com.localai.server.service;

import com.localai.server.engine.LlamaEngine;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class AIService_MembersInjector implements MembersInjector<AIService> {
  private final Provider<LlamaEngine> engineProvider;

  public AIService_MembersInjector(Provider<LlamaEngine> engineProvider) {
    this.engineProvider = engineProvider;
  }

  public static MembersInjector<AIService> create(Provider<LlamaEngine> engineProvider) {
    return new AIService_MembersInjector(engineProvider);
  }

  @Override
  public void injectMembers(AIService instance) {
    injectEngine(instance, engineProvider.get());
  }

  @InjectedFieldSignature("com.localai.server.service.AIService.engine")
  public static void injectEngine(AIService instance, LlamaEngine engine) {
    instance.engine = engine;
  }
}
