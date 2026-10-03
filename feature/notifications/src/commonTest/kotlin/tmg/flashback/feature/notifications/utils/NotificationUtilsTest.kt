package tmg.flashback.feature.notifications.utils

import tmg.flashback.formula1.enums.RaceWeekend
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.api.Assertions.assertEquals

internal class NotificationUtilsTest {

    data class TestCase(
        val label: String,
        val expectedRaceWeekend: RaceWeekend?
    )

    @ParameterizedTest
    @MethodSource("testCases")
    fun `get category based on label returns expected`(testCase: TestCase) {
        assertEquals(testCase.expectedRaceWeekend, NotificationUtils.getCategoryBasedOnLabel(testCase.label))
    }

    companion object {
        @JvmStatic
        fun testCases() = listOf(
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
            TestCase("unveil 3", null)
        )
    }
}