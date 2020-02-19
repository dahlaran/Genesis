package com.dahlaran.genesis.utilis

class WeatherUtilis {
    companion object {

        // Speed of clouds default kts to km/h
        fun ktsToKmH(cloudsSpeed: Double): Double {
            return cloudsSpeed * 1.852
        }

        // Speed of clouds default kts to miles/h
        fun ktsToMilesH(cloudsSpeed: Double): Double {
            return cloudsSpeed * 1.15078
        }
    }
}