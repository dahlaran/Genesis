package com.dahlaran.genesis.data.database

import androidx.room.*
import com.dahlaran.genesis.models.OpenWeatherApiWeather

@Dao
interface OpenWeatherApiWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weather: OpenWeatherApiWeather?)

    @Update
    suspend fun update(weather: OpenWeatherApiWeather)

    @Delete
    suspend fun delete(weather: OpenWeatherApiWeather)

    @Query("DELETE FROM OpenWeatherApiWeather_table")
    suspend fun deleteAllWeather()

    @Query("SELECT * FROM OpenWeatherApiWeather_table ORDER BY ID DESC")
    suspend fun getWeather(): OpenWeatherApiWeather?
}