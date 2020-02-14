package com.dahlaran.genesis.data.network

import com.dahlaran.genesis.models.OpenWeatherApiWeather
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface OpenWeatherApiService {

    @GET("weather")
    fun getWeatherAtLocation(
        @Query("lat") latitude: Int,
        @Query("lon") longitude: Int,
        @Query("appid") appid: String
    ): Observable<OpenWeatherApiWeather>

    companion object {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}