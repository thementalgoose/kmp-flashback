package tmg.flashback.data.repo.mappers.app

import tmg.flashback.persistence.flashback.models.overview.Event
import tmg.flashback.persistence.flashback.models.overview.model
import tmg.flashback.formula1.model.model
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class EventMapperTest {

    private lateinit var sut: EventMapper

    internal fun initUnderTest() {
        sut = EventMapper()
    }

    @Test
    fun `mapWinterTesting maps fields correctly`() {
        initUnderTest()

        val inputModel = Event.model()
        val expected = tmg.flashback.formula1.model.Event.model()

        assertEquals(expected, sut.mapEvent(inputModel))
    }

    @Test
    fun `mapWinterTesting returns null when date is invalid`() {
        initUnderTest()

        val inputModel = Event.model(
            date = "invalid"
        )

        assertNull(sut.mapEvent(inputModel))
    }
}