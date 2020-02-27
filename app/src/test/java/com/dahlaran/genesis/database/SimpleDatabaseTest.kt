package com.dahlaran.genesis.database

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.dahlaran.genesis.data.database.OpenWeatherApiWeatherDao
import com.dahlaran.genesis.data.database.OpenWeatherApiWeatherDatabase
import com.dahlaran.genesis.models.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.IsEqual.equalTo
import org.junit.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException

// TODO: Remove to target API 29 when Android Studio can support Java 9
@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(RobolectricTestRunner::class)
class SimpleDatabaseTest {
    private lateinit var weatherDao: OpenWeatherApiWeatherDao
    private lateinit var db: OpenWeatherApiWeatherDatabase

    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()

    // https://github.com/Kotlin/kotlinx.coroutines/tree/master/kotlinx-coroutines-test#providing-an-explicit-testcoroutinedispatcher
    private val testScope = TestCoroutineScope(TestCoroutineDispatcher())

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(
            context, OpenWeatherApiWeatherDatabase::class.java
        ).build()
        weatherDao = db.openWeatherApiWeatherDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeWeatherAndReadInList() = testScope.runBlockingTest {
        val weatherList = ArrayList<Weather>()
        weatherList.add(Weather(0, "main", "description", "icon"))
        val weather = OpenWeatherApiWeather(
            Coord(10, 10),
            weatherList,
            "base",
            Main(0.1, 0.2, 0.3, 0.4, 5, 6),
            Wind(0.1, 0.2), Clouds(1), 10,
            Sys(1, 2, 3.3, "country", 5, 6),
            1800, 2, "name", 42, System.currentTimeMillis()
        )
        var weatherRetried: OpenWeatherApiWeather? = null
        val coroutineScope = CoroutineScope(Dispatchers.Default + Job())

        coroutineScope.launch {
            weatherDao.insert(weather)
            weatherRetried = weatherDao.getWeather()
        }

        // Wait the coroutine to finish
        Thread.sleep(1500)

        Assert.assertThat(weatherRetried, equalTo(weather))
    }
}