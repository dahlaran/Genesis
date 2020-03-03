package com.dahlaran.genesis.di.application

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {

    @JvmStatic
    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }
}