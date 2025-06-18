package tmg.flashback.formula1.model

import tmg.flashback.formula1.enums.RaceStatus
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class DriverHistoryTest {

    @Test
    fun `championship wins returns valid when season not in progress and standing is 1`() {
        val model = DriverHistory.model(standings = listOf(
            DriverHistorySeason.model(season = 2019, isInProgress = false, championshipStanding = 1)
        ))
        assertEquals(1, model.championshipWins)
        assertTrue(model.isWorldChampionFor(2019))
        assertFalse(model.isWorldChampionFor(2020))
    }
    @Test
    fun `championship wins returns invalid when season in progress and standing is 1`() {
        val model = DriverHistory.model(standings = listOf(
            DriverHistorySeason.model(season = 2019, isInProgress = true, championshipStanding = 1)
        ))
        assertEquals(0, model.championshipWins)
        assertFalse(model.isWorldChampionFor(2019))
        assertFalse(model.isWorldChampionFor(2020))
    }
    @Test
    fun `championship wins returns invalid when season not in progress and standing is 2`() {
        val model = DriverHistory.model(standings = listOf(
            DriverHistorySeason.model(season = 2019, isInProgress = false, championshipStanding = 2)
        ))
        assertEquals(0, model.championshipWins)
        assertFalse(model.isWorldChampionFor(2019))
        assertFalse(model.isWorldChampionFor(2020))
    }
    @Test
    fun `championship wins returns invalid when season in progress and standing is 2`() {
        val model = DriverHistory.model(standings = listOf(
            DriverHistorySeason.model(season = 2019, isInProgress = true, championshipStanding = 2)
        ))
        assertEquals(0, model.championshipWins)
        assertFalse(model.isWorldChampionFor(2019))
        assertFalse(model.isWorldChampionFor(2020))
    }

    @Test
    fun `career best championship position returns correct result`() {
        val model = DriverHistory.model(standings = listOf(
            DriverHistorySeason.model(championshipStanding = null), // Invalid
            DriverHistorySeason.model(isInProgress = true), // Invalid
            DriverHistorySeason.model(championshipStanding = 2, isInProgress = false),
            DriverHistorySeason.model(championshipStanding = 3, isInProgress = false)
        ))
        assertEquals(2, model.careerBestChampionship)
    }

    @Test
    fun `has championship currently in progress returns true for in progress`() {
        val model = DriverHistory.model(standings = listOf(
            DriverHistorySeason.model(season = 2019, isInProgress = true),
            DriverHistorySeason.model(season = 2020, isInProgress = false)
        ))

        assertTrue(model.hasChampionshipCurrentlyInProgress)
    }

    @Test
    fun `has championship currently in progress returns false for when none in progress`() {
        val model = DriverHistory.model(standings = listOf(
            DriverHistorySeason.model(season = 2019, isInProgress = false),
            DriverHistorySeason.model(season = 2020, isInProgress = false)
        ))

        assertFalse(model.hasChampionshipCurrentlyInProgress)
    }

    @Test
    fun `career wins counts all wins from standings`() {
        val model = DriverHistory.model(standings = listOf(
            DriverHistorySeason.model(raceOverview = listOf(
                DriverHistorySeasonRace.model(finished = 1)
            )),
            DriverHistorySeason.model(raceOverview = listOf(
                DriverHistorySeasonRace.model(finished = 2)
            )),
            DriverHistorySeason.model(raceOverview = listOf(
                DriverHistorySeasonRace.model(finished = 1)
            ))
        ))

        assertEquals(2, model.careerWins)
    }

    @Test
    fun `career podiums counts all podiums from standings`() {
        val model = DriverHistory.model(standings = listOf(
            DriverHistorySeason.model(raceOverview = listOf(
                DriverHistorySeasonRace.model(finished = 2)
            )),
            DriverHistorySeason.model(raceOverview = listOf(
                DriverHistorySeasonRace.model(finished = 3)
            )),
            DriverHistorySeason.model(raceOverview = listOf(
                DriverHistorySeasonRace.model(finished = 1)
            )),
            DriverHistorySeason.model(raceOverview = listOf(
                DriverHistorySeasonRace.model(finished = 4)
            ))
        ))

        assertEquals(3, model.careerPodiums)
    }

    @Test
    fun `career points counts all points from standings`() {
        val model = DriverHistory.model(standings = listOf(
            DriverHistorySeason.model(points = 3.0),
            DriverHistorySeason.model(points = 24.0),
            DriverHistorySeason.model(points = 102.0)
        ))

        assertEquals(129.0, model.careerPoints)
    }

    @Test
    fun `career races counts all races from standings`() {
        val model = DriverHistory.model(standings = listOf(
            DriverHistorySeason.model(raceOverview = listOf(
                DriverHistorySeasonRace.model(),
                DriverHistorySeasonRace.model()
            )),
            DriverHistorySeason.model(raceOverview = listOf(
                DriverHistorySeasonRace.model()
            )),
            DriverHistorySeason.model(raceOverview = listOf(
                DriverHistorySeasonRace.model(),
                DriverHistorySeasonRace.model(),
                DriverHistorySeasonRace.model()
            ))
        ))

        assertEquals(6, model.careerRaces)
    }

    @Test
    fun `constructors returns grouped pair of season to constructors`() {
        val constructor1 = Constructor.model(id = "constructor1")
        val constructor2 = Constructor.model(id = "constructor2")
        val constructor3 = Constructor.model(id = "constructor3")
        val model = DriverHistory.model(standings = listOf(
            DriverHistorySeason.model(season = 2019, constructors = listOf(
                constructor1,
                constructor2
            )),
            DriverHistorySeason.model(season = 2020, constructors = listOf(
                constructor2,
                constructor3
            )),
            DriverHistorySeason.model(season = 2021, constructors = listOf(
                constructor3
            ))
        ))

        val expected = listOf(
            2019 to constructor1,
            2019 to constructor2,
            2020 to constructor2,
            2020 to constructor3,
            2021 to constructor3
        )

        assertEquals(expected, model.constructors)
    }

    @Test
    fun `race starts sums race starts`() {
        val model = DriverHistory.model(standings = listOf(
            DriverHistorySeason.model(raceOverview = List(4) { DriverHistorySeasonRace.model() }),
            DriverHistorySeason.model(raceOverview = List(8) { DriverHistorySeasonRace.model() })
        ))

        assertEquals(12, model.raceStarts)
    }

    @Test
    fun `race finishes and retirements sums race finishes`() {
        val model = DriverHistory.model(standings = listOf(
            DriverHistorySeason.model(raceOverview = listOf(
                DriverHistorySeasonRace.model(status = RaceStatus.FINISHED),
                DriverHistorySeasonRace.model(status = RaceStatus.LAPS_1),
                DriverHistorySeasonRace.model(status = RaceStatus.WITHDREW)
            )),
            DriverHistorySeason.model(raceOverview = listOf(
                DriverHistorySeasonRace.model(status = RaceStatus.WITHDREW),
                DriverHistorySeasonRace.model(status = RaceStatus.FINISHED),
                DriverHistorySeasonRace.model(status = RaceStatus.LAPS_2),
                DriverHistorySeasonRace.model(status = RaceStatus.GEARBOX)
            ))
        ))

        assertEquals(4, model.raceFinishes)
        assertEquals(3, model.raceRetirements)
    }
}