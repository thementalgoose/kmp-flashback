package tmg.flashback.data.repo.mappers.network

import tmg.flashback.persistence.flashback.models.standings.ConstructorStanding
import tmg.flashback.persistence.flashback.models.standings.ConstructorStandingDriver
import tmg.flashback.persistence.flashback.models.standings.model
import tmg.flashback.flashbackapi.api.models.races.ConstructorStandings
import tmg.flashback.flashbackapi.api.models.races.model
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

internal class NetworkConstructorStandingMapperTest {

    private lateinit var underTest: NetworkConstructorStandingMapper

    internal fun initUnderTest() {
        underTest = NetworkConstructorStandingMapper()
    }

    @Test
    fun `mapConstructorStanding maps fields correctly`() {
        initUnderTest()

        val inputSeason: Int = 2020
        val inputStandings = ConstructorStandings.model()
        val expected = ConstructorStanding.model()

        assertEquals(expected, underTest.mapConstructorStanding(inputSeason, inputStandings))
    }

    @Test
    fun `mapConstructorStanding null progress defaults to false`() {
        initUnderTest()

        val inputSeason: Int = 2020
        val inputStandings = ConstructorStandings.model(inProgress = null)

        assertFalse(underTest.mapConstructorStanding(inputSeason, inputStandings).inProgress)
    }

    @Test
    fun `mapConstructorStandingDriver maps fields correctly`() {
        initUnderTest()

        val inputSeason: Int = 2020
        val inputStandings = ConstructorStandings.model()
        val expected = listOf(ConstructorStandingDriver.model())

        assertEquals(expected, underTest.mapConstructorStandingDriver(inputSeason, inputStandings))
    }
}