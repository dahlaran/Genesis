package com.dahlaran.genesis.view.activity

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dahlaran.genesis.utilis.LocationUtilis

abstract class BaseActivity : AppCompatActivity() {

    companion object {
        const val GPS_REQUEST_PERMISSION_CODE = 10
    }

    fun enableGps() {
        if (hasGpsPermission()) {
            LocationUtilis.resetLocation(this)
        } else {
            askGpsPermission()
            Toast.makeText(this, "Please Enable GPS First", Toast.LENGTH_LONG).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            GPS_REQUEST_PERMISSION_CODE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                    this,
                    "Permission granted, application can access GPS.",
                    Toast.LENGTH_LONG
                ).show()
                LocationUtilis.resetLocation(this)
            } else {
                Toast.makeText(
                    this,
                    "Permission canceled, application cannot access GPS.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun askGpsPermission() {
        if (hasGpsPermission()) {
            Toast.makeText(this, "ACCESS_FINE_LOCATION permission allows the app to access GPS", Toast.LENGTH_LONG).show()
            LocationUtilis.resetLocation(this)
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    ACCESS_FINE_LOCATION
                ), GPS_REQUEST_PERMISSION_CODE
            )
        }
    }

    private fun hasGpsPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
}