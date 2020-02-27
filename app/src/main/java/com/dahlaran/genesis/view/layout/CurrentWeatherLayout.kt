package com.dahlaran.genesis.view.layout

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.cardview.widget.CardView
import com.dahlaran.genesis.R
import com.dahlaran.genesis.models.OpenWeatherApiWeather
import com.dahlaran.genesis.utilis.ImageUtilis
import com.dahlaran.genesis.utilis.TemperatureUtilis.Companion.convertKelvinToCelcius
import com.dahlaran.genesis.utilis.TimeUtilis
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
        currentWeatherTemperatureImage.setOnCreateContextMenuListener { menu, v, menuInfo ->
            weather?.main?.let {
                menu.add(context.getString(R.string.temperature_menu, convertKelvinToCelcius(it.temp)))
                menu.add(context.getString(R.string.feels_like_menu, convertKelvinToCelcius(it.feels_like)))
                menu.add(context.getString(R.string.temp_min_menu, convertKelvinToCelcius(it.temp_min)))
                menu.add(context.getString(R.string.temp_max_menu, convertKelvinToCelcius(it.temp_max)))
            }
        }

        currentWeatherCloudImage.setOnCreateContextMenuListener { menu, v, menuInfo ->
            weather?.let {
                menu.add(context.getString(R.string.cloud_all_menu, it.clouds.all))
                menu.add(context.getString(R.string.pressure_menu, it.main.pressure))
                menu.add(context.getString(R.string.humidity_menu, it.main.humidity))
            }
        }

        // TODO: add(inside menu)/change(icon) orientation of wind depending of the wind orientation (wind.deg)
        currentWeatherWindImage.setOnCreateContextMenuListener { menu, v, menuInfo ->
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
            currentWeatherTime.visibility = View.VISIBLE

            currentWeatherTime.text = TimeUtilis.getVisualFromLong(it.timeOfCall)
            if (it.weather.isNotEmpty()) {
                currentWeatherDescription.visibility = View.VISIBLE

                // There is a list of weather but I don't know why ?
                ImageUtilis.setImageToImageView(currentWeatherImage, it.weather[0].icon)
                currentWeatherDescription.text = Character.toUpperCase(it.weather[0].description[0]) + it.weather[0].description.substring(1)
            } else {
                ImageUtilis.setImageToImageView(currentWeatherImage, null)
                currentWeatherDescription.visibility = View.GONE
            }
        } ?: run {
            hideViews()
        }
    }

    private fun hideViews() {
        ImageUtilis.setImageToImageView(currentWeatherImage, null)
        currentWeatherDescription.visibility = View.GONE
        currentWeatherTime.visibility = View.GONE
    }
}