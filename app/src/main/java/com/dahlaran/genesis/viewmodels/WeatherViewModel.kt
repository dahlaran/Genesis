package com.dahlaran.genesis.viewmodels

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dahlaran.genesis.data.OpenWeatherRepository
import com.dahlaran.genesis.models.OpenWeatherApiWeather


class WeatherViewModel(application: Application) : AndroidViewModel(application) {
    // ViewModels are related to android live cycle, so it possess an observer life cycle aware
    private lateinit var weatherData: LiveData<OpenWeatherApiWeather?>

    fun getWeatherUsingCoordinate(location: Location?): LiveData<OpenWeatherApiWeather?> {
        // If observer is already initialize, the observer don't have to be
        if (!::weatherData.isInitialized) {
            location?.let {
                weatherData = OpenWeatherRepository.initialiseWeatherValue(it.latitude.toInt(), it.longitude.toInt())
            }.run {
                weatherData = OpenWeatherRepository.getInstanceOfLiveData()
            }
        } else {
            location?.let {
                OpenWeatherRepository.initialiseWeatherValue(it.latitude.toInt(), it.longitude.toInt())
            }
        }
        return weatherData
    }
}