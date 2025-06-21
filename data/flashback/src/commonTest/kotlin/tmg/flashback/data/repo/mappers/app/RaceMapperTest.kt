package tmg.flashback.data.repo.mappers.app

import tmg.flashback.data.repo.fakes.fakeCircuitMapper
import tmg.flashback.data.repo.fakes.fakeConstructorDataMapper
import tmg.flashback.data.repo.fakes.fakeDriverDataMapper
import tmg.flashback.data.repo.fakes.fakeScheduleMapper
import tmg.flashback.persistence.flashback.models.race.model
import tmg.flashback.formula1.model.Race
import tmg.flashback.formula1.model.RaceInfo
import tmg.flashback.formula1.model.model
import tmg.flashback.persistence.flashback.RoomRace
import tmg.flashback.persistence.flashback.RoomRaceInfo
import tmg.flashback.persistence.flashback.RoomRaceInfoWithCircuit
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertNull

internal class RaceMapperTest {

    private lateinit var underTest: RaceMapper

    internal fun initUnderTest() {
        underTest = RaceMapper(
            circuitMapper = fakeCircuitMapper(),
            driverDataMapper = fakeDriverDataMapper(),
            constructorDataMapper = fakeConstructorDataMapper(),
            scheduleMapper = fakeScheduleMapper()
        )
    }

    @Test
    fun `mapRaceInfo maps fields correctly`() {
        initUnderTest()

        val input = RoomRace.model()
        val expected = RaceInfo.model()

        assertEquals(expected, underTest.mapRaceInfo(input))
    }

    @Test
    fun `mapRaceInfo throws error if date is invalid`() {
        initUnderTest()

        val input = RoomRace.model(raceInfo = RoomRaceInfo.model(date = "invalid"))

        assertFails {
            underTest.mapRaceInfo(input)
        }
    }

    @Test
    fun `mapRaceInfo returns null if time is invalid`() {
        initUnderTest()

        val input = RoomRace.model(raceInfo = RoomRaceInfo.model(time = "invalid"))

        assertNull(underTest.mapRaceInfo(input).time)
    }

    @Test
    fun `mapRaceInfoWithCircuit maps fields correctly`() {
        initUnderTest()

        val input = RoomRaceInfoWithCircuit.model()
        val expected = RaceInfo.model()

        assertEquals(expected, underTest.mapRaceInfoWithCircuit(input))
    }

    @Test
    fun `mapRaceInfoWithCircuit throws error if date is invalid`() {
        initUnderTest()

        val input = RoomRaceInfoWithCircuit.model(raceInfo = RoomRaceInfo.model(date = "invalid"))

        assertFails {
            underTest.mapRaceInfoWithCircuit(input)
        }
    }

    @Test
    fun `mapRaceInfoWithCircuit returns null if time is invalid`() {
        initUnderTest()

        val input = RoomRaceInfoWithCircuit.model(raceInfo = RoomRaceInfo.model(time = "invalid"))

        assertNull(underTest.mapRaceInfoWithCircuit(input).time)
    }

    @Test
    fun `mapRace maps fields correctly`() {
        initUnderTest()

        val input = RoomRace.model()
        val expected = Race.model()

        assertEquals(expected, underTest.mapRace(input))
    }
}