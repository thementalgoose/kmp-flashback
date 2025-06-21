package tmg.flashback.data.repo.mappers.network

import tmg.flashback.persistence.flashback.models.overview.model
import tmg.flashback.flashbackapi.api.models.overview.Event
import tmg.flashback.flashbackapi.api.models.overview.model
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class NetworkEventMapperTest {

    private lateinit var underTest: NetworkEventMapper

    internal fun initUnderTest() {
        underTest = NetworkEventMapper()
    }

    @Test
    fun `mapWinterTesting maps fields correctly`() {
        initUnderTest()

        val input = Event.model()
        val expected = tmg.flashback.persistence.flashback.models.overview.Event.model()

        assertEquals(expected, underTest.mapEvent(2020, input))
    }

    @Test
    fun `mapWinterTesting returns null if date is invalid`() {
        initUnderTest()

        val input = Event.model(
            date = "invalid"
        )

        assertNull(underTest.mapEvent(2020, input))
    }
}