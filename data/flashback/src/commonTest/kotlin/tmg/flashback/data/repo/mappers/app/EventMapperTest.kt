package tmg.flashback.data.repo.mappers.app

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tmg.flashback.persistence.flashback.models.overview.Event
import tmg.flashback.persistence.flashback.models.overview.model
import tmg.flashback.formula1.model.model

internal class EventMapperTest {

    private lateinit var sut: EventMapper

    @BeforeEach
    internal fun setUp() {
        sut = EventMapper()
    }

    @Test
    fun `mapWinterTesting maps fields correctly`() {
        val inputModel = Event.model()
        val expected = tmg.flashback.formula1.model.Event.model()

        assertEquals(expected, sut.mapEvent(inputModel))
    }

    @Test
    fun `mapWinterTesting returns null when date is invalid`() {
        val inputModel = Event.model(
            date = "invalid"
        )

        assertNull(sut.mapEvent(inputModel))
    }
}