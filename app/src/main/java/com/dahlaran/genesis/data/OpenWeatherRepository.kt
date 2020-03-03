package com.dahlaran.genesis.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dahlaran.genesis.BuildConfig
import com.dahlaran.genesis.data.database.OpenWeatherApiWeatherDao
import com.dahlaran.genesis.data.network.OpenWeatherApiManager
import com.dahlaran.genesis.models.OpenWeatherApiWeather
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OpenWeatherRepository @Inject
constructor(private val openWeatherApiWeatherDao: OpenWeatherApiWeatherDao) {
    // LiveData who contain weather
    private val weatherLiveData = MutableLiveData<OpenWeatherApiWeather?>()

    // Network
    private val openWeatherApiWeather: OpenWeatherApiManager = OpenWeatherApiManager()
    private lateinit var weatherDisposable: Disposable

    fun getWeatherValue(latitude: Int, longitude: Int): LiveData<OpenWeatherApiWeather?> {
        if (weatherLiveData.value != null && !needToMakeCall(weatherLiveData.value!!.timeOfCall)) {
            // Post value to trigger the remove of loader
            weatherLiveData.postValue(weatherLiveData.value)
            return weatherLiveData
        }

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
            } else if (BuildConfig.DEBUG) {
                Log.d(javaClass.simpleName, "Weather not updated")
            }
        } else if (BuildConfig.DEBUG) {
            Log.d(javaClass.simpleName, "Weather not updated")
        }
    }

    fun getInstanceOfLiveData(): LiveData<OpenWeatherApiWeather?> {
        return weatherLiveData
    }

    private fun removeDispose() {
        if (this::weatherDisposable.isInitialized && !this.weatherDisposable.isDisposed) this.weatherDisposable.dispose()
    }

    /**
     * Method to know if a call is necessary to be launch,
     * if at least a minute is not spend, no need to request an update
     * @param lastCallTime time of the last call in millisecond
     */
    private fun needToMakeCall(lastCallTime: Long): Boolean {
        val currentTime = System.currentTimeMillis()

        // 60000 = 1 minute
        return currentTime > lastCallTime + 60000
    }
}