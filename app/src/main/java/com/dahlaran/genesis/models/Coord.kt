package com.dahlaran.genesis.models

import com.google.gson.annotations.SerializedName
import javax.inject.Inject

data class Coord @Inject constructor(
    @SerializedName("lon") val lon: Int,
    @SerializedName("lat") val lat: Int
)