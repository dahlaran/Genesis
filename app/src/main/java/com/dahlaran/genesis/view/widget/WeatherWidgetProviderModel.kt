package com.dahlaran.genesis.view.widget

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.dahlaran.genesis.BuildConfig
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

    var weatherLiveData: LiveData<OpenWeatherApiWeather?>? = null
    lateinit var presenter: WidgetPresenterInterface

    private lateinit var locationObserver: Observer<Location?>
    // Single observer
    private lateinit var weatherLiveDataObserver: Observer<OpenWeatherApiWeather?>

    // Initialize LiveData and Observable
    private fun initializeLiveData(context: Context) {
        if (!::presenter.isInitialized) {
            presenter = WidgetPresenter()
        }

        if (!::weatherLiveDataObserver.isInitialized) {
            weatherLiveDataObserver = Observer {

                if (BuildConfig.DEBUG) {
                    Log.d(javaClass.simpleName, "Weather observer updated")
                }
                // Create intent and broadcast to notify widgets when data changes
                val updateIntent = Intent(context, WeatherWidgetProvider::class.java)
                updateIntent.action = WeatherWidgetProvider.ACTION_UPDATE_DATA
                val pendingIntent = PendingIntent.getBroadcast(
                    context, 0, updateIntent, PendingIntent.FLAG_UPDATE_CURRENT
                )

                // Send broadcast to update all widgets
                pendingIntent.send()

                // Remove observer
                removeWeatherDataObserver()
            }
        }

        if (!::locationObserver.isInitialized) {
            locationObserver = Observer { location ->
                if (BuildConfig.DEBUG) {
                    Log.d(javaClass.simpleName, "Observe location = $location")
                }
                if (!::presenter.isInitialized) {
                    presenter = WidgetPresenter()
                }
                singleObserverToPresenterWeatherLiveData(context)
                presenter.getWeatherUsingCoordinate(location)
            }
        }

        LocationUtilis.location.observeForever(locationObserver)
    }

    fun singleObserverToPresenterWeatherLiveData(context: Context) {
        if (BuildConfig.DEBUG) {
            Log.d(javaClass.simpleName, "singleObserverToPresenterWeatherLiveData")
        }

        if (!::weatherLiveDataObserver.isInitialized) {
            initializeLiveData(context)
        }

        removeWeatherDataObserver()
        weatherLiveData = presenter.getLiveData()

        weatherLiveData?.observeForever(weatherLiveDataObserver)
    }

    fun removeLiveDataObserver() {
        if (BuildConfig.DEBUG) {
            Log.d(javaClass.simpleName, "removeLiveDataObserver")
        }
        removeWeatherDataObserver()
        if (::locationObserver.isInitialized) {
            LocationUtilis.location.removeObserver(locationObserver)
        }
        instance = null
    }

    private fun removeWeatherDataObserver() {
        if (BuildConfig.DEBUG) {
            Log.d(javaClass.simpleName, "removeWeatherDataObserver")
        }
        if (::weatherLiveDataObserver.isInitialized) {
            weatherLiveData?.removeObserver(weatherLiveDataObserver)
        }
    }
}