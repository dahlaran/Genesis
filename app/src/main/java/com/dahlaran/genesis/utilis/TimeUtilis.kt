package com.dahlaran.genesis.utilis

import android.annotation.SuppressLint
import android.text.format.DateUtils
import java.text.SimpleDateFormat


class TimeUtilis {

    companion object {

        /**
         * Create a string in format of date by using a long
         * Only hour and minutes if it is the same day
         * @param longDate date in long format (in millisecond)
         */
        @SuppressLint("SimpleDateFormat")
        fun getVisualFromLong(longDate: Long): String {
            if (DateUtils.isToday(longDate)) {
                return "Today " + SimpleDateFormat("HH:mm").format(longDate)
            }
            return getDumbVisualFromLong(longDate)
        }

        /**
         * Create a string in format of date by using a long
         * In dd/MM HH:mm without distinction of the current day
         * @param longDate date in long format (in millisecond)
         */
        @SuppressLint("SimpleDateFormat")
        fun getDumbVisualFromLong(longDate: Long): String {
            return SimpleDateFormat("dd/MM HH:mm").format(longDate)
        }
    }
}