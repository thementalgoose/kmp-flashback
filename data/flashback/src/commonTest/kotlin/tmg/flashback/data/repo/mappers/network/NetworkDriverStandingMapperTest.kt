package tmg.flashback.data.repo.mappers.network

import tmg.flashback.persistence.flashback.models.standings.DriverStanding
import tmg.flashback.persistence.flashback.models.standings.DriverStandingConstructor
import tmg.flashback.persistence.flashback.models.standings.model
import tmg.flashback.flashbackapi.api.models.races.DriverStandings
import tmg.flashback.flashbackapi.api.models.races.model
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

internal class NetworkDriverStandingMapperTest {

    private lateinit var underTest: NetworkDriverStandingMapper

    internal fun initUnderTest() {
        underTest = NetworkDriverStandingMapper()
    }

    @Test
    fun `mapDriverStanding maps fields correctly`() {
        initUnderTest()

        val inputSeason = 2020
        val inputData = DriverStandings.model()
        val expected = DriverStanding.model()

        assertEquals(expected, underTest.mapDriverStanding(inputSeason, inputData))
    }

    @Test
    fun `mapDriverStanding null progress defaults to false`() {
        initUnderTest()

        val inputSeason = 2020
        val inputData = DriverStandings.model(inProgress = null)

        assertFalse(underTest.mapDriverStanding(inputSeason, inputData).inProgress)
    }

    @Test
    fun `mapDriverStandingConstructor maps fields correctly`() {
        initUnderTest()

        val inputSeason = 2020
        val inputData = DriverStandings.model()
        val expected = listOf(DriverStandingConstructor.model())

        assertEquals(expected, underTest.mapDriverStandingConstructor(inputSeason, inputData))
    }
}