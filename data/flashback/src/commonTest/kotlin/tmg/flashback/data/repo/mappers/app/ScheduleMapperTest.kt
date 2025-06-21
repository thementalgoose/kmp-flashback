package tmg.flashback.data.repo.mappers.app

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tmg.flashback.data.persistence.RoomSchedule
import tmg.flashback.persistence.flashback.models.overview.model
import tmg.flashback.formula1.model.Schedule
import tmg.flashback.formula1.model.model

internal class ScheduleMapperTest {

    private lateinit var sut: ScheduleMapper

    @BeforeEach
    internal fun setUp() {
        sut = ScheduleMapper()
    }

    @Test
    fun `mapSchedule maps fields correctly`() {
        val input = RoomSchedule.model()
        val expected = Schedule.model()

        assertEquals(expected, sut.mapSchedule(input))
    }

    @Test
    fun `mapSchedule returns null if input is null`() {
        assertNull(sut.mapSchedule(null))
    }

    @Test
    fun `mapSchedule returns null if date is null`() {
        val input = RoomSchedule.model(date = "invalid")

        assertNull(sut.mapSchedule(input))
    }

    @Test
    fun `mapSchedule returns null if time is null`() {
        val input = RoomSchedule.model(time = "invalid")

        assertNull(sut.mapSchedule(input))
    }
}