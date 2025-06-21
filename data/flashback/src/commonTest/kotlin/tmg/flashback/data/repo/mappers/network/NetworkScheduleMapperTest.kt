package tmg.flashback.data.repo.mappers.network

import tmg.flashback.flashbackapi.NetworkSchedule
import tmg.flashback.persistence.flashback.models.overview.Schedule
import tmg.flashback.persistence.flashback.models.overview.model
import tmg.flashback.flashbackapi.api.models.overview.OverviewRace
import tmg.flashback.flashbackapi.api.models.overview.model
import tmg.flashback.flashbackapi.api.models.races.Race
import tmg.flashback.flashbackapi.api.models.races.model
import tmg.flashback.persistence.flashback.RoomSchedule
import kotlin.test.Test
import kotlin.test.assertEquals

internal class NetworkScheduleMapperTest {

    private lateinit var underTest: NetworkScheduleMapper

    internal fun initUnderTest() {
        underTest = NetworkScheduleMapper()
    }

    @Test
    fun `mapSchedule race overview maps fields correctly`() {
        initUnderTest()

        val input = OverviewRace.model()
        val expected: List<Schedule> = listOf(Schedule.model())

        assertEquals(expected, underTest.mapSchedules(input))
    }

    @Test
    fun `mapSchedule race maps fields correctly`() {
        initUnderTest()

        val input = Race.model()
        val expected: List<Schedule> = listOf(Schedule.model())

        assertEquals(expected, underTest.mapSchedules(input))
    }

    @Test
    fun `mapSchedule schedule maps fields correctly`() {
        initUnderTest()

        val inputSeason = 2020
        val inputRound = 1
        val input = NetworkSchedule.model()
        val expected = RoomSchedule.model()

        assertEquals(expected, underTest.mapSchedule(inputSeason, inputRound, input))
    }
}