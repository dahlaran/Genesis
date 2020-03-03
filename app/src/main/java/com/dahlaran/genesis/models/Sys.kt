package com.dahlaran.genesis.models

import com.google.gson.annotations.SerializedName
import javax.inject.Inject

data class Sys @Inject constructor(
    @SerializedName("type") val type: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("message") val message: Double,
    @SerializedName("country") val country: String,
    @SerializedName("sunrise") val sunrise: Int,
    @SerializedName("sunset") val sunset: Int
)