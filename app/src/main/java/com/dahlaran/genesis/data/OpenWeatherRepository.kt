package com.dahlaran.genesis.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dahlaran.genesis.BuildConfig
import com.dahlaran.genesis.data.database.OpenWeatherApiWeatherDatabase
import com.dahlaran.genesis.data.network.OpenWeatherApiManager
import com.dahlaran.genesis.models.OpenWeatherApiWeather
import com.dahlaran.genesis.view.GenesisApplication
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

object OpenWeatherRepository {
    // LiveData who contain weather
    private val weatherLiveData = MutableLiveData<OpenWeatherApiWeather?>()

    // Room
    private val openWeatherApiWeatherDao = OpenWeatherApiWeatherDatabase.getInstance(GenesisApplication.instance.applicationContext).openWeatherApiWeatherDao()

    // Network
    private val openWeatherApiWeather: OpenWeatherApiManager = OpenWeatherApiManager()
    private lateinit var weatherDisposable: Disposable

    fun initialiseWeatherValue(latitude: Int, longitude: Int): LiveData<OpenWeatherApiWeather?> {
        // Get weather stored inside database
        getWeatherFromDatabase()
        // Remove dispose if it has been already initialise
        removeDispose()
        weatherDisposable = openWeatherApiWeather.streamFetchUserFollowing(latitude, longitude).subscribeWith(object : DisposableObserver<OpenWeatherApiWeather>() {
            override fun onComplete() {
                if (BuildConfig.DEBUG) {
                    Log.d(javaClass.simpleName, "onComplete")
                }
            }

            override fun onNext(t: OpenWeatherApiWeather) {
                if (BuildConfig.DEBUG) {
                    Log.d(javaClass.simpleName, "OnNext")
                }
                t.timeOfCall = System.currentTimeMillis()
                insert(t)
                updateWeatherLiveData(t)
            }

            override fun onError(e: Throwable) {
                Log.e(javaClass.simpleName, "Error occurred : " + e.message)
            }
        })

        return weatherLiveData
    }

    fun getWeatherFromDatabase(): LiveData<OpenWeatherApiWeather?> {
        val coroutineScope = CoroutineScope(Dispatchers.Default + Job())

        coroutineScope.launch {
            val weather = openWeatherApiWeatherDao.getWeather()
            updateWeatherLiveData(weather)
        }
        return weatherLiveData
    }

    fun insert(weather: OpenWeatherApiWeather) {
        val coroutineScope = CoroutineScope(Dispatchers.Default + Job())

        coroutineScope.launch {
            openWeatherApiWeatherDao.insert(weather)
        }
    }

    private fun removeDispose() {
        if (this::weatherDisposable.isInitialized && !this.weatherDisposable.isDisposed) this.weatherDisposable.dispose()
    }

    fun updateWeatherLiveData(weather: OpenWeatherApiWeather?) {
        if (weather == null && weatherLiveData.value == null) {
            weatherLiveData.postValue(weather)
            if (BuildConfig.DEBUG) {
                Log.d(javaClass.simpleName, "Weather updated")
            }
        } else if (weather != null) {
            if (weatherLiveData.value == null || weather.timeOfCall > weatherLiveData.value!!.timeOfCall) {
                weatherLiveData.postValue(weather)
                if (BuildConfig.DEBUG) {
                    Log.d(javaClass.simpleName, "Weather updated")
                }
            } else {
                if (BuildConfig.DEBUG) {
                    Log.d(javaClass.simpleName, "Weather not updated")
                }
            }
        } else {
            if (BuildConfig.DEBUG) {
                Log.d(javaClass.simpleName, "Weather not updated")
            }
        }
    }

    fun getInstanceOfLiveData(): LiveData<OpenWeatherApiWeather?> {
        return weatherLiveData
    }
}