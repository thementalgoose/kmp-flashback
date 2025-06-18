package tmg.flashback.formula1.utils

import tmg.flashback.formula1.enums.RaceWeekend.*
import tmg.flashback.formula1.enums.RaceWeekend
import tmg.flashback.formula1.utils.NotificationUtils.getCategoryBasedOnLabel
import kotlin.test.Test
import kotlin.test.assertEquals

internal class NotificationUtilsTest {

    private data class TestCase(
        val label: String,
        val expectedRaceWeekend: RaceWeekend?
    )

    private val testCases = listOf(
        TestCase("Grand Prix",RACE),
        TestCase("raCe", RACE),
        TestCase("race", RACE),

        TestCase("sprinting", SPRINT),
        TestCase("sprinter", SPRINT),
        TestCase("sprint", SPRINT),
        TestCase("sprint quali", SPRINT),
        TestCase("sprint qualifying", SPRINT),

        TestCase("quali", QUALIFYING),
        TestCase("QUALIFIYING", QUALIFYING),
        TestCase("qualifying", QUALIFYING),

        TestCase("fp", FREE_PRACTICE),
        TestCase("FP2", FREE_PRACTICE),
        TestCase("FP4", FREE_PRACTICE),
        TestCase("Fp1", FREE_PRACTICE),
        TestCase("free practice", FREE_PRACTICE),
        TestCase("practice", FREE_PRACTICE),

        TestCase("free", null),
        TestCase("day", null),
        TestCase("winter test day 3", null),
        TestCase("unveil 3", null),
    )
    @Test
    fun `get category based on label returns expected`() {
        testCases.forEach { (label, expectedRaceWeekend) ->
            assertEquals(expectedRaceWeekend, getCategoryBasedOnLabel(label))
        }
    }
}