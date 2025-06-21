package tmg.flashback.data.repo.mappers.app

import tmg.flashback.data.repo.fakes.fakeConstructorDataMapper
import tmg.flashback.data.repo.fakes.fakeDriverDataMapper
import tmg.flashback.persistence.flashback.models.circuit.model
import tmg.flashback.formula1.model.Circuit
import tmg.flashback.formula1.model.CircuitHistory
import tmg.flashback.formula1.model.model
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class CircuitMapperTest {

    private lateinit var underTest: CircuitMapper

    internal fun initUnderTest() {
        underTest = CircuitMapper(
            driverMapper = fakeDriverDataMapper(),
            constructorMapper = fakeConstructorDataMapper()
        )
    }

    @Test
    fun `mapCircuit maps fields correctly`() {
        initUnderTest()

        val input = tmg.flashback.persistence.flashback.models.circuit.Circuit.model()
        val expected = Circuit.model()

        assertEquals(expected, underTest.mapCircuit(input))
    }

    @Test
    fun `mapCircuit maps location to null if lat is null`() {
        initUnderTest()

        val input = tmg.flashback.persistence.flashback.models.circuit.Circuit.model(locationLat = null)

        assertNull(underTest.mapCircuit(input)!!.location)
    }

    @Test
    fun `mapCircuit maps location to null if lng is null`() {
        initUnderTest()

        val input = tmg.flashback.persistence.flashback.models.circuit.Circuit.model(locationLng = null)

        assertNull(underTest.mapCircuit(input)!!.location)
    }

    @Test
    fun `mapCircuitHistory maps fields correctly`() {
        initUnderTest()

        val input = tmg.flashback.persistence.flashback.models.circuit.CircuitHistory.model()
        val expected = CircuitHistory.model()

        assertEquals(expected, underTest.mapCircuitHistory(input))
    }

    @Test
    fun `mapCircuitHistory returns null when input is null`() {
        initUnderTest()

        assertNull(underTest.mapCircuitHistory(null))
    }
}