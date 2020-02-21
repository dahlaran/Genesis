package com.dahlaran.genesis.utilis

class TemperatureUtilis {

    companion object {

        fun convertKelvinToCelcius(temp: Double): Double {
            return temp - 273.15
        }

        fun convertKelvinToFahrenheit(temp: Double): Double {
            return ((temp - 273.15) * 9 / 5 + 32)
        }
    }
}