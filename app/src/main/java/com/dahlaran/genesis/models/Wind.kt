package com.dahlaran.genesis.models

import com.google.gson.annotations.SerializedName

data class Wind(
    // Speed in kts
    @SerializedName("speed") val speed: Double,
    // South is 0, West is 90, North is 180, Est is 270
    @SerializedName("deg") val deg: Double
)