package tmg.flashback.presentation.settings.layout

import app.cash.turbine.test
import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import kotlinx.coroutines.test.runTest
import tmg.flashback.feature.season.repositories.CalendarRepository
import kotlin.test.Test
import kotlin.test.assertEquals

internal class SettingsLayoutViewModelTest {

    private lateinit var underTest: SettingsLayoutViewModel

    private val mockCalendarRepository: CalendarRepository = mock(autoUnit)

    private fun initUnderTest() {
        underTest = SettingsLayoutViewModel(
            calendarRepository = mockCalendarRepository
        )
    }

    @Test
    fun `keep user selection is populated from repo`() = runTest {
        every { mockCalendarRepository.emptyWeeksInCalendar } returns false
        every { mockCalendarRepository.collapseList } returns false
        every { mockCalendarRepository.keepUserSelectedSeason } returns true
        initUnderTest()
        underTest.uiState.test {
            assertEquals(true, awaitItem().keepLastSeason)
        }
    }

    @Test
    fun `collapse list is populated from repo`() = runTest {
        every { mockCalendarRepository.emptyWeeksInCalendar } returns false
        every { mockCalendarRepository.collapseList } returns true
        every { mockCalendarRepository.keepUserSelectedSeason } returns false
        initUnderTest()
        underTest.uiState.test {
            assertEquals(true, awaitItem().collapseRaces)
        }
    }

    @Test
    fun `empty weeks in calendar is populated from repo`() = runTest {
        every { mockCalendarRepository.emptyWeeksInCalendar } returns true
        every { mockCalendarRepository.collapseList } returns false
        every { mockCalendarRepository.keepUserSelectedSeason } returns false
        initUnderTest()
        underTest.uiState.test {
            assertEquals(true, awaitItem().showEmptyWeeks)
        }
    }

    @Test
    fun `updating values saves values to repo`() = runTest {
        every { mockCalendarRepository.emptyWeeksInCalendar } returns false
        every { mockCalendarRepository.collapseList } returns false
        every { mockCalendarRepository.keepUserSelectedSeason } returns false

        initUnderTest()

        underTest.updateKeepLastSeason(true)
        verify {
            mockCalendarRepository.keepUserSelectedSeason = true
        }
        underTest.updateShowEmptyWeeks(true)
        verify {
            mockCalendarRepository.emptyWeeksInCalendar = true
        }
        underTest.updateCollapseRacesEnabled(true)
        verify {
            mockCalendarRepository.collapseList = true
        }
    }
}