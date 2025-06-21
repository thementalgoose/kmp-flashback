package tmg.flashback.data.repo.mappers.network

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tmg.flashback.persistence.flashback.models.standings.DriverStanding
import tmg.flashback.persistence.flashback.models.standings.DriverStandingConstructor
import tmg.flashback.persistence.flashback.models.standings.model
import tmg.flashback.flashbackapi.api.models.races.DriverStandings
import tmg.flashback.flashbackapi.api.models.races.model

internal class NetworkDriverStandingMapperTest {

    private lateinit var sut: NetworkDriverStandingMapper

    @BeforeEach
    internal fun setUp() {
        sut = NetworkDriverStandingMapper()
    }

    @Test
    fun `mapDriverStanding maps fields correctly`() {
        val inputSeason = 2020
        val inputData = DriverStandings.model()
        val expected = DriverStanding.model()

        assertEquals(expected, sut.mapDriverStanding(inputSeason, inputData))
    }

    @Test
    fun `mapDriverStanding null progress defaults to false`() {
        val inputSeason = 2020
        val inputData = DriverStandings.model(inProgress = null)

        assertFalse(sut.mapDriverStanding(inputSeason, inputData).inProgress)
    }

    @Test
    fun `mapDriverStandingConstructor maps fields correctly`() {
        val inputSeason = 2020
        val inputData = DriverStandings.model()
        val expected = listOf(DriverStandingConstructor.model())

        assertEquals(expected, sut.mapDriverStandingConstructor(inputSeason, inputData))
    }
}