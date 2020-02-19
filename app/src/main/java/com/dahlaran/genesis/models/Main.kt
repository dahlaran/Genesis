package com.dahlaran.genesis.models

import com.google.gson.annotations.SerializedName

data class Main(
    // All temperatures are in kelvin
    @SerializedName("temp") val temp: Double,
    @SerializedName("feels_like") val feels_like: Double,
    @SerializedName("temp_min") val temp_min: Double,
    @SerializedName("temp_max") val temp_max: Double,

    // Pressure in Pa
    @SerializedName("pressure") val pressure: Int,

    // Humidity in %
    @SerializedName("humidity") val humidity: Int
)