package com.dahlaran.genesis.utilis

import android.widget.ImageView
import com.dahlaran.genesis.R

class ImageUtilis {
    enum class Images(val code: String, val idRes: Int) {
        WI_DAY_SUNNY("01d", R.drawable.ic_wi_day_sunny),
        WI_DAY_CLOUDY_GUSTS("02d", R.drawable.ic_wi_day_cloudy),
        WI_DAY_CLOUD("03d", R.drawable.ic_wi_cloud),
        WI_DAY_CLOUDY("04d", R.drawable.ic_wi_cloudy),
        WI_DAY_SHOWER_RAIN("09d", R.drawable.ic_wi_showers),
        WI_DAY_RAIN_MIX("10d", R.drawable.ic_wi_day_rain_mix),
        WI_DAY_THUNDERSTORM("11d", R.drawable.ic_wi_day_thunderstorm),
        WI_DAY_SNOW("13d", R.drawable.ic_wi_day_snow),
        WI_DAY_FOG("50d", R.drawable.ic_wi_day_fog),
        WI_NIGHT_CLEAR("01n", R.drawable.ic_wi_night_clear),
        WI_NIGHT_CLOUDY_GUSTS("02n", R.drawable.ic_wi_night_partly_cloudy),
        WI_NIGHT_CLOUD("03n", R.drawable.ic_wi_cloud),
        WI_NIGHT_CLOUDY("04n", R.drawable.ic_wi_cloudy),
        WI_NIGHT_SHOWER_RAIN("09n", R.drawable.ic_wi_showers),
        WI_NIGHT_RAIN_MIX("10n", R.drawable.ic_wi_night_rain_mix),
        WI_NIGHT_THUNDERSTORM("11n", R.drawable.ic_wi_night_alt_thunderstorm),
        WI_NIGHT_SNOW("13n", R.drawable.ic_wi_night_snow),
        WI_NIGHT_MIST("50d", R.drawable.ic_wi_night_fog),
        NO_MEDIA("", R.drawable.ic_no_media)
    }

    companion object{
        private val map = Images.values().associateBy(Images::code)

        fun getImageByString(name: String?): Images {
            return map[name] ?: Images.NO_MEDIA
        }

        fun setImageToImageView(imageView: ImageView, name: String?) {
            imageView.setImageDrawable(imageView.context.getDrawable(getImageByString(name).idRes))
        }
    }
}