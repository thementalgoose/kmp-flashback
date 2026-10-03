package tmg.flashback.data.repo.mappers.network

import tmg.flashback.flashbackapi.NetworkDriver
import tmg.flashback.persistence.flashback.models.drivers.Driver
import tmg.flashback.persistence.flashback.models.drivers.model
import tmg.flashback.flashbackapi.api.models.drivers.model
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class NetworkDriverDataMapperTest {

    private lateinit var underTest: NetworkDriverDataMapper

    internal fun initUnderTest() {
        underTest = NetworkDriverDataMapper()
    }

    @Test
    fun `mapDriverData maps fields correctly`() {
        initUnderTest()

        val input = NetworkDriver.model()
        val expected = Driver.model()

        assertEquals(expected, underTest.mapDriverData(input))
    }

    @Test
    fun `mapDriverData invalid permanent number is set to null`() {
        initUnderTest()

        val input = NetworkDriver.model(permanentNumber = "invalid")

        assertNull(underTest.mapDriverData(input).number)
    }
}