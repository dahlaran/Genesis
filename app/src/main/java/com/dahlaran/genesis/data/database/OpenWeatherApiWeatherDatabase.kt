package com.dahlaran.genesis.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dahlaran.genesis.models.OpenWeatherApiWeather

@Database(entities = [OpenWeatherApiWeather::class], version = 1, exportSchema = true)
@TypeConverters(ClassTypeConverter::class)
abstract class OpenWeatherApiWeatherDatabase : RoomDatabase() {

    abstract fun openWeatherApiWeatherDao(): OpenWeatherApiWeatherDao

    companion object {
        private var instance: OpenWeatherApiWeatherDatabase? = null

        fun getInstance(context: Context): OpenWeatherApiWeatherDatabase {
            if (instance == null) {
                // If multiple call
                synchronized(OpenWeatherApiWeatherDatabase::class) {
                    // Prevent to have multiple instance of database (https://portabledroid.wordpress.com/2018/08/09/fast-locking-in-android-with-kotlin/)
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            OpenWeatherApiWeatherDatabase::class.java, "OpenWeatherApiWeather.db"
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return instance!!
        }
    }
}