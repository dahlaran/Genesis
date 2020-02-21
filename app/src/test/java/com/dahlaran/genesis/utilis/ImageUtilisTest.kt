package com.dahlaran.genesis.utilis

import com.dahlaran.genesis.R
import org.junit.Assert
import org.junit.Test


class ImageUtilisTest {

    @Test
    fun getImageByStringTest() {
        Assert.assertEquals(ImageUtilis.getImageByString("01d"), ImageUtilis.Images.WI_DAY_SUNNY)
        Assert.assertEquals(ImageUtilis.getImageByString("04d"), ImageUtilis.Images.WI_DAY_CLOUDY)
    }

    @Test
    fun imageResourceTest() {
        Assert.assertEquals(ImageUtilis.getImageByString("01d").idRes, R.drawable.ic_wi_day_sunny)
        Assert.assertEquals(ImageUtilis.getImageByString("04d").idRes, R.drawable.ic_wi_cloudy)
    }

    @Test
    fun defaultValueTest() {
        Assert.assertEquals(ImageUtilis.getImageByString("01dDSE"), ImageUtilis.Images.NO_MEDIA)
        Assert.assertEquals(ImageUtilis.getImageByString("04152d").idRes, R.drawable.ic_no_media)
    }
}