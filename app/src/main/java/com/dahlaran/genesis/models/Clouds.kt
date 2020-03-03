package com.dahlaran.genesis.models

import com.google.gson.annotations.SerializedName
import javax.inject.Inject


data class Clouds @Inject constructor(
    @SerializedName("all") val all: Int
)