package tmg.flashback.data.repo.mappers.network

import tmg.flashback.flashbackapi.NetworkCircuit
import tmg.flashback.persistence.flashback.models.circuit.Circuit
import tmg.flashback.persistence.flashback.models.circuit.model
import tmg.flashback.flashbackapi.api.models.circuits.model
import kotlin.test.Test
import kotlin.test.assertEquals

internal class NetworkCircuitDataMapperTest {

    private lateinit var underTest: NetworkCircuitDataMapper

    internal fun initUnderTest() {
        underTest = NetworkCircuitDataMapper()
    }

    @Test
    fun `mapCircuitData maps fields correctly`() {
        initUnderTest()

        val input = NetworkCircuit.model()
        val expected = Circuit.model()

        assertEquals(expected, underTest.mapCircuitData(input))
    }
}