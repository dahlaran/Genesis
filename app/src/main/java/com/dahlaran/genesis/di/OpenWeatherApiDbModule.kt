package com.dahlaran.genesis.di

import android.content.Context
import androidx.room.Room
import com.dahlaran.genesis.data.database.OpenWeatherApiWeatherDao
import com.dahlaran.genesis.data.database.OpenWeatherApiWeatherDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
object OpenWeatherApiDbModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideOpenWeatherApiWeatherDatabase(context: Context): OpenWeatherApiWeatherDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            OpenWeatherApiWeatherDatabase::class.java,
            OpenWeatherApiWeatherDatabase.DB_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideShowDao(openWeatherApiWeatherDatabase: OpenWeatherApiWeatherDatabase): OpenWeatherApiWeatherDao {
        return openWeatherApiWeatherDatabase.openWeatherApiWeatherDao()
    }
}