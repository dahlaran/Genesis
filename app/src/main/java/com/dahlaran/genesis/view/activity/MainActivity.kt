package com.dahlaran.genesis.view.activity

import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.dahlaran.genesis.BuildConfig
import com.dahlaran.genesis.R
import com.dahlaran.genesis.utilis.LocationUtilis
import com.dahlaran.genesis.viewmodels.ViewModelFactory
import com.dahlaran.genesis.viewmodels.WeatherViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    // Observer to get new weather value, if it change
    private val locationObserver by lazy {
        Observer<Location?> { location ->
            currentLocation = location
            getWeather()
        }
    }
    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory

    private var currentLocation: Location? = null
    private lateinit var weatherViewModel: WeatherViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        setContentView(R.layout.activity_main)

        weatherViewModel = ViewModelProvider(this, viewModelFactory).get(WeatherViewModel::class.java)

        // Check if locationUtilis has been launched
        if (!LocationUtilis.hasBeenSuccessfullyLaunch) {
            // Use BaseActivity to initialize the LocationUtilis
            enableGps()
            LocationUtilis.location.observe(this, locationObserver)
        }

        weatherSwipeRefresh.setOnRefreshListener {
            getWeather()
        }

        weatherViewModel.weatherData.observe(this,
            Observer { weather ->
                if (BuildConfig.DEBUG) {
                    Log.d(javaClass.simpleName, "weatherObserver  = $weather")
                }
                current_weather_layout.setNewValue(weather)
                weatherSwipeRefresh.isRefreshing = false
            })
    }

    override fun onDestroy() {
        LocationUtilis.location.removeObserver { locationObserver }
        super.onDestroy()
    }

    private fun getWeather() {
        weatherViewModel.getWeatherUsingCoordinate(currentLocation)
    }
}
