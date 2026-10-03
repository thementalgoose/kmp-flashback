package tmg.flashback.feature.drivers.presentation.comparison

import app.cash.turbine.test
import dev.mokkery.MockMode.autofill
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import tmg.flashback.data.repo.repository.RaceRepository
import tmg.flashback.data.repo.repository.SeasonRepository
import tmg.flashback.data.repo.repository.StandingsRepository
import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.Driver
import tmg.flashback.formula1.model.DriverEntry
import tmg.flashback.formula1.model.Race
import tmg.flashback.formula1.model.RaceResult
import tmg.flashback.formula1.model.Season
import tmg.flashback.formula1.model.SprintResult
import tmg.flashback.formula1.model.SeasonDriverStandingSeason
import tmg.flashback.formula1.model.SeasonDriverStandings
import tmg.flashback.formula1.model.model
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class DriverComparisonViewModelTest {

    private val mockSeasonRepository: SeasonRepository = mock(autofill)
    private val mockRacesRepository: RaceRepository = mock(autofill)
    private val mockStandingsRepository: StandingsRepository = mock(autofill)

    private lateinit var underTest: DriverComparisonViewModel

    private val driverLeft = Driver.model(id = "driver1", firstName = "Lewis", lastName = "Hamilton")
    private val driverRight = Driver.model(id = "driver2", firstName = "Max", lastName = "Verstappen")
    private val constructorLeft = Constructor.model(id = "constructor1", name = "Mercedes")
    private val constructorRight = Constructor.model(id = "constructor2", name = "Red Bull")

    private val seasonWithDrivers = Season.model(
        season = 2020,
        races = listOf(
            Race.model(
                qualifying = emptyList(),
                sprint = SprintResult.model(qualifying = emptyList(), race = emptyList()),
                race = listOf(
                    RaceResult.model(
                        driver = DriverEntry.model(driver = driverLeft, constructor = constructorLeft),
                        finish = 1,
                        grid = 1,
                        qualified = 1,
                        points = 25.0
                    ),
                    RaceResult.model(
                        driver = DriverEntry.model(driver = driverRight, constructor = constructorRight),
                        finish = 2,
                        grid = 2,
                        qualified = 2,
                        points = 18.0
                    )
                )
            )
        )
    )

    private val driverStandings = SeasonDriverStandings.model(
        standings = listOf(
            SeasonDriverStandingSeason.model(driver = driverLeft, points = 25.0),
            SeasonDriverStandingSeason.model(driver = driverRight, points = 18.0)
        )
    )

    private fun initUnderTest() {
        every { mockSeasonRepository.getSeason(2020) } returns flow {
            emit(seasonWithDrivers)
        }
        every { mockStandingsRepository.getDriverStandings(2020) } returns flow {
            emit(driverStandings)
        }
        underTest = DriverComparisonViewModel(
            seasonRepository = mockSeasonRepository,
            standingsRepository = mockStandingsRepository,
            racesRepository = mockRacesRepository,
            coroutineContext = Dispatchers.Unconfined
        )
    }

    @Test
    fun `initial state is loading with empty driver list`() = runTest {
        initUnderTest()

        underTest.uiState.test {
            val state = awaitItem()
            assertTrue(state.isLoading)
            assertTrue(state.driverList.isEmpty())
            assertNull(state.driverLeft)
            assertNull(state.driverRight)
            assertNull(state.comparison)
        }
    }

    @Test
    fun `setup loads driver list from season`() = runTest {
        initUnderTest()

        underTest.uiState.test {
            awaitItem() // initial state
            underTest.setup(2020)

            val state = awaitItem()
            assertEquals(2, state.driverList.size)
            assertTrue(state.driverList.any { it.id == "driver1" })
            assertTrue(state.driverList.any { it.id == "driver2" })
        }
    }

    @Test
    fun `selecting left driver updates state`() = runTest {
        initUnderTest()

        underTest.uiState.test {
            awaitItem() // initial state
            underTest.setup(2020)
            awaitItem() // loaded state

            underTest.selectDriverLeft("driver1")

            val state = awaitItem()
            assertEquals(driverLeft.id, state.driverLeft?.id)
            assertNull(state.driverRight)
            assertNull(state.comparison)
        }
    }

    @Test
    fun `selecting right driver updates state`() = runTest {
        initUnderTest()

        underTest.uiState.test {
            awaitItem() // initial state
            underTest.setup(2020)
            awaitItem() // loaded state

            underTest.selectDriverRight("driver2")

            val state = awaitItem()
            assertNull(state.driverLeft)
            assertEquals(driverRight.id, state.driverRight?.id)
            assertNull(state.comparison)
        }
    }

    @Test
    fun `selecting both drivers generates comparison`() = runTest {
        initUnderTest()

        underTest.uiState.test {
            awaitItem() // initial state
            underTest.setup(2020)
            awaitItem() // loaded state

            underTest.selectDriverLeft("driver1")
            awaitItem()

            underTest.selectDriverRight("driver2")

            val state = awaitItem()
            assertEquals(driverLeft.id, state.driverLeft?.id)
            assertEquals(driverRight.id, state.driverRight?.id)
            val comparison = state.comparison
            assertNotNull(comparison)
            assertEquals(1, comparison.left.racesHeadToHead)
            assertEquals(0, comparison.right.racesHeadToHead)
        }
    }

    @Test
    fun `cannot select same driver for both sides - left same as right`() = runTest {
        initUnderTest()

        underTest.uiState.test {
            awaitItem() // initial state
            underTest.setup(2020)
            awaitItem() // loaded state

            underTest.selectDriverRight("driver1")
            val stateAfterRightSelect = awaitItem()
            assertEquals(driverLeft.id, stateAfterRightSelect.driverRight?.id)

            underTest.selectDriverLeft("driver1")
            // No new state emitted because the selection was rejected
            // Verify the state hasn't changed
            assertEquals(driverLeft.id, underTest.uiState.value.driverRight?.id)
            assertNull(underTest.uiState.value.driverLeft)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `cannot select same driver for both sides - right same as left`() = runTest {
        initUnderTest()

        underTest.uiState.test {
            awaitItem() // initial state
            underTest.setup(2020)
            awaitItem() // loaded state

            underTest.selectDriverLeft("driver1")
            val stateAfterLeftSelect = awaitItem()
            assertEquals(driverLeft.id, stateAfterLeftSelect.driverLeft?.id)

            underTest.selectDriverRight("driver1")
            // No new state emitted because the selection was rejected
            // Verify the state hasn't changed
            assertEquals(driverLeft.id, underTest.uiState.value.driverLeft?.id)
            assertNull(underTest.uiState.value.driverRight)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `swapping drivers swaps left and right`() = runTest {
        initUnderTest()

        underTest.uiState.test {
            awaitItem() // initial state
            underTest.setup(2020)
            awaitItem() // loaded state

            underTest.selectDriverLeft("driver1")
            awaitItem()

            underTest.selectDriverRight("driver2")
            awaitItem()

            underTest.swapDrivers()

            val state = awaitItem()
            assertEquals(driverRight.id, state.driverLeft?.id)
            assertEquals(driverLeft.id, state.driverRight?.id)
        }
    }

    @Test
    fun `refresh calls populate on repositories`() = runTest {
        initUnderTest()

        underTest.uiState.test {
            awaitItem() // initial state
            underTest.setup(2020)

            verifySuspend {
                mockRacesRepository.populateRaces(2020)
            }
            verifySuspend {
                mockStandingsRepository.populateStandings(2020)
            }

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `selecting null driver clears selection`() = runTest {
        initUnderTest()

        underTest.uiState.test {
            awaitItem() // initial state
            underTest.setup(2020)
            awaitItem() // loaded state

            underTest.selectDriverLeft("driver1")
            awaitItem()

            underTest.selectDriverLeft(null)

            val state = awaitItem()
            assertNull(state.driverLeft)
        }
    }
}