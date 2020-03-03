package com.dahlaran.genesis.presenters

import android.location.Location
import androidx.lifecycle.LiveData
import com.dahlaran.genesis.data.OpenWeatherRepository
import com.dahlaran.genesis.models.OpenWeatherApiWeather
import javax.inject.Inject

class WidgetPresenter @Inject constructor(private val openWeatherRepository: OpenWeatherRepository) : WidgetPresenterInterface {

    override fun getLiveData(): LiveData<OpenWeatherApiWeather?> {
        return openWeatherRepository.getInstanceOfLiveData()
    }

    override fun getWeatherUsingCoordinate(location: Location?) {
        location?.let {
            openWeatherRepository.getWeatherValue(it.latitude.toInt(), it.longitude.toInt())
        } ?: run {
            openWeatherRepository.getWeatherFromDatabase()
        }
    }
}