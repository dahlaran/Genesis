package com.dahlaran.genesis.utilis

import org.junit.Assert
import org.junit.Test

class TemperatureUtilisTest {

    @Test
    fun convertKelvinToCelciusTest() {
        Assert.assertEquals(TemperatureUtilis.convertKelvinToCelcius(300.0), 26.85, 0.01)
        Assert.assertEquals(TemperatureUtilis.convertKelvinToCelcius(0.0), -273.15, 0.01)
        Assert.assertEquals(TemperatureUtilis.convertKelvinToCelcius(273.15), 0.0, 0.01)
    }

    @Test
    fun convertKelvinToFahrenheitTest() {
        Assert.assertEquals(TemperatureUtilis.convertKelvinToFahrenheit(300.0), 80.33, 0.01)
        Assert.assertEquals(TemperatureUtilis.convertKelvinToFahrenheit(0.0), -459.67, 0.01)
        Assert.assertEquals(TemperatureUtilis.convertKelvinToFahrenheit(255.372), 0.0, 0.01)
    }
}