package tmg.flashback.composeApp.presentation.settings.layout_race

import app.cash.turbine.test
import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import kotlinx.coroutines.test.runTest
import tmg.flashback.feature.weekend.repositories.WeekendRepository
import kotlin.test.Test
import kotlin.test.assertEquals

internal class SettingsLayoutRaceViewModelTest {

    private lateinit var underTest: SettingsLayoutRaceViewModel

    private val mockWeekendRepository: WeekendRepository = mock(autoUnit)

    private fun initUnderTest() {
        underTest = SettingsLayoutRaceViewModel(
            weekendRepository = mockWeekendRepository,
        )
    }

    @Test
    fun `keep user selection is populated from repo`() = runTest {
        every { mockWeekendRepository.weatherDetails} returns true
        initUnderTest()
        underTest.uiState.test {
            assertEquals(true, awaitItem().weatherDetails)
        }
    }

    @Test
    fun `updating values saves values to repo`() = runTest {
        every { mockWeekendRepository.weatherDetails } returns false

        initUnderTest()

        underTest.updateWeatherPref(true)
        verify {
            mockWeekendRepository.weatherDetails = true
        }
    }
}