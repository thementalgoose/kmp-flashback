package tmg.flashback.data.repo.mappers.network

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tmg.flashback.flashbackapi.NetworkDriver
import tmg.flashback.persistence.flashback.models.drivers.Driver
import tmg.flashback.persistence.flashback.models.drivers.model
import tmg.flashback.flashbackapi.api.models.drivers.model

internal class NetworkDriverDataMapperTest {

    private lateinit var sut: NetworkDriverDataMapper

    @BeforeEach
    internal fun setUp() {
        sut = NetworkDriverDataMapper()
    }

    @Test
    fun `mapDriverData maps fields correctly`() {
        val input = NetworkDriver.model()
        val expected = Driver.model()

        assertEquals(expected, sut.mapDriverData(input))
    }

    @Test
    fun `mapDriverData invalid permanent number is set to null`() {
        val input = NetworkDriver.model(permanentNumber = "invalid")

        assertNull(sut.mapDriverData(input).number)
    }
}