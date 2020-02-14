package com.dahlaran.genesis.view

import android.app.Application
import android.content.Intent
import android.provider.Settings

class GenesisApplication : Application() {

    companion object {
        lateinit var instance: GenesisApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun startLocationSourceSettingsActivity() {
        startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
    }
}