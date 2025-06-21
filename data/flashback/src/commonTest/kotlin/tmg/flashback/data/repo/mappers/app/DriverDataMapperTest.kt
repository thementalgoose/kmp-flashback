package tmg.flashback.data.repo.mappers.app

import tmg.flashback.persistence.flashback.models.drivers.model
import tmg.flashback.formula1.model.Driver
import tmg.flashback.formula1.model.model
import tmg.flashback.persistence.flashback.RoomDriver
import kotlin.test.Test
import kotlin.test.assertEquals

internal class DriverDataMapperTest {

    private lateinit var sut: DriverDataMapper

    internal fun initUnderTest() {
        sut = DriverDataMapper()
    }

    @Test
    fun `mapDriver maps fields correctly`() {
        initUnderTest()

        val input = RoomDriver.model()
        val expected = Driver.model()

        assertEquals(expected, sut.mapDriver(input))
    }
}