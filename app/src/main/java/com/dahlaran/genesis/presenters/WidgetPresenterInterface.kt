package com.dahlaran.genesis.presenters

import android.location.Location
import androidx.lifecycle.LiveData
import com.dahlaran.genesis.models.OpenWeatherApiWeather

interface WidgetPresenterInterface {

    fun getLiveData(): LiveData<OpenWeatherApiWeather?>
    fun getWeatherUsingCoordinate(location: Location?)

}