package tmg.flashback.data.repo.mappers.app

import tmg.flashback.data.repo.fakes.fakeConstructorDataMapper
import tmg.flashback.data.repo.fakes.fakeDriverDataMapper
import tmg.flashback.data.repo.fakes.fakeRaceMapper
import tmg.flashback.formula1.model.DriverHistory
import tmg.flashback.formula1.model.model
import tmg.flashback.persistence.flashback.RoomDriverHistory
import tmg.flashback.persistence.flashback.models.drivers.model
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class DriverMapperTest {

    private lateinit var underTest: DriverMapper

    internal fun initUnderTest() {
        underTest = DriverMapper(
            driverDataMapper = fakeDriverDataMapper(),
            constructorDataMapper = fakeConstructorDataMapper(),
            raceDataMapper = fakeRaceMapper()
        )
    }

    @Test
    fun `mapDriver maps fields correctly`() {
        initUnderTest()

        val input = RoomDriverHistory.model()
        val expected = DriverHistory.model()

        assertEquals(expected, underTest.mapDriver(input))
    }

    @Test
    fun `mapDriver returns null if input is null`() {
        initUnderTest()

        assertNull(underTest.mapDriver(null))
    }
}