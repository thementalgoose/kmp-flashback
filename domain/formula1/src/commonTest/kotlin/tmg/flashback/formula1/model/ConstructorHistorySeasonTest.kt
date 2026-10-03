package tmg.flashback.formula1.model

import kotlin.test.Test
import kotlin.test.assertEquals

internal class ConstructorHistorySeasonTest {

    @Test
    fun `qualifying pole returns all pole position`() {
        val model = ConstructorHistorySeason.model(drivers = mapOf(
            "driver1" to ConstructorHistorySeasonDriver.model(polePosition = 3),
            "driver2" to ConstructorHistorySeasonDriver.model(polePosition = 7)
        ))

        assertEquals(10, model.qualifyingPole)
    }

    @Test
    fun `driver points returns accurate points count`() {
        val model = ConstructorHistorySeason.model(drivers = mapOf(
            "driver1" to ConstructorHistorySeasonDriver.model(points = 3.0),
            "driver2" to ConstructorHistorySeasonDriver.model(points = 7.0)
        ))

        assertEquals(10.0, model.driverPoints)
    }

    @Test
    fun `finishes in points returns accurate count`() {
        val model = ConstructorHistorySeason.model(drivers = mapOf(
            "driver1" to ConstructorHistorySeasonDriver.model(finishesInPoints = 1),
            "driver2" to ConstructorHistorySeasonDriver.model(finishesInPoints = 2),
            "driver3" to ConstructorHistorySeasonDriver.model(finishesInPoints = 0)
        ))

        assertEquals(3, model.finishInPoints)
    }

    @Test
    fun `number of wins returns accurate value`() {
        val model = ConstructorHistorySeason.model(drivers = mapOf(
            "driver1" to ConstructorHistorySeasonDriver.model(wins = 3),
            "driver2" to ConstructorHistorySeasonDriver.model(wins = 7),
            "driver3" to ConstructorHistorySeasonDriver.model(wins = 0)
        ))

        assertEquals(10, model.wins)
    }

    @Test
    fun `number of podiums returns accurate value`() {
        val model = ConstructorHistorySeason.model(drivers = mapOf(
            "driver1" to ConstructorHistorySeasonDriver.model(
                driver = DriverEntry.model(driver = Driver.model(id = "driver1")),
                podiums = 3
            ),
            "driver2" to ConstructorHistorySeasonDriver.model(
                driver = DriverEntry.model(driver = Driver.model(id = "driver2")),
                podiums = 7
            ),
            "driver3" to ConstructorHistorySeasonDriver.model(podiums = 0)
        ))

        assertEquals(10, model.podiums)
    }
}