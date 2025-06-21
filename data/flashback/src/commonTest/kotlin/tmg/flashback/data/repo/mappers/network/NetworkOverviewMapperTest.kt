package tmg.flashback.data.repo.mappers.network

import tmg.flashback.persistence.flashback.models.overview.Overview
import tmg.flashback.persistence.flashback.models.overview.model
import tmg.flashback.flashbackapi.api.models.overview.OverviewRace
import tmg.flashback.flashbackapi.api.models.overview.model
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class NetworkOverviewMapperTest {

    private lateinit var underTest: NetworkOverviewMapper

    internal fun initUnderTest() {
        underTest = NetworkOverviewMapper()
    }

    @Test
    fun `mapOverview maps fields correctly`() {
        initUnderTest()

        val input = OverviewRace.model()
        val expected = Overview.model()

        assertEquals(expected, underTest.mapOverview(input))
    }

    @Test
    fun `mapOverview null when input is null`() {
        initUnderTest()

        assertNull(underTest.mapOverview(null))
    }
}