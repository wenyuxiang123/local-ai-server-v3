package com.localai.server.di

import android.content.Context
import com.localai.server.data.repository.AIRepositoryImpl
import com.localai.server.domain.repository.AIRepository
import com.localai.server.engine.LlamaEngine
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context = context
    
    @Provides
    @Singleton
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
    
    @Provides
    @Singleton
    fun provideModelDir(context: Context): File {
        return File(context.filesDir, "models").apply { mkdirs() }
    }
}

@Module
@InstallIn(SingletonComponent::class)
object EngineModule {
    
    @Provides
    @Singleton
    fun provideLlamaEngine(context: Context): LlamaEngine {
        return LlamaEngine(context)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    
    @Provides
    @Singleton
    fun provideAIRepository(
        context: Context,
        engine: LlamaEngine
    ): AIRepository {
        return AIRepositoryImpl(context, engine)
    }
}
