package tmg.flashback.feature.constructors.presentation.stats

import app.cash.turbine.test
import dev.mokkery.MockMode.autofill
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import tmg.flashback.data.repo.repository.ConstructorRepository
import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.ConstructorHistory
import tmg.flashback.formula1.model.ConstructorHistorySeasonDriver
import tmg.flashback.formula1.model.Driver
import tmg.flashback.formula1.model.model
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class ConstructorStatsViewModelTest {

    private val mockConstructorRepository: ConstructorRepository = mock(autofill)

    private lateinit var underTest: ConstructorStatsViewModel

    private fun initUnderTest() {
        every { mockConstructorRepository.getConstructorOverview("constructorId") } returns flow {
            emit(ConstructorHistory.model())
        }
        underTest = ConstructorStatsViewModel(
            constructorRepository = mockConstructorRepository,
            coroutineContext = Dispatchers.Unconfined
        )
    }

    @Test
    fun `loading constructor outputs regular list`() = runTest {
        initUnderTest()

        underTest.uiState.test {
            val initialState = awaitItem()
            underTest.loadConstructor("constructorId", null)

            val state = awaitItem()
            assertEquals(Constructor.model(), state.constructor)
        }
    }

    @Test
    fun `loading constructor without season preselects overview`() = runTest {
        initUnderTest()

        underTest.uiState.test {
            val initialState = awaitItem()
            underTest.loadConstructor("constructorId", null)

            val state = awaitItem()
            assertEquals(Constructor.model(), state.constructor)
            assertEquals(8, state.stats.size)
            assertEquals(listOf(2020), state.availableSeasons)

            val data = listOf(ConstructorStatSeasonOverview(2020, mapOf(1 to Driver.model()), 1))
            assertEquals(data, (state.data as ConstructorStatsData.Overview).items)
        }
    }

    @Test
    fun `changing selection updates selection`() = runTest {
        initUnderTest()

        underTest.uiState.test {
            val initialState = awaitItem()
            underTest.loadConstructor("constructorId", null)

            val state = awaitItem()
            assertEquals(Constructor.model(), state.constructor)
            assertTrue(state.data is ConstructorStatsData.Overview)

            underTest.changeSelection(ConstructorFilter.Season(2020))

            val updated = awaitItem()
            assertEquals(Constructor.model(), updated.constructor)
            assertTrue(updated.data is ConstructorStatsData.Season)
        }
    }

    @Test
    fun `loading constructor with season preselects season`() = runTest {
        initUnderTest()

        underTest.uiState.test {
            val initialState = awaitItem()
            underTest.loadConstructor("constructorId", 2020)

            val state = awaitItem()
            assertEquals(Constructor.model(), state.constructor)
            assertEquals(7, state.stats.size)
            assertEquals(listOf(2020), state.availableSeasons)
            assertEquals(listOf(ConstructorHistorySeasonDriver.model()), (state.data as ConstructorStatsData.Season).drivers)
        }
    }

    @Test
    fun `refresh calls populate driver`() = runTest {
        initUnderTest()

        underTest.uiState.test {
            val initialState = awaitItem()
            underTest.loadConstructor("constructorId", 2020)

            verifySuspend {
                mockConstructorRepository.populateConstructor("constructorId")
            }

            val state = awaitItem()
            assertEquals(Constructor.model(), state.constructor)
            assertEquals(listOf(ConstructorHistorySeasonDriver.model()), (state.data as ConstructorStatsData.Season).drivers)

            underTest.refresh()

            verifySuspend {
                mockConstructorRepository.populateConstructor("constructorId")
            }
        }
    }
}