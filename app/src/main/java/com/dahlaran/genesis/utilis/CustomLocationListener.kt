package com.dahlaran.genesis.utilis

import android.location.Location

interface CustomLocationListener {
    fun onLocationChange(location: Location)
}