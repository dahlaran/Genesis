package com.dahlaran.genesis.utilis

class TemperatureUtilis {

    companion object {

        fun convertKelvinToCelcius(temp: Double): Double {
            return temp - 273.15
        }

        fun convertKelvinTofahrenheit(temp: Double): Double {
            return temp - 459.67
        }
    }
}