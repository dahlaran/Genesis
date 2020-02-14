package com.dahlaran.genesis.view.activity

import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dahlaran.genesis.R
import com.dahlaran.genesis.utilis.LocationUtilis
import com.dahlaran.genesis.viewmodels.WeatherViewModel

class MainActivity : BaseActivity() {

    // Observer to get new weather value, if it change
    private val locationObserver by lazy {
        Observer<Location?> { location ->
            weatherViewModel.getWeatherUsingCoordinate(location).observe(this,
                Observer { weather ->
                    // New weather value
                    Toast.makeText(this, "weather = " + weather?.id, Toast.LENGTH_LONG).show()
                    Log.d("MainActivity", "weatherObserver  = $weather")
                })
        }
    }

    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        // Check if locationUtilis has been launched
        if (!LocationUtilis.hasBeenSuccessfullyLaunch) {
            // Use BaseActivity to initialize the LocationUtilis
            enableGps()
            LocationUtilis.location.observe(this, locationObserver)
        }
    }

    override fun onDestroy() {
        LocationUtilis.location.removeObserver { locationObserver }
        super.onDestroy()
    }
}
