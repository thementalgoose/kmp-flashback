package tmg.flashback.data.repo.mappers.network

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tmg.flashback.persistence.flashback.models.overview.model
import tmg.flashback.flashbackapi.api.models.overview.Event
import tmg.flashback.flashbackapi.api.models.overview.model

internal class NetworkEventMapperTest {

    private lateinit var sut: NetworkEventMapper

    @BeforeEach
    internal fun setUp() {
        sut = NetworkEventMapper()
    }

    @Test
    fun `mapWinterTesting maps fields correctly`() {
        val input = Event.model()
        val expected = tmg.flashback.persistence.flashback.models.overview.Event.model()

        assertEquals(expected, sut.mapEvent(2020, input))
    }

    @Test
    fun `mapWinterTesting returns null if date is invalid`() {
        val input = Event.model(
            date = "invalid"
        )

        assertNull(sut.mapEvent(2020, input))
    }
}