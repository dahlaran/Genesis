package com.dahlaran.genesis.view

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.provider.Settings
import com.dahlaran.genesis.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class GenesisApplication : Application(), HasAndroidInjector {

    /*companion object {
        lateinit var instance: GenesisApplication
    }*/

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        /*instance = this*/

        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

    fun startLocationSourceSettingsActivity() {
        startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
    }
}