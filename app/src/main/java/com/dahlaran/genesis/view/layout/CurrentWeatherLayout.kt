package com.dahlaran.genesis.view.layout

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.cardview.widget.CardView
import com.dahlaran.genesis.R
import com.dahlaran.genesis.models.OpenWeatherApiWeather
import com.dahlaran.genesis.utilis.ImageUtilis
import com.dahlaran.genesis.utilis.TemperatureUtilis.Companion.convertKelvinToCelcius
import com.dahlaran.genesis.utilis.WeatherUtilis
import kotlinx.android.synthetic.main.current_weather_layout.view.*

class CurrentWeatherLayout : CardView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var weather: OpenWeatherApiWeather? = null

    init {
        inflate(context, R.layout.current_weather_layout, this)
        hideViews()
        // TODO: add thermometer with colors and size change depending of the temperature
        current_weather_temperature_image.setOnCreateContextMenuListener { menu, v, menuInfo ->
            weather?.main?.let {
                menu.add(context.getString(R.string.temperature_menu, convertKelvinToCelcius(it.temp)))
                menu.add(context.getString(R.string.feels_like_menu, convertKelvinToCelcius(it.feels_like)))
                menu.add(context.getString(R.string.temp_min_menu, convertKelvinToCelcius(it.temp_min)))
                menu.add(context.getString(R.string.temp_max_menu, convertKelvinToCelcius(it.temp_max)))
            }
        }

        current_weather_cloud_image.setOnCreateContextMenuListener { menu, v, menuInfo ->
            weather?.let {
                menu.add(context.getString(R.string.cloud_all_menu, it.clouds.all))
                menu.add(context.getString(R.string.pressure_menu, it.main.pressure))
                menu.add(context.getString(R.string.humidity_menu, it.main.humidity))
            }
        }

        // TODO: add(inside menu)/change(icon) orientation of wind depending of the wind orientation (wind.deg)
        current_weather_wind_image.setOnCreateContextMenuListener { menu, v, menuInfo ->
            weather?.let {
                menu.add(context.getString(R.string.speed, WeatherUtilis.ktsToKmH(it.wind.speed)))
                menu.add(context.getString(R.string.deg, it.wind.deg))
            }
        }
    }

    fun setNewValue(newWeather: OpenWeatherApiWeather?) {
        weather = newWeather
        updateView()
    }

    private fun updateView() {
        weather?.let {
            current_weather_description.visibility = View.VISIBLE
            current_weather_hour.visibility = View.VISIBLE

            // TODO: change time zone to be the time when the call is received
            current_weather_hour.text = it.timezone.toString()
            if (it.weather.isNotEmpty()) {
                // There is a list of weather but I don't know why ?
                ImageUtilis.setImageToImageView(current_weather_image, it.weather[0].icon)
                current_weather_description.text = Character.toUpperCase(it.weather[0].description[0]) + it.weather[0].description.substring(1)
            } else {
                ImageUtilis.setImageToImageView(current_weather_image, null)
                current_weather_description.visibility = View.GONE
            }
        } ?: run {
            hideViews()
        }
    }

    private fun hideViews() {
        ImageUtilis.setImageToImageView(current_weather_image, null)
        current_weather_description.visibility = View.GONE
        current_weather_hour.visibility = View.GONE
    }
}