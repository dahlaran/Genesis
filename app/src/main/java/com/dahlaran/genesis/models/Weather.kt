package com.dahlaran.genesis.models

import com.google.gson.annotations.SerializedName
import javax.inject.Inject

data class Weather @Inject constructor(
    @SerializedName("id") val id: Int,
    @SerializedName("main") val main: String,
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
)