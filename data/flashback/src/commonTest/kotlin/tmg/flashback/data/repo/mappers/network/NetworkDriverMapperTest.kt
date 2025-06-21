package tmg.flashback.data.repo.mappers.network

import tmg.flashback.persistence.flashback.models.drivers.DriverSeason
import tmg.flashback.persistence.flashback.models.drivers.DriverSeasonRace
import tmg.flashback.persistence.flashback.models.drivers.model
import tmg.flashback.flashbackapi.api.models.drivers.DriverHistoryStanding
import tmg.flashback.flashbackapi.api.models.drivers.DriverHistoryStandingRace
import tmg.flashback.flashbackapi.api.models.drivers.model
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

internal class NetworkDriverMapperTest {

    private lateinit var underTest: NetworkDriverMapper

    internal fun initUnderTest() {
        underTest = NetworkDriverMapper()
    }

    @Test
    fun `mapDriverSeason maps fields correctly`() {
        initUnderTest()

        val inputDriverId: String = "driverId"
        val inputDriver = DriverHistoryStanding.model()
        val expected = DriverSeason.model()

        assertEquals(expected, underTest.mapDriverSeason(inputDriverId, inputDriver))
    }

    @Test
    fun `mapDriverSeasonRace maps fields correctly`() {
        initUnderTest()

        val inputDriverId: String = "driverId"
        val inputSeason: Int = 2020
        val inputData = DriverHistoryStandingRace.model()
        val expected = DriverSeasonRace.model()

        assertEquals(expected, underTest.mapDriverSeasonRace(inputDriverId, inputSeason, inputData))
    }

    @Test
    fun `mapDriverSeasonRace null sprint quali defaults to false`() {
        initUnderTest()

        val inputDriverId: String = "driverId"
        val inputSeason: Int = 2020
        val inputData = DriverHistoryStandingRace.model(sprint = null)

        assertFalse(underTest.mapDriverSeasonRace(inputDriverId, inputSeason, inputData).sprintRace)
        assertFalse(underTest.mapDriverSeasonRace(inputDriverId, inputSeason, inputData).sprintQualifying)
    }
}