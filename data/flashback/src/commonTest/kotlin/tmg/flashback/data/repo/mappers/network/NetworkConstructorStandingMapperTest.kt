package tmg.flashback.data.repo.mappers.network

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tmg.flashback.persistence.flashback.models.standings.ConstructorStanding
import tmg.flashback.persistence.flashback.models.standings.ConstructorStandingDriver
import tmg.flashback.persistence.flashback.models.standings.model
import tmg.flashback.flashbackapi.api.models.races.ConstructorStandings
import tmg.flashback.flashbackapi.api.models.races.model

internal class NetworkConstructorStandingMapperTest {

    private lateinit var sut: NetworkConstructorStandingMapper

    @BeforeEach
    internal fun setUp() {
        sut = NetworkConstructorStandingMapper()
    }

    @Test
    fun `mapConstructorStanding maps fields correctly`() {
        val inputSeason: Int = 2020
        val inputStandings = ConstructorStandings.model()
        val expected = ConstructorStanding.model()

        assertEquals(expected, sut.mapConstructorStanding(inputSeason, inputStandings))
    }

    @Test
    fun `mapConstructorStanding null progress defaults to false`() {
        val inputSeason: Int = 2020
        val inputStandings = ConstructorStandings.model(inProgress = null)

        assertFalse(sut.mapConstructorStanding(inputSeason, inputStandings).inProgress)
    }

    @Test
    fun `mapConstructorStandingDriver maps fields correctly`() {
        val inputSeason: Int = 2020
        val inputStandings = ConstructorStandings.model()
        val expected = listOf(ConstructorStandingDriver.model())

        assertEquals(expected, sut.mapConstructorStandingDriver(inputSeason, inputStandings))
    }
}