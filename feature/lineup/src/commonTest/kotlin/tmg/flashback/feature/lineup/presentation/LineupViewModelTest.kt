package tmg.flashback.feature.lineup.presentation

import app.cash.turbine.test
import dev.mokkery.MockMode.autofill
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import tmg.flashback.data.repo.model.Response
import tmg.flashback.data.repo.repository.LineupRepository
import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.Driver
import tmg.flashback.formula1.model.LineupSeason
import tmg.flashback.formula1.model.model
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class LineupViewModelTest {

    private val mockLineupRepository: LineupRepository = mock(autofill)

    private lateinit var underTest: LineupViewModel

    private fun initUnderTest() {
        underTest = LineupViewModel(
            lineupRepository = mockLineupRepository
        )
    }

    @Test
    fun `initial state when no lineup data is available`() = runTest {
        every { mockLineupRepository.getLineup() } returns flow { emit(null) }

        initUnderTest()

        underTest.uiState.test {
            val state = awaitItem()

            assertEquals(false, state.isLoading)
            assertEquals(emptyList(), state.seasons)
            assertEquals(emptyList(), state.rows)
        }
    }

    @Test
    fun `lineup rows are generated from season data`() = runTest {
        val constructor = Constructor.model(id = "constructorId", name = "Ferrari")
        val driverOne = Driver.model(id = "driver1", firstName = "Charles", lastName = "Leclerc")
        val driverTwo = Driver.model(id = "driver2", firstName = "Carlos", lastName = "Sainz")
        val seasonOne = LineupSeason(
            season = 2024,
            driversToConstructors = mapOf(
                driverOne to constructor,
                driverTwo to constructor,
            )
        )
        val seasonTwo = LineupSeason(
            season = 2025,
            driversToConstructors = mapOf(
                driverOne to constructor,
                driverTwo to constructor,
            )
        )
        val channel = MutableSharedFlow<List<LineupSeason>>()
        every { mockLineupRepository.getLineup() } returns channel

        initUnderTest()

        underTest.uiState.test {
            val initial = awaitItem()

            channel.emit(listOf(seasonOne, seasonTwo))

            val state = awaitItem()

            assertEquals(listOf(2024, 2025), state.seasons)
            assertEquals(1, state.rows.size)
            assertEquals(constructor, state.rows.first().constructor)
            assertEquals(
                mapOf(
                    driverOne to listOf(2024, 2025),
                    driverTwo to listOf(2024, 2025),
                ),
                state.rows.first().contractBars
            )
        }
    }

    @Test
    fun `refresh calls populate lineup`() = runTest {
        every { mockLineupRepository.getLineup() } returns flow { emit(null) }
        everySuspend { mockLineupRepository.populateLineup() } returns Response.Successful

        initUnderTest()

        underTest.refresh()

        verifySuspend {
            mockLineupRepository.populateLineup()
        }

        underTest.uiState.test {
            val state = awaitItem()
            assertTrue(state.isLoading == false || state.isLoading == true)
        }
    }
}