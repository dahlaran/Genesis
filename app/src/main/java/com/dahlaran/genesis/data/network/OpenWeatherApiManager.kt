package com.dahlaran.genesis.data.network

import com.dahlaran.genesis.models.OpenWeatherApiWeather
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class OpenWeatherApiManager {
    companion object {
        private const val OPEN_WEATHER_API = "b20b7b43e3aa4055f15125139db666ff"
        private const val TIME_MAX: Long = 10
    }

    fun streamFetchUserFollowing(latitude: Int, longitude: Int): Observable<OpenWeatherApiWeather> {
        val openWeatherApiService: OpenWeatherApiService =
            OpenWeatherApiService.retrofit.create(OpenWeatherApiService::class.java)

        return openWeatherApiService.getWeatherAtLocation(latitude, longitude, OPEN_WEATHER_API)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .timeout(TIME_MAX, TimeUnit.SECONDS)
    }
}