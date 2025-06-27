package tmg.flashback.feature.weekend.repositories

import dev.mokkery.MockMode
import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import tmg.flashback.preferences.manager.PreferenceManager
import kotlin.test.Test
import kotlin.test.assertTrue

internal class WeatherRepositoryTest {

    private val mockPreferenceManager: PreferenceManager = mock(autoUnit)

    private lateinit var underTest: WeatherRepositoryImpl

    private fun initSUT() {
        underTest = WeatherRepositoryImpl(
            preferenceManager = mockPreferenceManager
        )
    }

    //region Weather

    @Test
    fun `weather temperature metrics reads value from preferences repository`() {
        every { mockPreferenceManager.getBoolean(keyWeatherTemperatureMetric, true) } returns true
        initSUT()

        assertTrue(underTest.weatherTemperatureMetric)
        verify {
            mockPreferenceManager.getBoolean(keyWeatherTemperatureMetric, true)
        }
    }

    @Test
    fun `weather temperature metrics saves value to shared prefs repository`() {
        initSUT()

        underTest.weatherTemperatureMetric = true
        verify {
            mockPreferenceManager.save(keyWeatherTemperatureMetric, true)
        }
    }


    @Test
    fun `weather wind speed metrics reads value from preferences repository`() {
        every { mockPreferenceManager.getBoolean(keyWeatherWindspeedMetric, false) } returns true
        initSUT()

        assertTrue(underTest.weatherWindspeedMetric)
        verify {
            mockPreferenceManager.getBoolean(keyWeatherWindspeedMetric, false)
        }
    }

    @Test
    fun `weather wind speed metrics saves value to shared prefs repository`() {
        initSUT()

        underTest.weatherWindspeedMetric = true
        verify {
            mockPreferenceManager.save(keyWeatherWindspeedMetric, true)
        }
    }

    //endregion

    companion object {

        private const val keyWeatherTemperatureMetric: String = "WEATHER_TEMPERATURE_METRIC"
        private const val keyWeatherWindspeedMetric: String = "WEATHER_WINDSPEED_METRIC"
    }
}