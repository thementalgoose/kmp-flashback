package tmg.flashback.formula1.model

import tmg.flashback.formula1.enums.RaceStatus
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class DriverHistorySeasonTest {

    @Test
    fun `best finish returns accurately`() {
        val model = DriverHistorySeason.model(raceOverview = listOf(
            DriverHistorySeasonRace.model(finished = 3),
            DriverHistorySeasonRace.model(finished = 0)
        ))

        assertEquals(3, model.bestFinish)
    }

    @Test
    fun `best finish returns null if no finish data`() {
        val model = DriverHistorySeason.model(raceOverview = listOf(
            DriverHistorySeasonRace.model(finished = -1),
            DriverHistorySeasonRace.model(finished = 0)
        ))

        assertNull(model.bestFinish)
    }

    @Test
    fun `best qualifying returns accurately`() {
        val model = DriverHistorySeason.model(raceOverview = listOf(
            DriverHistorySeasonRace.model(qualified = null),
            DriverHistorySeasonRace.model(qualified = 0),
            DriverHistorySeasonRace.model(qualified = 3)
        ))

        assertEquals(3, model.bestQualifying)
    }

    @Test
    fun `best qualifying returns null if no qualified results`() {
        val model = DriverHistorySeason.model(raceOverview = listOf(
            DriverHistorySeasonRace.model(qualified = -1),
            DriverHistorySeasonRace.model(qualified = 0),
            DriverHistorySeasonRace.model(qualified = null)
        ))

        assertNull(model.bestQualifying)
    }

    @Test
    fun `podiums returns finished results of 3 or above`() {
        val model = DriverHistorySeason.model(raceOverview = listOf(
            DriverHistorySeasonRace.model(finished = 1),
            DriverHistorySeasonRace.model(finished = 2),
            DriverHistorySeasonRace.model(finished = 3),
            DriverHistorySeasonRace.model(finished = 4),
            DriverHistorySeasonRace.model(finished = 0),
        ))

        assertEquals(3, model.podiums)
    }

    @Test
    fun `races returns size of race overview`() {
        val expectedRaces = 14
        val model = DriverHistorySeason.model(raceOverview = List(expectedRaces) {
            DriverHistorySeasonRace.model()
        })

        assertEquals(expectedRaces, model.races)
    }

    @Test
    fun `wins returns finished results of 1`() {
        val model = DriverHistorySeason.model(raceOverview = listOf(
            DriverHistorySeasonRace.model(finished = 1),
            DriverHistorySeasonRace.model(finished = 2),
            DriverHistorySeasonRace.model(finished = 3),
            DriverHistorySeasonRace.model(finished = 4),
            DriverHistorySeasonRace.model(finished = 0),
        ))

        assertEquals(1, model.wins)
    }

    @Test
    fun `qualifying on pole returns accurately`() {
        val model = DriverHistorySeason.model(raceOverview = listOf(
            DriverHistorySeasonRace.model(qualified = 1),
            DriverHistorySeasonRace.model(qualified = 2),
            DriverHistorySeasonRace.model(qualified = 3),
            DriverHistorySeasonRace.model(qualified = 4),
            DriverHistorySeasonRace.model(qualified = null)
        ))

        assertEquals(1, model.qualifyingPoles)
    }

    @Test
    fun `qualifying top 3 returns accurately`() {
        val model = DriverHistorySeason.model(raceOverview = listOf(
            DriverHistorySeasonRace.model(qualified = 1),
            DriverHistorySeasonRace.model(qualified = 2),
            DriverHistorySeasonRace.model(qualified = 3),
            DriverHistorySeasonRace.model(qualified = 3),
            DriverHistorySeasonRace.model(qualified = 4),
            DriverHistorySeasonRace.model(qualified = null)
        ))

        assertEquals(4, model.qualifyingTop3)
    }

    @Test
    fun `finishes in points returns accurately`() {
        val model = DriverHistorySeason.model(raceOverview = listOf(
            DriverHistorySeasonRace.model(points = 1.0),
            DriverHistorySeasonRace.model(points = 2.0),
            DriverHistorySeasonRace.model(points = 0.0),
            DriverHistorySeasonRace.model(points = 0.0),
            DriverHistorySeasonRace.model(points = 1.0),
        ))

        assertEquals(3, model.finishesInPoints)
    }

    private val testCasesValidStatuses = listOf(
        "Finished",
        "+1 Lap",
        "+2 Laps",
        "+3 Laps",
        "+4 Laps",
        "+5 Laps",
        "+6 Laps",
        "+7 Laps",
        "+8 Laps",
        "+9 Laps"
    )
    @Test
    fun `race finishes`() {
        testCasesValidStatuses.forEach { status ->
            val model = DriverHistorySeason.model(raceOverview = listOf(
                DriverHistorySeasonRace.model(status = RaceStatus.from(status)),
            ))

            assertEquals(1, model.raceFinishes)
            assertEquals(0, model.raceRetirements)
        }
    }

    private val testCasesInvalidStatuses = listOf(
        "Engine",
        "Wheel nut",
        "Tyre",
        "Wheel",
        "Damage",
        "Steering",
        "Brakes",
        "Vibrations",
        "Suspension",
        "Power Unit",
        "Hydraulics",
        "Water leak",
        "Mechanical",
        "Gearbox",
        "Illness",
        "Debris",
        "Collision",
        "Collision damage",
        "Power loss",
        "Withdrew",
        "Accident",
        "Oil pressure",
        "Disqualified",
        "Puncture",
        "ERS",
        "Electrical",
        "Electronics",
        "Electronic",
        "Driveshaft",
        "Fuel pressure",
        "Spun off",
        "Turbo",
        "Fuel system",
        "Transmission",
        "Clutch",
        "Oil leak",
        "Exhaust",
        "Drivetrain",
        "Rear wing",
        "Front wing",
        "Water pressure",
        "Seat",
        "Battery",
        "Out of fuel",
        "Overheating",
        "Spark plugs",
        "Throttle",
        "Unknown"
    )
    @Test
    fun `race retirements`() {
        testCasesInvalidStatuses.forEach { status ->
            val model = DriverHistorySeason.model(raceOverview = listOf(
                DriverHistorySeasonRace.model(status = RaceStatus.from(status)),
            ))

            assertEquals(0, model.raceFinishes)
            assertEquals(1, model.raceRetirements)
        }
    }

    private data class TestCase(
        val quali1: Int?,
        val quali2: Int?,
        val quali3: Int?,
        val input: Int,
        val expectedResult: Int
    )
    private val testCasesQualiAbove = listOf(
        TestCase(1 ,1 ,1 ,1 ,3),
        TestCase(1 ,1 ,1 ,10,3),
        TestCase(1 ,2 ,1 ,1 ,2),
        TestCase(1 ,2 ,1 ,2 ,3),
        TestCase(12,25,13,10,0),
        TestCase(12,25,10,10,1)
    )
    @Test
    fun `total qualifying above value returns accurate result`() {
        testCasesQualiAbove.forEach { (q1, q2, q3, input, expectedResult) ->
            val model = DriverHistorySeason.model(raceOverview = listOf(
                DriverHistorySeasonRace.model(qualified = q1),
                DriverHistorySeasonRace.model(qualified = q2),
                DriverHistorySeasonRace.model(qualified = q3)
            ))
            assertEquals(expectedResult, model.totalQualifyingAbove(input))
        }
    }
}