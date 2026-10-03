package tmg.flashback.formula1.constants

import kotlin.time.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

internal class Formula1Test {

    data class TestCase(
        val season: Int,
        val expectedPoints: Int
    )

    @ParameterizedTest
    @MethodSource("testCasesMaxPoints")
    fun `maxPoints by season returns correct amount of points`(testCase: TestCase) {
        assertEquals(testCase.expectedPoints, Formula1.maxDriverPointsBySeason(testCase.season))
    }

    @Test
    fun `constructor championship starts in 1958`() {
        assertEquals(1958, Formula1.championshipConstructorStarts)
    }

    @Test
    fun `maxPoints by current year returns correct amounts of points`() {
        val year = Clock.System.now().toLocalDateTime(TimeZone.UTC).year
        assertEquals(25, Formula1.maxDriverPointsBySeason(year))
    }

    @ParameterizedTest
    @MethodSource("testCasesMaxTeamPoints")
    fun `maxTeamPoints by season returns correct amount of points`(testCase: TestCase) {
        assertEquals(testCase.expectedPoints, Formula1.maxTeamPointsBySeason(testCase.season))
    }

    @Test
    fun `maxTeamPoints by current year returns correct amounts of points`() {
        val year = Clock.System.now().toLocalDateTime(TimeZone.UTC).year
        assertEquals(58, Formula1.maxTeamPointsBySeason(year))
    }

    companion object {
        @JvmStatic
        fun testCasesMaxPoints() = listOf(
            TestCase(2025, 25),
            TestCase(2024, 26),
            TestCase(2023, 26),
            TestCase(2021, 26),
            TestCase(2020, 26),
            TestCase(2010, 26),
            TestCase(2009, 11),
            TestCase(1991, 11),
            TestCase(1990, 8),
            TestCase(1950, 8)
        )

        @JvmStatic
        fun testCasesMaxTeamPoints() = listOf(
            TestCase(2023, 60),
            TestCase(2021, 47),
            TestCase(2020, 42),
            TestCase(2010, 42),
            TestCase(2009, 19),
            TestCase(1991, 19),
            TestCase(1990, 14),
            TestCase(1950, 14)
        )
    }
}