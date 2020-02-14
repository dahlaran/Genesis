package com.dahlaran.genesis.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dahlaran.genesis.data.network.OpenWeatherApiManager
import com.dahlaran.genesis.models.OpenWeatherApiWeather
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver

object OpenWeatherRepository {

    // TODO: Save inside a room database (offline)

    private val openWeatherApiWeather: OpenWeatherApiManager = OpenWeatherApiManager()
    private val weatherLiveData = MutableLiveData<OpenWeatherApiWeather?>()
    private lateinit var weatherDisposable: Disposable

    fun initialiseWeatherValue(latitude: Int, longitude: Int): LiveData<OpenWeatherApiWeather?> {
        // Remove dispose if it has been already initialise
        removeDispose()
        weatherDisposable = openWeatherApiWeather.streamFetchUserFollowing(latitude, longitude).subscribeWith(object : DisposableObserver<OpenWeatherApiWeather>() {
            override fun onComplete() {
                Log.d(javaClass.name, "InitialiseWeatherValue: onComplete")
            }

            override fun onNext(t: OpenWeatherApiWeather) {
                Log.d(javaClass.name, "InitialiseWeatherValue: $t")
                updateWeatherLiveData(t)
            }

            override fun onError(e: Throwable) {
                Log.e(javaClass.name, "InitialiseWeatherValue: error occurred : " + e.message)
            }
        })

        return weatherLiveData
    }

    fun removeDispose() {
        if (this::weatherDisposable.isInitialized && !this.weatherDisposable.isDisposed) this.weatherDisposable.dispose()
    }

    fun updateWeatherLiveData(weather: OpenWeatherApiWeather) {
        if (weatherLiveData.value != weather) {
            weatherLiveData.postValue(weather)
        }
    }
}