package com.localai.server.di;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import java.io.File;
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
public final class AppModule_ProvideModelDirFactory implements Factory<File> {
  private final Provider<Context> contextProvider;

  public AppModule_ProvideModelDirFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public File get() {
    return provideModelDir(contextProvider.get());
  }

  public static AppModule_ProvideModelDirFactory create(Provider<Context> contextProvider) {
    return new AppModule_ProvideModelDirFactory(contextProvider);
  }

  public static File provideModelDir(Context context) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideModelDir(context));
  }
}
