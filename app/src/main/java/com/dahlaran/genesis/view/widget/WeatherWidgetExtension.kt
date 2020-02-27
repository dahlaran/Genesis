package com.dahlaran.genesis.view.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.RemoteViews
import com.dahlaran.genesis.R
import com.dahlaran.genesis.models.OpenWeatherApiWeather
import com.dahlaran.genesis.utilis.ImageUtilis
import com.dahlaran.genesis.utilis.TimeUtilis
import com.dahlaran.genesis.view.activity.MainActivity

/**
 *  WeatherWidget method who don't need attribute can be an extension
 */


internal fun WeatherWidgetProvider.updateWidget(context: Context, view: RemoteViews, appWidgetManager: AppWidgetManager, appWidgetId: Int, weather: OpenWeatherApiWeather?) {

    // TODO: Add widget in dark mode

    // Go inside MainActivity when click on
    val buttonIntent = Intent(context, MainActivity::class.java)
    val buttonPendingIntent = PendingIntent.getActivity(
        context,
        0, buttonIntent, 0
    )

    view.setOnClickPendingIntent(R.id.widgetWeatherMain, buttonPendingIntent)

    // Broadcast to refresh data
    val refreshIntent = Intent(context, WeatherWidgetProvider::class.java)
    refreshIntent.action = WeatherWidgetProvider.ACTION_REFRESH_DATA
    val pendingIntent = PendingIntent.getBroadcast(
        context, 0, refreshIntent, PendingIntent.FLAG_UPDATE_CURRENT
    )
    view.setOnClickPendingIntent(R.id.widgetWeatherRefreshImage, pendingIntent)


    weather?.let {
        view.setViewVisibility(R.id.widgetWeatherTime, View.VISIBLE)

        view.setTextViewText(R.id.widgetWeatherTime, TimeUtilis.getVisualFromLong(it.timeOfCall))
        if (it.weather.isNotEmpty()) {

            view.setViewVisibility(R.id.widgetWeatherDescription, View.VISIBLE)
            // There is a list of weather but I don't know why ?
            view.setImageViewResource(R.id.widgetWeatherImage, ImageUtilis.getImageByString(it.weather[0].icon).idRes)
            view.setTextViewText(R.id.widgetWeatherDescription, Character.toUpperCase(it.weather[0].description[0]) + it.weather[0].description.substring(1))
        } else {
            view.setImageViewResource(R.id.widgetWeatherImage, ImageUtilis.getImageByString(null).idRes)
            view.setViewVisibility(R.id.widgetWeatherDescription, View.GONE)
        }
    } ?: run {
        view.setImageViewResource(R.id.widgetWeatherImage, ImageUtilis.getImageByString(null).idRes)
        view.setViewVisibility(R.id.widgetWeatherDescription, View.GONE)
        view.setViewVisibility(R.id.widgetWeatherTime, View.GONE)
    }

    // Notify that the view have been updated
    appWidgetManager.updateAppWidget(appWidgetId, view)
}