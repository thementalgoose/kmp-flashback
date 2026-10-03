package tmg.flashback.formula1.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import tmg.flashback.infrastructure.datetime.now
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ScheduleTest {

    @Test
    fun `timestamp field uses date and time`() {
        val date = LocalDate.now()
        val time = LocalTime.now()

        val model = Schedule.model(date = date, time = time)

        assertEquals(Timestamp(date, time), model.timestamp)
    }
}