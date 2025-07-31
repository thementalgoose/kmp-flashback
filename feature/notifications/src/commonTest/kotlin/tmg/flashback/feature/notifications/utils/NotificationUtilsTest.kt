package tmg.flashback.feature.notifications.utils

import tmg.flashback.formula1.enums.RaceWeekend
import kotlin.test.Test
import kotlin.test.assertEquals

internal class NotificationUtilsTest {

    private data class TestCase(
        val label: String,
        val expectedRaceWeekend: RaceWeekend?
    )

    private val testCases = listOf(
        TestCase("Grand Prix", RaceWeekend.RACE),
        TestCase("raCe", RaceWeekend.RACE),
        TestCase("race", RaceWeekend.RACE),

        TestCase("sprinting", RaceWeekend.SPRINT),
        TestCase("sprinter", RaceWeekend.SPRINT),
        TestCase("sprint", RaceWeekend.SPRINT),
        TestCase("sprint quali", RaceWeekend.SPRINT),
        TestCase("sprint qualifying", RaceWeekend.SPRINT),

        TestCase("quali", RaceWeekend.QUALIFYING),
        TestCase("QUALIFIYING", RaceWeekend.QUALIFYING),
        TestCase("qualifying", RaceWeekend.QUALIFYING),

        TestCase("fp", RaceWeekend.FREE_PRACTICE),
        TestCase("FP2", RaceWeekend.FREE_PRACTICE),
        TestCase("FP4", RaceWeekend.FREE_PRACTICE),
        TestCase("Fp1", RaceWeekend.FREE_PRACTICE),
        TestCase("free practice", RaceWeekend.FREE_PRACTICE),
        TestCase("practice", RaceWeekend.FREE_PRACTICE),

        TestCase("free", null),
        TestCase("day", null),
        TestCase("winter test day 3", null),
        TestCase("unveil 3", null),
    )
    @Test
    fun `get category based on label returns expected`() {
        testCases.forEach { (label, expectedRaceWeekend) ->
            assertEquals(expectedRaceWeekend, NotificationUtils.getCategoryBasedOnLabel(label))
        }
    }
}