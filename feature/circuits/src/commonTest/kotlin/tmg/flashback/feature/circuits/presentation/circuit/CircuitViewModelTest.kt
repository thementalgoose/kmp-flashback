package tmg.flashback.feature.circuits.presentation.circuit

import app.cash.turbine.test
import dev.mokkery.MockMode
import dev.mokkery.MockMode.autofill
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify
import dev.mokkery.verifySuspend
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import tmg.flashback.data.repo.repository.CircuitRepository
import tmg.flashback.device.usecases.OpenLocationUseCase
import tmg.flashback.device.usecases.OpenWebpageUseCase
import tmg.flashback.formula1.model.Circuit
import tmg.flashback.formula1.model.CircuitHistory
import tmg.flashback.formula1.model.CircuitHistoryRace
import tmg.flashback.formula1.model.CircuitHistoryRaceResult
import tmg.flashback.formula1.model.Location
import tmg.flashback.formula1.model.model
import kotlin.test.Test
import kotlin.test.assertEquals

internal class CircuitViewModelTest {

    private lateinit var underTest: CircuitViewModel

    private val mockCircuitRepository: CircuitRepository = mock(autofill)
    private val mockOpenWebpageUseCase: OpenWebpageUseCase = mock(autofill)
    private val mockOpenLocationUseCase: OpenLocationUseCase = mock(autofill)

    private fun initUnderTest() {
        underTest = CircuitViewModel(
            circuitRepository = mockCircuitRepository,
            openWebpageUseCase = mockOpenWebpageUseCase,
            openLocationUseCase = mockOpenLocationUseCase
        )
    }

    @Test
    fun `loading circuit loads data if available`() = runTest {
        every { mockCircuitRepository.getCircuitHistory(any()) } returns flow {
            emit(CircuitHistory.model())
        }

        initUnderTest()

        underTest.load("circuitId")
        underTest.uiState.test {
            val initial = awaitItem()

            val item = awaitItem()
            assertEquals(Circuit.model(), item.circuit)
            assertEquals(listOf(CircuitEvent(
                race = CircuitHistoryRace.model(),
                first = CircuitHistoryRaceResult.model(),
                second = null,
                third = null
            )), item.races)

            // Populate request
            assertEquals(true, awaitItem().isLoading)
            assertEquals(false, awaitItem().isLoading)
            verifySuspend {
                mockCircuitRepository.populateCircuit("circuitId")
            }
        }
    }

    @Test
    fun `open link opens webpage`() {
        initUnderTest()
        underTest.openMap(Location.model(), "TRACK")
        verify {
            mockOpenLocationUseCase.invoke(Location.model().lat, Location.model().lng, "TRACK")
        }
    }

    @Test
    fun `open maps opens location`() {
        initUnderTest()
        underTest.openLink("test")
        verify {
            mockOpenWebpageUseCase.invoke("test")
        }
    }
}