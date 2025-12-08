package tmg.flashback.feature.drivers.presentation.stats

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
import tmg.flashback.data.repo.repository.DriverRepository
import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.Driver
import tmg.flashback.formula1.model.DriverHistory
import tmg.flashback.formula1.model.DriverHistorySeasonRace
import tmg.flashback.formula1.model.model
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class DriverStatsViewModelTest {

    private val mockDriverRepository: DriverRepository = mock(autofill)

    private lateinit var underTest: DriverStatsViewModel

    private fun initUnderTest() {
        every { mockDriverRepository.getDriverOverview("driverId") } returns flow {
            emit(DriverHistory.model())
        }
        underTest = DriverStatsViewModel(
            driverRepository = mockDriverRepository,
            coroutineContext = Dispatchers.Unconfined
        )
    }

    @Test
    fun `loading driver outputs driver list`() = runTest {
        initUnderTest()

        underTest.uiState.test {
            val initialState = awaitItem()
            underTest.loadDriver("driverId", null)

            val state = awaitItem()
            assertEquals(Driver.model(), state.driver)
        }
    }

    @Test
    fun `loading driver without season preselects overview`() = runTest {
        initUnderTest()

        underTest.uiState.test {
            val initialState = awaitItem()
            underTest.loadDriver("driverId", null)

            val state = awaitItem()
            assertEquals(Driver.model(), state.driver)
            assertEquals(12, state.stats.size)
            assertEquals(listOf(Constructor.model()), state.constructors)
            assertEquals(listOf(2020), state.availableSeasons)
            assertEquals(listOf(DriverStatTeamOverview(2020, listOf(Constructor.model()), null)), (state.data as DriverStatsData.Overview).teams)
        }
    }

    @Test
    fun `changing selection updates selection`() = runTest {
        initUnderTest()

        underTest.uiState.test {
            val initialState = awaitItem()
            underTest.loadDriver("driverId", null)

            val state = awaitItem()
            assertEquals(Driver.model(), state.driver)
            assertTrue(state.data is DriverStatsData.Overview)

            underTest.changeSelection(DriverFilter.Season(2020))

            val updated = awaitItem()
            assertEquals(Driver.model(), updated.driver)
            assertTrue(updated.data is DriverStatsData.Season)
        }
    }

    @Test
    fun `loading driver with season preselects season`() = runTest {
        initUnderTest()

        underTest.uiState.test {
            val initialState = awaitItem()
            underTest.loadDriver("driverId", 2020)

            val state = awaitItem()
            assertEquals(Driver.model(), state.driver)
            assertEquals(12, state.stats.size)
            assertEquals(listOf(Constructor.model()), state.constructors)
            assertEquals(listOf(2020), state.availableSeasons)
            assertEquals(listOf(DriverHistorySeasonRace.model()), (state.data as DriverStatsData.Season).races)
        }
    }

    @Test
    fun `refresh calls populate driver`() = runTest {
        initUnderTest()

        underTest.uiState.test {
            val initialState = awaitItem()
            underTest.loadDriver("driverId", 2020)

            verifySuspend {
                mockDriverRepository.populateDriver("driverId")
            }

            val state = awaitItem()
            assertEquals(Driver.model(), state.driver)
            assertEquals(listOf(DriverHistorySeasonRace.model()), (state.data as DriverStatsData.Season).races)

            underTest.refresh()

            verifySuspend {
                mockDriverRepository.populateDriver("driverId")
            }
        }
    }
}