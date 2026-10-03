package tmg.flashback.data.repo.mappers.network

import tmg.flashback.persistence.flashback.models.circuit.CircuitRound
import tmg.flashback.persistence.flashback.models.circuit.CircuitRoundResult
import tmg.flashback.persistence.flashback.models.circuit.model
import tmg.flashback.flashbackapi.api.models.circuits.CircuitResult
import tmg.flashback.flashbackapi.api.models.circuits.model
import kotlin.test.Test
import kotlin.test.assertEquals

internal class NetworkCircuitMapperTest {

    private lateinit var underTest: NetworkCircuitMapper

    internal fun initUnderTest() {
        underTest = NetworkCircuitMapper()
    }

    @Test
    fun `map circuit rounds maps fields correctly`() {
        initUnderTest()

        val input = CircuitResult.model()
        val expected = CircuitRound.model()

        assertEquals(expected, underTest.mapCircuitRounds(expected.circuitId, input))
    }

    @Test
    fun `map circuit previews maps fields correctly`() {
        initUnderTest()

        val input = CircuitResult.model()
        val expected = listOf(CircuitRoundResult.model())

        assertEquals(expected, underTest.mapCircuitPreviews(input))
    }

    @Test
    fun `map circuit previews returns empty list when mapping null`() {
        initUnderTest()

        assertEquals(emptyList<CircuitRoundResult>(), underTest.mapCircuitPreviews(CircuitResult.model(preview = null)))
    }
}