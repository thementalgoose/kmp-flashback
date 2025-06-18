package tmg.flashback.formula1.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class ConstructorHistoryTest {

    @Test
    fun `championship wins counts finished standings only`() {
        val model = ConstructorHistory.model(standings = listOf(
            ConstructorHistorySeason.model(season = 2018, championshipStanding = 1), // Valid
            ConstructorHistorySeason.model(season = 2019, isInProgress = false), // Invalid
            ConstructorHistorySeason.model(season = 2020, championshipStanding = 2), // Invalid
        ))

        assertEquals(1, model.championshipWins)
    }

    private data class TestCase(
        val season1Standing: Int?,
        val season1InProgress: Boolean,
        val season2Standing: Int?,
        val season2InProgress: Boolean,
        val expected: Int?
    )

    private val testCases = listOf(
        TestCase(1, false,2 ,false ,1),
        TestCase(1, true,2 ,false ,2),
        TestCase(null,false,2 ,false ,2),
        TestCase(null,false,null,false, null),
        TestCase(1, true ,null,false, null),
    )
    @Test
    fun `best championship position`() {
        testCases.forEach { (season1Standing, season1InProgress, season2Standing, season2InProgress, expected) ->
            val model = ConstructorHistory.model(standings = listOf(
                ConstructorHistorySeason.model(season = 2019, championshipStanding = season1Standing, isInProgress = season1InProgress),
                ConstructorHistorySeason.model(season = 2020, championshipStanding = season2Standing, isInProgress = season2InProgress)
            ))

            assertEquals(expected, model.bestChampionship)
        }
    }

    @Test
    fun `drivers championships wins counts finished standings only`() {
        val model = ConstructorHistory.model(standings = listOf(
            ConstructorHistorySeason.model(season = 2019, isInProgress = false, drivers = mapOf(
                "driver1" to ConstructorHistorySeasonDriver.model(championshipStanding = 2)
            )),
            ConstructorHistorySeason.model(season = 2020, isInProgress = false, drivers = mapOf(
                "driver1" to ConstructorHistorySeasonDriver.model(championshipStanding = 1)
            )),
            ConstructorHistorySeason.model(season = 2021, isInProgress = false, drivers = mapOf(
                "driver1" to ConstructorHistorySeasonDriver.model(championshipStanding = 1)
            )),
            // Ignored for in progress
            ConstructorHistorySeason.model(season = 2022, isInProgress = true, drivers = mapOf(
                "driver1" to ConstructorHistorySeasonDriver.model(championshipStanding = 1)
            ))
        ))
        assertEquals(2, model.driversChampionships)
    }

    @Test
    fun `has championship currently in progress returns true for in progress`() {
        val model = ConstructorHistory.model(standings = listOf(
            ConstructorHistorySeason.model(season = 2019, isInProgress = true),
            ConstructorHistorySeason.model(season = 2020, isInProgress = false)
        ))

        assertTrue(model.hasChampionshipCurrentlyInProgress)
    }

    @Test
    fun `has championship currently in progress returns false for when none in progress`() {
        val model = ConstructorHistory.model(standings = listOf(
            ConstructorHistorySeason.model(season = 2019, isInProgress = false),
            ConstructorHistorySeason.model(season = 2020, isInProgress = false)
        ))

        assertFalse(model.hasChampionshipCurrentlyInProgress)
    }

    @Test
    fun `races returns sum of all races`() {
        val model = ConstructorHistory.model(standings = listOf(
            ConstructorHistorySeason.model(season = 2019, races = 2),
            ConstructorHistorySeason.model(season = 2020, races = 4)
        ))

        assertEquals(6, model.races)
    }

    @Test
    fun `total wins sums wins in seasons`() {
        val model = ConstructorHistory.model(standings = listOf(
            ConstructorHistorySeason.model(season = 2019, drivers = mapOf(
                "driver1" to ConstructorHistorySeasonDriver.model(wins = 3)
            )),
            ConstructorHistorySeason.model(season = 2020, drivers = mapOf(
                "driver1" to ConstructorHistorySeasonDriver.model(wins = 6)
            ))
        ))

        assertEquals(9, model.totalWins)
    }

    @Test
    fun `total podiums sums podiums in seasons`() {
        val model = ConstructorHistory.model(standings = listOf(
            ConstructorHistorySeason.model(season = 2019, drivers = mapOf(
                "driver1" to ConstructorHistorySeasonDriver.model(podiums = 3)
            )),
            ConstructorHistorySeason.model(season = 2020, drivers = mapOf(
                "driver1" to ConstructorHistorySeasonDriver.model(podiums = 6)
            ))
        ))

        assertEquals(9, model.totalPodiums)
    }

    @Test
    fun `total points sums in seasons`() {
        val model = ConstructorHistory.model(standings = listOf(
            ConstructorHistorySeason.model(season = 2019, points = 12.0),
            ConstructorHistorySeason.model(season = 2020, points = 6.0)
        ))

        assertEquals(18.0, model.totalPoints)
    }

    @Test
    fun `total qualifying poles sums on pole`() {
        val model = ConstructorHistory.model(standings = listOf(
            ConstructorHistorySeason.model(season = 2019, drivers = mapOf(
                "driverId" to ConstructorHistorySeasonDriver.model(polePosition = 2)
            )),
            ConstructorHistorySeason.model(season = 2019, drivers = mapOf(
                "driver2" to ConstructorHistorySeasonDriver.model(
                    driver = DriverEntry.model(driver = Driver.model(id = "driver2")),
                    polePosition = 3
                )
            ))
        ))

        assertEquals(5, model.totalQualifyingPoles)
    }

    @Test
    fun `finishes in points sums on pole`() {
        val model = ConstructorHistory.model(standings = listOf(
            ConstructorHistorySeason.model(season = 2019, drivers = mapOf(
                "driverId" to ConstructorHistorySeasonDriver.model(finishesInPoints = 2)
            )),
            ConstructorHistorySeason.model(season = 2019, drivers = mapOf(
                "driver2" to ConstructorHistorySeasonDriver.model(
                    driver = DriverEntry.model(driver = Driver.model(id = "driver2")),
                    finishesInPoints = 5
                )
            ))
        ))

        assertEquals(7, model.finishesInPoints)
    }

    @Test
    fun `is world champion for season returns true if season is finished and standing is 1`() {
        val model = ConstructorHistory.model(standings = listOf(
            ConstructorHistorySeason.model(season = 2019, isInProgress = false, championshipStanding = 1)
        ))

        assertTrue(model.isWorldChampionFor(2019))
    }

    @Test
    fun `is world champion for season returns false if season is not finished and standing is 1`() {
        val model = ConstructorHistory.model(standings = listOf(
            ConstructorHistorySeason.model(season = 2019, isInProgress = true, championshipStanding = 1)
        ))

        assertFalse(model.isWorldChampionFor(2019))
    }

    @Test
    fun `is world champion for season returns false if season is finished and standing is greater than 1`() {
        val model = ConstructorHistory.model(standings = listOf(
            ConstructorHistorySeason.model(season = 2019, isInProgress = false, championshipStanding = 2)
        ))

        assertFalse(model.isWorldChampionFor(2019))
    }

    @Test
    fun `is world champion for season returns false if season is finished and standing is 1 for different season`() {
        val model = ConstructorHistory.model(standings = listOf(
            ConstructorHistorySeason.model(season = 2019, isInProgress = false, championshipStanding = 1)
        ))

        assertFalse(model.isWorldChampionFor(2020))
    }
}