package com.dahlaran.genesis.utilis

import android.text.format.DateUtils
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.powermock.api.mockito.PowerMockito.mockStatic
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import java.text.SimpleDateFormat


@RunWith(PowerMockRunner::class)
@PrepareForTest(DateUtils::class)
class TimeUtilisTest {

    @Test
    fun getDumbVisualFromLongTest() {
        val currentTime = System.currentTimeMillis()
        Assert.assertEquals(TimeUtilis.getDumbVisualFromLong(currentTime), SimpleDateFormat("dd/MM HH:mm").format(currentTime))
    }

    @Test
    fun getVisualFromLongTest() {
        val currentTime = System.currentTimeMillis()
        // Mock DateUtils.isToday used by TimeUtilis.getVisualFromLong()
        mockStatic(DateUtils::class.java)
        Mockito.`when`(DateUtils.isToday(currentTime)).thenReturn(true)

        Assert.assertEquals(TimeUtilis.getVisualFromLong(currentTime), "Today " + SimpleDateFormat("HH:mm").format(currentTime))
    }
}