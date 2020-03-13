package com.dahlaran.genesis.viewmodels

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dahlaran.genesis.data.OpenWeatherRepository
import com.dahlaran.genesis.models.OpenWeatherApiWeather

class WeatherViewModel(application: Application) : AndroidViewModel(application) {
    // ViewModels are related to android live cycle, so it possess an observer life cycle aware
    val weatherData: LiveData<OpenWeatherApiWeather?> = OpenWeatherRepository.getInstanceOfLiveData()

    fun getWeatherUsingCoordinate(location: Location?): LiveData<OpenWeatherApiWeather?> {
        location?.let {
            OpenWeatherRepository.getWeatherValue(it.latitude.toInt(), it.longitude.toInt())
        } ?: OpenWeatherRepository.getWeatherFromDatabase()

        return weatherData
    }
}