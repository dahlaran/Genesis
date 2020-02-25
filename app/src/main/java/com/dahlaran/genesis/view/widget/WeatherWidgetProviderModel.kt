package com.dahlaran.genesis.view.widget

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.dahlaran.genesis.models.OpenWeatherApiWeather
import com.dahlaran.genesis.presenters.WidgetPresenter
import com.dahlaran.genesis.presenters.WidgetPresenterInterface
import com.dahlaran.genesis.utilis.LocationUtilis

class WeatherWidgetProviderModel {

    companion object {
        private var instance: WeatherWidgetProviderModel? = null
        fun getInstance(context: Context): WeatherWidgetProviderModel {
            if (instance == null) {
                synchronized(WeatherWidgetProviderModel::class) {
                    // Prevent multiple instances (https://portabledroid.wordpress.com/2018/08/09/fast-locking-in-android-with-kotlin/)
                    if (instance == null) {
                        instance = WeatherWidgetProviderModel()
                        instance!!.initializeLiveData(context)
                    }
                }
            }
            return instance!!
        }
    }

    lateinit var presenter: WidgetPresenterInterface
    lateinit var weatherLiveData: LiveData<OpenWeatherApiWeather?>
    lateinit var weatherLiveDataObserver: Observer<OpenWeatherApiWeather?>
    lateinit var locationObserver: Observer<Location?>

    // Initialize LiveData and Observable
    private fun initializeLiveData(context: Context) {
        if (!::presenter.isInitialized) {
            presenter = WidgetPresenter()
        }

        if (!::weatherLiveDataObserver.isInitialized) {
            weatherLiveDataObserver = Observer {

                // Create intent and broadcast to notify widgets when data changes
                val updateIntent = Intent(context, WeatherWidgetProvider::class.java)
                updateIntent.action = WeatherWidgetProvider.ACTION_UPDATE_DATA
                val pendingIntent = PendingIntent.getBroadcast(
                    context, 0, updateIntent, PendingIntent.FLAG_UPDATE_CURRENT
                )

                // Send broadcast to update all widgets
                pendingIntent.send()
            }
        }
        if (!::weatherLiveData.isInitialized) {
            weatherLiveData = presenter.getLiveData()
        }

        if (!::locationObserver.isInitialized) {
            locationObserver = Observer { location ->
                Log.d("WeatherWidgetProvider", "location = " + location)

                if (::presenter.isInitialized) {
                    Log.d("WeatherWidgetProvider", "presenter.isInitialized")
                    presenter.getWeatherUsingCoordinate(location)
                } else {
                    Log.d("WeatherWidgetProvider", "presenter.notInithialized")
                }
            }
        }

        LocationUtilis.location.observeForever {
            locationObserver
        }

        // Observe data to be notify when the data change
        weatherLiveData.observeForever {
            weatherLiveDataObserver
        }
    }

    fun removeLiveDataObserver() {
        if (::weatherLiveDataObserver.isInitialized) {
            weatherLiveData.removeObserver {
                weatherLiveDataObserver
            }
        }
        if (::locationObserver.isInitialized) {
            LocationUtilis.location.removeObserver {
                locationObserver
            }
        }
        instance = null
    }
}