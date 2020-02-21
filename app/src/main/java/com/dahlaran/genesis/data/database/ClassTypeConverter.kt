package com.dahlaran.genesis.data.database

import android.text.TextUtils
import androidx.room.TypeConverter
import com.dahlaran.genesis.models.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


// Convert custom type to string to be store inside database
class ClassTypeConverter {
    // Clouds
    @TypeConverter
    fun stringToClouds(string: String): Clouds? {
        if (TextUtils.isEmpty(string))
            return null
        return Gson().fromJson(string, Clouds::class.java)
    }

    @TypeConverter
    fun cloudsToString(clouds: Clouds): String {
        return Gson().toJson(clouds)
    }

    // Coord
    @TypeConverter
    fun stringToCoord(string: String): Coord? {
        if (TextUtils.isEmpty(string))
            return null
        return Gson().fromJson(string, Coord::class.java)
    }

    @TypeConverter
    fun coordToString(coord: Coord): String {
        return Gson().toJson(coord)
    }

    // Main
    @TypeConverter
    fun stringToMain(string: String): Main? {
        if (TextUtils.isEmpty(string))
            return null
        return Gson().fromJson(string, Main::class.java)
    }

    @TypeConverter
    fun mainToString(main: Main): String {
        return Gson().toJson(main)
    }

    // Sys
    @TypeConverter
    fun stringToSys(string: String): Sys? {
        if (TextUtils.isEmpty(string))
            return null
        return Gson().fromJson(string, Sys::class.java)
    }

    @TypeConverter
    fun sysToString(sys: Sys): String {
        return Gson().toJson(sys)
    }

    // Weather list
    @TypeConverter
    fun stringToWeatherList(string: String): List<Weather>? {
        if (TextUtils.isEmpty(string))
            return null
        val listType = object : TypeToken<List<Weather?>?>() {}.type
        return Gson().fromJson(string, listType)
    }

    @TypeConverter
    fun weatherListToString(weatherList: List<Weather>): String {
        return Gson().toJson(weatherList)
    }

    // Wind
    @TypeConverter
    fun stringToWind(string: String): Wind? {
        if (TextUtils.isEmpty(string))
            return null
        return Gson().fromJson(string, Wind::class.java)
    }

    @TypeConverter
    fun windToString(wind: Wind): String {
        return Gson().toJson(wind)
    }
}