package tmg.flashback.formula1.model

import kotlin.test.Test
import kotlin.test.assertEquals

internal class SeasonTest {

    @Test
    fun `drivers defaults to all drivers in races`() {
        val driver1 = Driver.model(id = "1")
        val driver2 = Driver.model(id = "2")
        val input = Season.model(races = listOf(
            Race.model(
                raceInfo = RaceInfo.model(round = 1),
                race = listOf(
                    RaceResult.model(driver = DriverEntry.model(driver = driver1))
                ),
                qualifying = emptyList(),
                sprint = SprintResult(emptyList(), emptyList())
            ),
            Race.model(
                raceInfo = RaceInfo.model(round = 2),
                race = listOf(
                    RaceResult.model(driver = DriverEntry.model(driver = driver2)),
                    RaceResult.model(driver = DriverEntry.model(driver = driver1))
                ),
                qualifying = emptyList(),
                sprint = SprintResult(emptyList(), emptyList())
            )
        ))

        assertEquals(setOf(driver1, driver2), input.drivers)
    }

    @Test
    fun `constructors defaults to all constructors in races`() {
        val constructor1 = Constructor.model(id = "1")
        val constructor2 = Constructor.model(id = "2")
        val input = Season.model(races = listOf(
            Race.model(
                raceInfo = RaceInfo.model(round = 1),
                race = listOf(
                    RaceResult.model(driver = DriverEntry.model(constructor = constructor1))
                ),
                qualifying = emptyList(),
                sprint = SprintResult(emptyList(), emptyList())
            ),
            Race.model(
                raceInfo = RaceInfo.model(round = 2),
                race = listOf(
                    RaceResult.model(driver = DriverEntry.model(constructor = constructor2)),
                    RaceResult.model(driver = DriverEntry.model(constructor = constructor1))
                ),
                qualifying = emptyList(),
                sprint = SprintResult(emptyList(), emptyList())
            )
        ))

        assertEquals(setOf(constructor1, constructor2), input.constructors)
    }

    @Test
    fun `circuits returns list of all circuits`() {
        val input = Season.model(races = listOf(
            Race.model(raceInfo = RaceInfo.model(circuit = Circuit.model(
                id = "id1"
            ))),
            Race.model(raceInfo = RaceInfo.model(circuit = Circuit.model(
                id = "id1"
            ))),
            Race.model(raceInfo = RaceInfo.model(circuit = Circuit.model(
                id = "id2"
            )))
        ))

        assertEquals(listOf("id1", "id2"), input.circuits.map { it.id })
    }

    @Test
    fun `first race returns race with min round`() {
        val input = Season.model(
            races = listOf(
                Race.model(raceInfo = RaceInfo.model(round = 3, name = "min")),
                Race.model(raceInfo = RaceInfo.model(round = 4, name = "max"))
            )
        )

        assertEquals(3, input.firstRace!!.raceInfo.round)
    }

    @Test
    fun `last race returns race with max round`() {
        val input = Season.model(
            races = listOf(
                Race.model(raceInfo = RaceInfo.model(round = 3, name = "min")),
                Race.model(raceInfo = RaceInfo.model(round = 4, name = "max"))
            )
        )

        assertEquals(4, input.lastRace!!.raceInfo.round)
    }
}