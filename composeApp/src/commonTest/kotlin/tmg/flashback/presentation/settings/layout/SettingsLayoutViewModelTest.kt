package tmg.flashback.presentation.settings.layout

import app.cash.turbine.test
import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import kotlinx.coroutines.test.runTest
import tmg.flashback.feature.highlights.repositories.HighlightsRepository
import tmg.flashback.feature.season.repositories.CalendarRepository
import kotlin.test.Test
import kotlin.test.assertEquals

internal class SettingsLayoutViewModelTest {

    private lateinit var underTest: SettingsLayoutViewModel

    private val mockCalendarRepository: CalendarRepository = mock(autoUnit)
    private val mockHighlightsRepository: HighlightsRepository = mock(autoUnit)

    private fun initUnderTest() {
        underTest = SettingsLayoutViewModel(
            calendarRepository = mockCalendarRepository,
            highlightsRepository = mockHighlightsRepository
        )
    }

    @Test
    fun `keep user selection is populated from repo`() = runTest {
        every { mockCalendarRepository.emptyWeeksInCalendar } returns false
        every { mockCalendarRepository.collapseList } returns false
        every { mockCalendarRepository.keepUserSelectedSeason } returns true
        every { mockHighlightsRepository.show } returns false
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
        every { mockHighlightsRepository.show } returns false
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
        every { mockHighlightsRepository.show } returns false
        initUnderTest()
        underTest.uiState.test {
            assertEquals(true, awaitItem().showEmptyWeeks)
        }
    }

    @Test
    fun `recent highlights is populated from repo`() = runTest {
        every { mockCalendarRepository.emptyWeeksInCalendar } returns false
        every { mockCalendarRepository.collapseList } returns false
        every { mockCalendarRepository.keepUserSelectedSeason } returns false
        every { mockHighlightsRepository.show } returns true
        initUnderTest()
        underTest.uiState.test {
            assertEquals(true, awaitItem().recentHighlights)
        }
    }

    @Test
    fun `updating values saves values to repo`() = runTest {
        every { mockCalendarRepository.emptyWeeksInCalendar } returns false
        every { mockCalendarRepository.collapseList } returns false
        every { mockCalendarRepository.keepUserSelectedSeason } returns false
        every { mockHighlightsRepository.show } returns false

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
        underTest.updateHighlight(true)
        verify {
            mockHighlightsRepository.show = true
        }
    }
}