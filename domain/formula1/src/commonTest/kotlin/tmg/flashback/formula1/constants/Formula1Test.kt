package tmg.flashback.formula1.constants

import kotlin.time.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals

internal class Formula1Test {

    private data class TestCase(
        val season: Int,
        val expectedPoints: Int
    )

    private val testCasesMaxPoints = listOf(
        TestCase(2024,26),
        TestCase(2023,26),
        TestCase(2021,26),
        TestCase(2020,26),
        TestCase(2010,26),
        TestCase(2009,11),
        TestCase(1991,11),
        TestCase(1990,8),
        TestCase(1950,8),
    )
    @Test
    fun `maxPoints by season returns correct amount of points`() {
        testCasesMaxPoints.forEach { (season, expectedPoints) ->
            assertEquals(expectedPoints, Formula1.maxDriverPointsBySeason(season))
        }
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


    private val testCasesMaxTeamPoints = listOf(
        TestCase(2023,60),
        TestCase(2021,47),
        TestCase(2020,42),
        TestCase(2010,42),
        TestCase(2009,19),
        TestCase(1991,19),
        TestCase(1990,14),
        TestCase(1950,14)
    )
    @Test
    fun `maxTeamPoints by season returns correct amount of points`() {
        testCasesMaxTeamPoints.forEach { (season, expectedPoints) ->
            assertEquals(expectedPoints, Formula1.maxTeamPointsBySeason(season))
        }
    }

    @Test
    fun `maxTeamPoints by current year returns correct amounts of points`() {
        val year = Clock.System.now().toLocalDateTime(TimeZone.UTC).year
        assertEquals(58, Formula1.maxTeamPointsBySeason(year))
    }
}