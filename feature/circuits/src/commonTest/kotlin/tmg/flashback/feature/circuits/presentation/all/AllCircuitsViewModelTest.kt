package tmg.flashback.feature.circuits.presentation.all

import app.cash.turbine.test
import dev.mokkery.MockMode.autofill
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import dev.mokkery.verifySuspend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import tmg.flashback.data.repo.repository.CircuitRepository
import tmg.flashback.formula1.model.Circuit
import tmg.flashback.formula1.model.model
import kotlin.test.Test
import kotlin.test.assertEquals

internal class AllCircuitsViewModelTest {

    private lateinit var underTest: AllCircuitsViewModel

    private val mockCircuitRepository: CircuitRepository = mock(autofill)

    private val fakeCircuit1 = Circuit.model(id = "1", name = "abc")
    private val fakeCircuit2 = Circuit.model(id = "2", name = "bcd")
    private val fakeCircuitOverview1 = CircuitOverview(
        circuit = fakeCircuit1,
        track = null
    )
    private val fakeCircuitOverview2 = CircuitOverview(
        circuit = fakeCircuit2,
        track = null
    )

    private fun initUnderTest() {
        every { mockCircuitRepository.getCircuits() } returns flow {
            emit(listOf(fakeCircuit1, fakeCircuit2))
        }
        underTest = AllCircuitsViewModel(
            circuitRepository = mockCircuitRepository,
            coroutineContext = Dispatchers.Unconfined
        )
    }

    @Test
    fun `all circuits returned by default`() = runTest {
        initUnderTest()

        underTest.uiState.test {
            assertEquals(AllCircuitsUiState(), awaitItem())
            testScheduler.advanceUntilIdle()

            val item = awaitItem()
            assertEquals(null, item.searchQuery)
            assertEquals(true, item.isLoading)
            assertEquals(listOf(fakeCircuitOverview1, fakeCircuitOverview2), item.circuits)
        }
    }

    @Test
    fun `search term updates circuits list, clear clears list`() = runTest {
        initUnderTest()

        underTest.uiState.test {
            assertEquals(AllCircuitsUiState(), awaitItem())

            val item = awaitItem()
            testScheduler.advanceUntilIdle()
            assertEquals(null, item.searchQuery)
            assertEquals(true, item.isLoading)
            assertEquals(listOf(fakeCircuitOverview1, fakeCircuitOverview2), item.circuits)

            underTest.enterSearchTerm("a")
            testScheduler.advanceUntilIdle()
            assertEquals(listOf(fakeCircuitOverview1), awaitItem().circuits)

            underTest.enterSearchTerm("b")
            testScheduler.advanceUntilIdle()
            assertEquals(listOf(fakeCircuitOverview1, fakeCircuitOverview2), awaitItem().circuits)

            underTest.enterSearchTerm("bcd")
            testScheduler.advanceUntilIdle()
            assertEquals(listOf(fakeCircuitOverview2), awaitItem().circuits)

            underTest.clearSearch()
            testScheduler.advanceUntilIdle()
            assertEquals(listOf(fakeCircuitOverview1, fakeCircuitOverview2), awaitItem().circuits)
        }
    }

    @Test
    fun `refresh calls populate`() {
        initUnderTest()

        underTest.refresh()

        verifySuspend {
            mockCircuitRepository.populateCircuits()
        }
    }
}