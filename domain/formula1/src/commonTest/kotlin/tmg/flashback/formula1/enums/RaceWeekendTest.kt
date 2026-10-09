package tmg.flashback.formula1.enums

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class RaceWeekendTest {

    @Test
    fun `toRaceWeekend maps qualifying correctly`() {
        assertEquals(RaceWeekend.QUALIFYING, "Qualifying".toRaceWeekend())
        assertEquals(RaceWeekend.QUALIFYING, "qualifying".toRaceWeekend())
        assertEquals(RaceWeekend.QUALIFYING, "QUALI".toRaceWeekend())
        assertEquals(RaceWeekend.QUALIFYING, "quali".toRaceWeekend())
    }

    @Test
    fun `toRaceWeekend maps race correctly`() {
        assertEquals(RaceWeekend.RACE, "Race".toRaceWeekend())
        assertEquals(RaceWeekend.RACE, "race".toRaceWeekend())
        assertEquals(RaceWeekend.RACE, "Grand Prix".toRaceWeekend())
        assertEquals(RaceWeekend.RACE, "grand prix".toRaceWeekend())
    }

    @Test
    fun `toRaceWeekend maps sprint race correctly`() {
        assertEquals(RaceWeekend.SPRINT, "Sprint".toRaceWeekend())
        assertEquals(RaceWeekend.SPRINT, "sprint".toRaceWeekend())
        assertEquals(RaceWeekend.SPRINT, "Sprint Race".toRaceWeekend())
    }

    @Test
    fun `toRaceWeekend maps sprint qualifying shootout correctly`() {
        assertEquals(RaceWeekend.SPRINT_QUALIFYING, "Sprint Shootout".toRaceWeekend())
        assertEquals(RaceWeekend.SPRINT_QUALIFYING, "shootout".toRaceWeekend())
        assertEquals(RaceWeekend.SPRINT_QUALIFYING, "Sprint Qualifying".toRaceWeekend(2024))
        assertEquals(RaceWeekend.SPRINT_QUALIFYING, "Sprint Qualifying".toRaceWeekend(null))
    }

    @Test
    fun `toRaceWeekend maps 2021-2022 sprint qualifying to SPRINT`() {
        assertEquals(RaceWeekend.SPRINT, "Sprint Qualifying".toRaceWeekend(2021))
        assertEquals(RaceWeekend.SPRINT, "Sprint Qualifying".toRaceWeekend(2022))
    }

    @Test
    fun `toRaceWeekend maps free practice correctly`() {
        assertEquals(RaceWeekend.FREE_PRACTICE, "FP1".toRaceWeekend())
        assertEquals(RaceWeekend.FREE_PRACTICE, "FP2".toRaceWeekend())
        assertEquals(RaceWeekend.FREE_PRACTICE, "FP3".toRaceWeekend())
        assertEquals(RaceWeekend.FREE_PRACTICE, "Practice 1".toRaceWeekend())
        assertEquals(RaceWeekend.FREE_PRACTICE, "Free Practice".toRaceWeekend())
    }

    @Test
    fun `toRaceWeekend returns null for unknown label`() {
        assertNull("Testing day 1".toRaceWeekend())
        assertNull("Press conference".toRaceWeekend())
    }
}
