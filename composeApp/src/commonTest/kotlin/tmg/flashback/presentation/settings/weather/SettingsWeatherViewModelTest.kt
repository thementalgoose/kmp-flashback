package tmg.flashback.presentation.settings.weather

import app.cash.turbine.test
import dev.mokkery.MockMode
import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import kotlinx.coroutines.test.runTest
import tmg.flashback.feature.weekend.repositories.WeatherRepository
import kotlin.test.Test
import kotlin.test.assertEquals

internal class SettingsWeatherViewModelTest {

    private lateinit var underTest: SettingsWeatherViewModel

    private val mockWeatherRepository: WeatherRepository = mock(autoUnit)

    private fun initUnderTest() {
        underTest = SettingsWeatherViewModel(
            weatherRepository = mockWeatherRepository
        )
    }

    @Test
    fun `windspeed is populated from repo`() = runTest {
        every { mockWeatherRepository.weatherTemperatureMetric } returns false
        every { mockWeatherRepository.weatherWindspeedMetric } returns true
        initUnderTest()
        underTest.uiState.test {
            assertEquals(true, awaitItem().windSpeedMetrics)
        }
    }

    @Test
    fun `temperature is populated from repo`() = runTest {
        every { mockWeatherRepository.weatherWindspeedMetric } returns false
        every { mockWeatherRepository.weatherTemperatureMetric } returns true
        initUnderTest()
        underTest.uiState.test {
            assertEquals(true, awaitItem().temperatureMetrics)
        }
    }

    @Test
    fun `updating values saves values to repo`() = runTest {
        every { mockWeatherRepository.weatherWindspeedMetric } returns false
        every { mockWeatherRepository.weatherTemperatureMetric } returns false

        initUnderTest()

        underTest.updateTemperatureMetric(true)
        verify {
            mockWeatherRepository.weatherTemperatureMetric = true
        }

        underTest.updateWindspeedMetric(true)
        verify {
            mockWeatherRepository.weatherWindspeedMetric = true
        }
    }
}