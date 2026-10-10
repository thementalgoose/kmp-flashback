package tmg.flashback.feature.weekend.presentation

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class WeekendTabsTest {

    @Test
    fun `toWeekendTab maps qualifying correctly`() {
        assertEquals(WeekendTabs.Qualifying, "Qualifying".toWeekendTab())
        assertEquals(WeekendTabs.Qualifying, "qualifying".toWeekendTab())
        assertEquals(WeekendTabs.Qualifying, "QUALI".toWeekendTab())
        assertEquals(WeekendTabs.Qualifying, "quali".toWeekendTab())
    }

    @Test
    fun `toWeekendTab maps race correctly`() {
        assertEquals(WeekendTabs.Race, "Race".toWeekendTab())
        assertEquals(WeekendTabs.Race, "race".toWeekendTab())
        assertEquals(WeekendTabs.Race, "Grand Prix".toWeekendTab())
        assertEquals(WeekendTabs.Race, "grand prix".toWeekendTab())
    }

    @Test
    fun `toWeekendTab maps sprint race correctly`() {
        assertEquals(WeekendTabs.SprintRace, "Sprint".toWeekendTab())
        assertEquals(WeekendTabs.SprintRace, "sprint".toWeekendTab())
        assertEquals(WeekendTabs.SprintRace, "Sprint Race".toWeekendTab())
    }

    @Test
    fun `toWeekendTab maps sprint qualifying shootout correctly`() {
        assertEquals(WeekendTabs.SprintQualifying, "Sprint Shootout".toWeekendTab())
        assertEquals(WeekendTabs.SprintQualifying, "shootout".toWeekendTab())
        assertEquals(WeekendTabs.SprintQualifying, "Sprint Qualifying".toWeekendTab(2024))
        assertEquals(WeekendTabs.SprintQualifying, "Sprint Qualifying".toWeekendTab(null))
    }

    @Test
    fun `toWeekendTab maps 2021-2022 sprint qualifying to SprintRace`() {
        assertEquals(WeekendTabs.SprintRace, "Sprint Qualifying".toWeekendTab(2021))
        assertEquals(WeekendTabs.SprintRace, "Sprint Qualifying".toWeekendTab(2022))
    }

    @Test
    fun `toWeekendTab returns null for free practice and unknown labels`() {
        assertNull("FP1".toWeekendTab())
        assertNull("FP2".toWeekendTab())
        assertNull("Practice 1".toWeekendTab())
        assertNull("Free Practice".toWeekendTab())
        assertNull("Testing day 1".toWeekendTab())
        assertNull("Press conference".toWeekendTab())
    }
}
