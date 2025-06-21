package tmg.flashback.data.repo.mappers.app

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.format.DateTimeParseException
import tmg.flashback.data.persistence.RoomRace
import tmg.flashback.data.persistence.RoomRaceInfo
import tmg.flashback.data.persistence.RoomRaceInfoWithCircuit
import tmg.flashback.persistence.flashback.models.race.model
import tmg.flashback.formula1.model.Circuit
import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.Driver
import tmg.flashback.formula1.model.Race
import tmg.flashback.formula1.model.RaceInfo
import tmg.flashback.formula1.model.Schedule
import tmg.flashback.formula1.model.model

internal class RaceMapperTest {

    private val mockCircuitMapper: CircuitMapper = mockk(relaxed = true)
    private val mockDriverDataMapper: DriverDataMapper = mockk(relaxed = true)
    private val mockConstructorDataMapper: ConstructorDataMapper = mockk(relaxed = true)
    private val mockScheduleMapper: ScheduleMapper = mockk(relaxed = true)

    private lateinit var sut: RaceMapper

    @BeforeEach
    internal fun setUp() {
        sut = RaceMapper(
            mockCircuitMapper,
            mockDriverDataMapper,
            mockConstructorDataMapper,
            mockScheduleMapper
        )

        every { mockCircuitMapper.mapCircuit(any()) } returns Circuit.model()
        every { mockDriverDataMapper.mapDriver(any()) } returns Driver.model()
        every { mockConstructorDataMapper.mapConstructorData(any()) } returns Constructor.model()
        every { mockScheduleMapper.mapSchedule(any()) } returns Schedule.model()
    }

    @Test
    fun `mapRaceInfo maps fields correctly`() {
        val input = RoomRace.model()
        val expected = RaceInfo.model()

        assertEquals(expected, sut.mapRaceInfo(input))
    }

    @Test
    fun `mapRaceInfo throws error if date is invalid`() {
        val input = RoomRace.model(raceInfo = RoomRaceInfo.model(date = "invalid"))

        assertThrows(DateTimeParseException::class.java) {
            sut.mapRaceInfo(input)
        }
    }

    @Test
    fun `mapRaceInfo returns null if time is invalid`() {
        val input = RoomRace.model(raceInfo = RoomRaceInfo.model(time = "invalid"))

        assertNull(sut.mapRaceInfo(input).time)
    }

    @Test
    fun `mapRaceInfoWithCircuit maps fields correctly`() {
        val input = RoomRaceInfoWithCircuit.model()
        val expected = RaceInfo.model()

        assertEquals(expected, sut.mapRaceInfoWithCircuit(input))
    }

    @Test
    fun `mapRaceInfoWithCircuit throws error if date is invalid`() {
        val input = RoomRaceInfoWithCircuit.model(raceInfo = RoomRaceInfo.model(date = "invalid"))

        assertThrows(DateTimeParseException::class.java) {
            sut.mapRaceInfoWithCircuit(input)
        }
    }

    @Test
    fun `mapRaceInfoWithCircuit returns null if time is invalid`() {
        val input = RoomRaceInfoWithCircuit.model(raceInfo = RoomRaceInfo.model(time = "invalid"))

        assertNull(sut.mapRaceInfoWithCircuit(input).time)
    }

    @Test
    fun `mapRace maps fields correctly`() {
        val input = RoomRace.model()
        val expected = Race.model()

        assertEquals(expected, sut.mapRace(input))
    }
}