package tmg.flashback.data.repo.mappers.network

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tmg.flashback.persistence.flashback.models.drivers.DriverSeason
import tmg.flashback.persistence.flashback.models.drivers.DriverSeasonRace
import tmg.flashback.persistence.flashback.models.drivers.model
import tmg.flashback.flashbackapi.api.models.drivers.DriverHistoryStanding
import tmg.flashback.flashbackapi.api.models.drivers.DriverHistoryStandingRace
import tmg.flashback.flashbackapi.api.models.drivers.model

internal class NetworkDriverMapperTest {

    private lateinit var sut: NetworkDriverMapper

    @BeforeEach
    internal fun setUp() {
        sut = NetworkDriverMapper()
    }

    @Test
    fun `mapDriverSeason maps fields correctly`() {
        val inputDriverId: String = "driverId"
        val inputDriver = DriverHistoryStanding.model()
        val expected = DriverSeason.model()

        assertEquals(expected, sut.mapDriverSeason(inputDriverId, inputDriver))
    }

    @Test
    fun `mapDriverSeasonRace maps fields correctly`() {
        val inputDriverId: String = "driverId"
        val inputSeason: Int = 2020
        val inputData = DriverHistoryStandingRace.model()
        val expected = DriverSeasonRace.model()

        assertEquals(expected, sut.mapDriverSeasonRace(inputDriverId, inputSeason, inputData))
    }

    @Test
    fun `mapDriverSeasonRace null sprint quali defaults to false`() {
        val inputDriverId: String = "driverId"
        val inputSeason: Int = 2020
        val inputData = DriverHistoryStandingRace.model(sprint = null)

        assertFalse(sut.mapDriverSeasonRace(inputDriverId, inputSeason, inputData).sprintRace)
        assertFalse(sut.mapDriverSeasonRace(inputDriverId, inputSeason, inputData).sprintQualifying)
    }
}