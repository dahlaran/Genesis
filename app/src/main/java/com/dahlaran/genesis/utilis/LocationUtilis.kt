package com.dahlaran.genesis.utilis

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.dahlaran.genesis.BuildConfig
import com.dahlaran.genesis.view.GenesisApplication


// Singleton who possess the location of the phone inside a LiveData
object LocationUtilis : LocationListener {
    var hasBeenSuccessfullyLaunch = false
    var location = MutableLiveData<Location?>()
    private lateinit var locationManager: LocationManager
    private var locationProvider: String? = null

    private val oneShotRequestPosition = object : LocationListener {
        override fun onLocationChanged(location: Location?) {
            locationChanged(location)
            locationManager.removeUpdates(this)
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            if (BuildConfig.DEBUG) {
                Log.d(javaClass.simpleName, "onStatusChanged")
            }
        }

        override fun onProviderEnabled(provider: String?) {
            if (BuildConfig.DEBUG) {
                Log.d(javaClass.simpleName, "onProviderEnabled")
            }
        }

        override fun onProviderDisabled(provider: String?) {
            GenesisApplication.instance.startLocationSourceSettingsActivity()
        }
    }

    // TODO: Use GPS Provider if network Provider don't work and vise versa

    fun resetLocation(context: Context): Boolean {
        if (::locationManager.isInitialized) {
            locationManager.removeUpdates(this)
        }
        checkProviderStatus(context)

        // If it don't have been initialized, don't go any further a problem occurred
        if (!::locationManager.isInitialized) {
            return false
        }

        try {
            // TODO: remove this to manage GPS_Provider
            locationProvider = LocationManager.NETWORK_PROVIDER
            locationProvider?.let { provider ->
                location.value = locationManager.getLastKnownLocation(provider)
                locationManager.requestLocationUpdates(provider, 10000, 5f, this)
                hasBeenSuccessfullyLaunch = true
            }

        } catch (exception: SecurityException) {
            return false
        }
        return true
    }

    /**
     * Initialise the location Manager and set locationProvider to one of the LocationProvider enable (LocationManager.GPS_PROVIDER or LocationManager.NETWORK_PROVIDER)
     */
    private fun checkProviderStatus(context: Context): Boolean {
        // The service that we want is LOCATION_SERVICE
        val systemServiceTmp = context.getSystemService(Context.LOCATION_SERVICE)

        // Check if the service returned is the type of LocationManager (to be sure)
        if (systemServiceTmp is LocationManager) {
            locationManager = systemServiceTmp

            // TODO: Add GPS Provider
            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                locationProvider = LocationManager.NETWORK_PROVIDER
            }

            return !TextUtils.isEmpty(locationProvider)
        }
        return false
    }

    override fun onLocationChanged(location: Location?) {
        // When location changed
        locationChanged(location)
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        if (BuildConfig.DEBUG) {
            Log.d(javaClass.simpleName, "onStatusChanged")
        }
    }

    override fun onProviderEnabled(provider: String?) {
        if (BuildConfig.DEBUG) {
            Log.d(javaClass.simpleName, "onProviderEnabled")
        }
    }

    override fun onProviderDisabled(provider: String?) {
        GenesisApplication.instance.startLocationSourceSettingsActivity()
    }

    fun requestCurrentPosition(context: Context): Boolean {
        if (::locationManager.isInitialized) {
            locationManager.removeUpdates(oneShotRequestPosition)
        }
        checkProviderStatus(context)

        // If it don't have been initialized, don't go any further a problem occurred
        if (!::locationManager.isInitialized) {
            return false
        }

        try {
            locationProvider?.let {
                locationManager.removeUpdates(oneShotRequestPosition)
                locationManager.requestLocationUpdates(it, 1, 0f, oneShotRequestPosition)
                return true
            } ?: run {
                return false
            }
        } catch (exception: SecurityException) {
            return false
        }
    }

    fun locationChanged(newLocation: Location?) {
        if (BuildConfig.DEBUG) {
            Log.d(javaClass.simpleName, "Latitude = " + newLocation?.latitude + "  longitude = " + newLocation?.longitude)
        }
        this.location.postValue(newLocation)
    }
}