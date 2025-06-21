package tmg.flashback.data.repo.mappers.network

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tmg.flashback.flashbackapi.NetworkSchedule
import tmg.flashback.data.persistence.RoomSchedule
import tmg.flashback.persistence.flashback.models.overview.Schedule
import tmg.flashback.persistence.flashback.models.overview.model
import tmg.flashback.flashbackapi.api.models.overview.OverviewRace
import tmg.flashback.flashbackapi.api.models.overview.model
import tmg.flashback.flashbackapi.api.models.races.Race
import tmg.flashback.flashbackapi.api.models.races.model

internal class NetworkScheduleMapperTest {

    private lateinit var sut: NetworkScheduleMapper

    @BeforeEach
    internal fun setUp() {
        sut = NetworkScheduleMapper()
    }

    @Test
    fun `mapSchedule race overview maps fields correctly`() {
        val input = OverviewRace.model()
        val expected: List<Schedule> = listOf(Schedule.model())

        assertEquals(expected, sut.mapSchedules(input))
    }

    @Test
    fun `mapSchedule race maps fields correctly`() {
        val input = Race.model()
        val expected: List<Schedule> = listOf(Schedule.model())

        assertEquals(expected, sut.mapSchedules(input))
    }

    @Test
    fun `mapSchedule schedule maps fields correctly`() {
        val inputSeason = 2020
        val inputRound = 1
        val input = NetworkSchedule.model()
        val expected = RoomSchedule.model()

        assertEquals(expected, sut.mapSchedule(inputSeason, inputRound, input))
    }
}