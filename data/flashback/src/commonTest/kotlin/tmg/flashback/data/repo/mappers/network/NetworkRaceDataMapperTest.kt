package tmg.flashback.data.repo.mappers.network

import tmg.flashback.flashbackapi.NetworkRaceData
import tmg.flashback.persistence.flashback.models.race.RaceInfo
import tmg.flashback.persistence.flashback.models.race.model
import tmg.flashback.flashbackapi.api.models.races.model
import kotlin.test.Test
import kotlin.test.assertEquals

internal class NetworkRaceDataMapperTest {

    private lateinit var underTest: NetworkRaceDataMapper

    internal fun initUnderTest() {
        underTest = NetworkRaceDataMapper()
    }

    @Test
    fun `mapRaceData maps fields correctly`() {
        initUnderTest()

        val input = NetworkRaceData.model()
        val expected = RaceInfo.model()

        assertEquals(expected, underTest.mapRaceData(input))
    }
}