package tmg.flashback.data.repo.mappers.app

import tmg.flashback.persistence.flashback.models.overview.model
import tmg.flashback.formula1.model.Schedule
import tmg.flashback.formula1.model.model
import tmg.flashback.persistence.flashback.RoomSchedule
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class ScheduleMapperTest {

    private lateinit var underTest: ScheduleMapper

    internal fun initUnderTest() {
        underTest = ScheduleMapper()
    }

    @Test
    fun `mapSchedule maps fields correctly`() {
        initUnderTest()

        val input = RoomSchedule.model()
        val expected = Schedule.model()

        assertEquals(expected, underTest.mapSchedule(input))
    }

    @Test
    fun `mapSchedule returns null if input is null`() {
        initUnderTest()

        assertNull(underTest.mapSchedule(null))
    }

    @Test
    fun `mapSchedule returns null if date is null`() {
        initUnderTest()

        val input = RoomSchedule.model(date = "invalid")

        assertNull(underTest.mapSchedule(input))
    }

    @Test
    fun `mapSchedule returns null if time is null`() {
        initUnderTest()

        val input = RoomSchedule.model(time = "invalid")

        assertNull(underTest.mapSchedule(input))
    }
}