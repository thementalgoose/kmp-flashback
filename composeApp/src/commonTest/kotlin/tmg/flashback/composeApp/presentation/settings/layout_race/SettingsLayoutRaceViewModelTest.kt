package tmg.flashback.composeApp.presentation.settings.layout_race

import app.cash.turbine.test
import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import kotlinx.coroutines.test.runTest
import tmg.flashback.feature.weekend.repositories.WeatherRepository
import tmg.flashback.feature.weekend.repositories.WeekendRepository
import kotlin.test.Test
import kotlin.test.assertEquals

internal class SettingsLayoutRaceViewModelTest {

    private lateinit var underTest: SettingsLayoutRaceViewModel

    private val mockWeekendRepository: WeekendRepository = mock(autoUnit)

    private val mockWeatherRepository: WeatherRepository = mock(autoUnit)

    private fun initUnderTest() {
        underTest = SettingsLayoutRaceViewModel(
            weekendRepository = mockWeekendRepository,
            weatherRepository = mockWeatherRepository
        )
    }

    @Test
    fun `keep user selection is populated from repo`() = runTest {
        every { mockWeekendRepository.weatherDetails} returns true
        every { mockWeatherRepository.weatherTemperatureMetric } returns false
        every { mockWeatherRepository.weatherWindspeedMetric } returns false
        initUnderTest()
        underTest.uiState.test {
            assertEquals(true, awaitItem().weatherDetails)
        }
    }

    @Test
    fun `windspeed is populated from repo`() = runTest {
        every { mockWeekendRepository.weatherDetails} returns false
        every { mockWeatherRepository.weatherTemperatureMetric } returns false
        every { mockWeatherRepository.weatherWindspeedMetric } returns true
        initUnderTest()
        underTest.uiState.test {
            assertEquals(true, awaitItem().windSpeedMetrics)
        }
    }

    @Test
    fun `temperature is populated from repo`() = runTest {
        every { mockWeekendRepository.weatherDetails} returns false
        every { mockWeatherRepository.weatherWindspeedMetric } returns false
        every { mockWeatherRepository.weatherTemperatureMetric } returns true
        initUnderTest()
        underTest.uiState.test {
            assertEquals(true, awaitItem().temperatureMetrics)
        }
    }

    @Test
    fun `updating values saves values to repo`() = runTest {
        every { mockWeekendRepository.weatherDetails } returns false
        every { mockWeatherRepository.weatherWindspeedMetric } returns false
        every { mockWeatherRepository.weatherTemperatureMetric } returns false

        initUnderTest()

        underTest.updateWeatherPref(true)
        verify {
            mockWeekendRepository.weatherDetails = true
        }
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