package tmg.flashback.feature.season.repositories

import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import tmg.flashback.preferences.manager.PreferenceManager
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class CalendarRepositoryTest {

    private lateinit var underTest: CalendarRepositoryImpl

    private val mockPreferenceManager: PreferenceManager = mock(autoUnit)

    private fun initUnderTest() {
        underTest = CalendarRepositoryImpl(
            preferenceManager = mockPreferenceManager
        )
    }

    @Test
    fun `viewed seasons reads from prefs`() {
        val set = mutableSetOf("1", "2")
        every { mockPreferenceManager.getSet(expectedKeySeenSeasons, emptySet()) } returns set

        initUnderTest()

        assertEquals(setOf(1, 2), underTest.viewedSeasons)
    }

    @Test
    fun `viewed seasons writes to prefs`() {
        initUnderTest()
        underTest.viewedSeasons = setOf(1, 2)

        verify {
            mockPreferenceManager.save(expectedKeySeenSeasons, mutableSetOf("1", "2"))
        }
    }

    @Test
    fun `empty weeks in calendar reads from prefs`() {
        every { mockPreferenceManager.getBoolean(expectedKeyEmptyWeeksInSchedule, false) } returns true

        initUnderTest()
        assertTrue(underTest.emptyWeeksInCalendar)
    }

    @Test
    fun `empty weeks in calendar writes to prefs`() {
        initUnderTest()
        underTest.emptyWeeksInCalendar = true

        verify {
            mockPreferenceManager.save(expectedKeyEmptyWeeksInSchedule, true)
        }
    }

    @Test
    fun `collapse list reads from prefs`() {
        every { mockPreferenceManager.getBoolean(expectedKeyDashboardCollapseList, false) } returns true

        initUnderTest()
        assertTrue(underTest.collapseList)
    }

    @Test
    fun `collapse list writes to prefs`() {
        initUnderTest()
        underTest.collapseList = true

        verify {
            mockPreferenceManager.save(expectedKeyDashboardCollapseList, true)
        }
    }

    @Test
    fun `keep user selected season reads from prefs`() {
        every { mockPreferenceManager.getBoolean(expectedKeyRememberSeasonChange, false) } returns true

        initUnderTest()
        assertTrue(underTest.keepUserSelectedSeason)
    }

    @Test
    fun `keep user selected season writes to prefs`() {
        initUnderTest()
        underTest.keepUserSelectedSeason = true

        verify {
            mockPreferenceManager.save(expectedKeyRememberSeasonChange, true)
        }
    }

    @Test
    fun `user selected season reads from prefs`() {
        every { mockPreferenceManager.getInt(expectedKeyUserSeasonChange, -1) } returns 2

        initUnderTest()
        assertEquals(2, underTest.userSelectedSeason)
    }

    @Test
    fun `user selected season writes to prefs`() {
        initUnderTest()
        underTest.userSelectedSeason = 2020

        verify {
            mockPreferenceManager.save(expectedKeyUserSeasonChange, 2020)
        }
    }
    companion object {

        // Prefs
        private const val expectedKeyEmptyWeeksInSchedule: String = "empty_weeks_in_schedule"
        private const val expectedKeySeenSeasons: String = "SEASONS_VIEWED"
        private const val expectedKeyDashboardCollapseList: String = "DASHBOARD_COLLAPSE_LIST"
        private const val expectedKeyRememberSeasonChange: String = "REMEMBER_SEASON_CHANGE"
        private const val expectedKeyUserSeasonChange: String = "USER_SEASON_CHANGE"
    }
}