package com.dahlaran.genesis.viewmodels

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dahlaran.genesis.data.OpenWeatherRepository
import com.dahlaran.genesis.models.OpenWeatherApiWeather
import javax.inject.Inject

class WeatherViewModel @Inject constructor(private val openWeatherRepository: OpenWeatherRepository) : ViewModel() {
    // ViewModels are related to android live cycle, so it possess an observer life cycle aware
    val weatherData: LiveData<OpenWeatherApiWeather?> = openWeatherRepository.getInstanceOfLiveData()

    fun getWeatherUsingCoordinate(location: Location?): LiveData<OpenWeatherApiWeather?> {
        location?.let {
            openWeatherRepository.getWeatherValue(it.latitude.toInt(), it.longitude.toInt())
        } ?: run {
            openWeatherRepository.getWeatherFromDatabase()
        }
        return weatherData
    }
}