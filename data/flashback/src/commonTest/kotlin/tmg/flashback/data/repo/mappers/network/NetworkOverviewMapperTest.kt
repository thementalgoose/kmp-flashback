package tmg.flashback.data.repo.mappers.network

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tmg.flashback.persistence.flashback.models.overview.Overview
import tmg.flashback.persistence.flashback.models.overview.model
import tmg.flashback.flashbackapi.api.models.overview.OverviewRace
import tmg.flashback.flashbackapi.api.models.overview.model

internal class NetworkOverviewMapperTest {

    private lateinit var sut: NetworkOverviewMapper

    @BeforeEach
    internal fun setUp() {
        sut = NetworkOverviewMapper()
    }

    @Test
    fun `mapOverview maps fields correctly`() {
        val input = OverviewRace.model()
        val expected = Overview.model()

        assertEquals(expected, sut.mapOverview(input))
    }

    @Test
    fun `mapOverview null when input is null`() {
        assertNull(sut.mapOverview(null))
    }
}