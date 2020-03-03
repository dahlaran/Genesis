package com.dahlaran.genesis.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dahlaran.genesis.models.OpenWeatherApiWeather

@Database(entities = [OpenWeatherApiWeather::class], version = 1, exportSchema = true)
@TypeConverters(ClassTypeConverter::class)
abstract class OpenWeatherApiWeatherDatabase : RoomDatabase() {

    abstract fun openWeatherApiWeatherDao(): OpenWeatherApiWeatherDao

    companion object {
        const val DB_NAME = "OpenWeatherApiWeather.db"
    }
}