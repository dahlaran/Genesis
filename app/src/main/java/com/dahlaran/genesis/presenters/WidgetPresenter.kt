package com.dahlaran.genesis.presenters

import android.location.Location
import androidx.lifecycle.LiveData
import com.dahlaran.genesis.data.OpenWeatherRepository
import com.dahlaran.genesis.models.OpenWeatherApiWeather

class WidgetPresenter : WidgetPresenterInterface {

    override fun getLiveData(): LiveData<OpenWeatherApiWeather?> {
        return OpenWeatherRepository.getInstanceOfLiveData()
    }

    override fun getWeatherUsingCoordinate(location: Location?) {
        location?.let {
            OpenWeatherRepository.initialiseWeatherValue(it.latitude.toInt(), it.longitude.toInt())
        } ?: run {
            OpenWeatherRepository.getWeatherFromDatabase()
        }
    }
}