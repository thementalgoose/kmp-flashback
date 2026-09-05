package tmg.flashback.feature.weekend.repositories

import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import tmg.flashback.preferences.manager.PreferenceManager
import kotlin.test.Test
import kotlin.test.assertTrue


internal class WeekendRepositoryTest {

    private val mockPreferenceManager: PreferenceManager = mock(autoUnit)

    private lateinit var underTest: WeekendRepositoryImpl

    private fun initSUT() {
        underTest = WeekendRepositoryImpl(
            preferenceManager = mockPreferenceManager
        )
    }

    @Test
    fun `weather temperature metrics reads value from preferences repository`() {
        every { mockPreferenceManager.getBoolean(keyWeekendWeatherDetails, true) } returns true
        initSUT()

        assertTrue(underTest.weatherDetails)
        verify {
            mockPreferenceManager.getBoolean(keyWeekendWeatherDetails, true)
        }
    }

    @Test
    fun `weather temperature metrics saves value to shared prefs repository`() {
        initSUT()

        underTest.weatherDetails = true
        verify {
            mockPreferenceManager.save(keyWeekendWeatherDetails, true)
        }
    }

    companion object {
        private const val keyWeekendWeatherDetails: String = "WEEKEND_WEATHER_DETAILS"
    }
    }
