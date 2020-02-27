package com.dahlaran.genesis.view.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.Toast
import com.dahlaran.genesis.R
import com.dahlaran.genesis.utilis.LocationUtilis


class WeatherWidgetProvider : AppWidgetProvider() {

    companion object {
        const val ACTION_REFRESH_DATA = "com.dahlaran.genesis.ACTION_REFRESH_DATA"
        const val ACTION_UPDATE_DATA = "com.dahlaran.genesis.ACTION_UPDATE_DATA"
    }

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        context?.let {
            WeatherWidgetProviderModel.getInstance(it)
        }
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        context?.let {
            WeatherWidgetProviderModel.getInstance(it).removeLiveDataObserver()
        }
    }

    override fun onReceive(context: Context?, intent: Intent) {
        val action = intent.action

        if (action == ACTION_UPDATE_DATA && context != null) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val ids = appWidgetManager.getAppWidgetIds(ComponentName(context, WeatherWidgetProvider::class.java))

            updateView(context, AppWidgetManager.getInstance(context), ids)
        } else if (action == ACTION_REFRESH_DATA && context != null) {
            // Request location if it change
            val requestLocation = LocationUtilis.requestCurrentPosition(context)
            // TODO: show success and failed graphic instead of toast
            if (!requestLocation) {
                Toast.makeText(context, "Request Location Failed", Toast.LENGTH_SHORT).show()
            }

            WeatherWidgetProviderModel.getInstance(context).singleObserverToPresenterWeatherLiveData(context)
            // Call presenter to get new weather even if the location didn't change
            WeatherWidgetProviderModel.getInstance(context).presenter.getWeatherUsingCoordinate(LocationUtilis.location.value)
        } else super.onReceive(context, intent)
    }

    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        updateView(context, appWidgetManager, appWidgetIds)
        context?.let {
            createRefreshDataPendingIntent(it).send()
        }
    }

    private fun updateView(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        if (context != null && appWidgetManager != null) {
            appWidgetIds?.forEach { id ->
                val remoteViews = RemoteViews(context.packageName, R.layout.widget_weather)

                updateWidget(context, remoteViews, appWidgetManager, id, WeatherWidgetProviderModel.getInstance(context).weatherLiveData?.value)
            }
        }
    }

    override fun onAppWidgetOptionsChanged(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetId: Int, newOptions: Bundle?) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
        if (context != null && appWidgetManager != null) {
            val remoteViews = RemoteViews(context.packageName, R.layout.widget_weather)

            updateWidget(context, remoteViews, appWidgetManager, appWidgetId, WeatherWidgetProviderModel.getInstance(context).weatherLiveData?.value)
        }
    }
}
