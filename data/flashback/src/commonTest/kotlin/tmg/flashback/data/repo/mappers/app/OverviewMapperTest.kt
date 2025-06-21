package tmg.flashback.data.repo.mappers.app

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.format.DateTimeParseException
import tmg.flashback.persistence.flashback.models.overview.Overview
import tmg.flashback.persistence.flashback.models.overview.OverviewWithCircuit
import tmg.flashback.persistence.flashback.models.overview.model
import tmg.flashback.formula1.model.OverviewRace
import tmg.flashback.formula1.model.Schedule
import tmg.flashback.formula1.model.model

internal class OverviewMapperTest {

    private val mockScheduleMapper: ScheduleMapper = mockk(relaxed = true)

    private lateinit var sut: OverviewMapper

    @BeforeEach
    internal fun setUp() {
        sut = OverviewMapper(mockScheduleMapper)

        every { mockScheduleMapper.mapSchedule(any()) } returns Schedule.model()
    }

    @Test
    fun `mapOverview maps fields correctly`() {
        val input = OverviewWithCircuit.model()
        val expected = OverviewRace.model()

        assertEquals(expected, sut.mapOverview(input))
    }

    @Test
    fun `mapOverview throws error if date is invalid`() {
        val input = OverviewWithCircuit.model(overview = Overview.model(date = "invalid"))

        assertThrows(DateTimeParseException::class.java) {
            sut.mapOverview(input)
        }
    }

    @Test
    fun `mapOverview returns null time if invalid`() {
        val input = OverviewWithCircuit.model(overview = Overview.model(time = "invalid"))

        assertNull(sut.mapOverview(input).time)
    }
}