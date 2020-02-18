package com.dahlaran.genesis.view.layout

import android.content.Context
import android.util.AttributeSet
import android.view.ContextMenu
import android.view.View
import androidx.cardview.widget.CardView
import com.dahlaran.genesis.R
import com.dahlaran.genesis.models.OpenWeatherApiWeather
import com.dahlaran.genesis.utilis.ImageUtilis
import kotlinx.android.synthetic.main.current_weather_layout.view.*

class CurrentWeatherLayout : CardView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var weather: OpenWeatherApiWeather? = null

    init {
        inflate(context, R.layout.current_weather_layout, this)
        hideViews()
        current_weather_temperature.setOnCreateContextMenuListener { menu, v, menuInfo ->
            weather?.main?.let {
                menu.add(context.getString(R.string.temperature_menu, it.temp))
                menu.add(context.getString(R.string.feels_like_menu, it.feels_like))
                menu.add(context.getString(R.string.temp_min_menu, it.temp_min))
                menu.add(context.getString(R.string.temp_max_menu, it.temp_max))
            }
        }
        current_weather_temperature.setOnCreateContextMenuListener { menu, v, menuInfo ->
            weather?.main?.let {
                menu.add(context.getString(R.string.temperature_menu, it.temp))
                menu.add(context.getString(R.string.feels_like_menu, it.feels_like))
                menu.add(context.getString(R.string.temp_min_menu, it.temp_min))
                menu.add(context.getString(R.string.temp_max_menu, it.temp_max))
            }
        }
        current_weather_temperature.setOnCreateContextMenuListener { menu, v, menuInfo ->
            weather?.main?.let {
                menu.add(context.getString(R.string.temperature_menu, it.temp))
                menu.add(context.getString(R.string.feels_like_menu, it.feels_like))
                menu.add(context.getString(R.string.temp_min_menu, it.temp_min))
                menu.add(context.getString(R.string.temp_max_menu, it.temp_max))
            }
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?) {
        super.onCreateContextMenu(menu)

    }

    fun setNewValue(newWeather: OpenWeatherApiWeather?) {
        weather = newWeather
        updateView()
    }

    private fun updateView() {
        weather?.let {
            current_weather_description.visibility = View.VISIBLE
            current_weather_hour.visibility = View.VISIBLE
            current_weather_temperature.visibility = View.VISIBLE

            current_weather_hour.text = it.timezone.toString()
            current_weather_temperature.text = it.main.temp.toString()
            if (it.weather.isNotEmpty()) {
                // There is a list of weather but I don't know why ?
                ImageUtilis.setImageToImageView(current_weather_image, it.weather[0].icon)
                current_weather_description.text = it.weather[0].description
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
        current_weather_temperature.visibility = View.GONE
    }
}