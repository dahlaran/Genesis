package com.dahlaran.genesis.utilis

import org.junit.Assert
import org.junit.Test

class WeatherUtilisTest {

    @Test
    fun ktsToKmHTest() {
        Assert.assertEquals(WeatherUtilis.ktsToKmH(30.0), 55.56, 0.01)
        Assert.assertEquals(WeatherUtilis.ktsToKmH(1.0), 1.852, 0.01)
        Assert.assertEquals(WeatherUtilis.ktsToKmH(0.539957), 1.0, 0.01)
    }

    @Test
    fun ktsToMilesHTest() {
        Assert.assertEquals(WeatherUtilis.ktsToMilesH(30.0), 34.5234, 0.01)
        Assert.assertEquals(WeatherUtilis.ktsToMilesH(1.0), 1.15078, 0.01)
        Assert.assertEquals(WeatherUtilis.ktsToMilesH(0.868976), 1.0, 0.01)
    }
}