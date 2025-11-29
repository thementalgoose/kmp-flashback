package tmg.flashback.feature.season.presentation.driver_standings

import app.cash.turbine.test
import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import tmg.flashback.data.repo.model.Response
import tmg.flashback.data.repo.repository.OverviewRepository
import tmg.flashback.data.repo.repository.RaceRepository
import tmg.flashback.data.repo.repository.SeasonRepository
import tmg.flashback.data.repo.repository.StandingsRepository
import tmg.flashback.feature.season.presentation.shared.seasonpicker.CurrentSeasonHolder
import tmg.flashback.formula1.model.Driver
import tmg.flashback.formula1.model.SeasonDriverStandingSeason
import tmg.flashback.formula1.model.SeasonDriverStandings
import tmg.flashback.formula1.model.model
import kotlin.test.Test
import kotlin.test.assertEquals

internal class DriverStandingsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val mockStandingsRepository: StandingsRepository = mock(autoUnit)
    private val mockCurrentSeasonHolder: CurrentSeasonHolder = mock(autoUnit)
    private val mockSeasonRepository: SeasonRepository = mock(autoUnit)
    private val standing1 = SeasonDriverStandingSeason.model()
    private val standing2 = SeasonDriverStandingSeason.model(driver = Driver.model(id = "2"))

    private val _fakeCurrentSeasonFlow: MutableStateFlow<Int> = MutableStateFlow(2020)

    private lateinit var underTest: DriverStandingsViewModel

    private fun initUnderTest() = runBlocking {
        every { mockStandingsRepository.getDriverStandings(2020) } returns flow { emit(SeasonDriverStandings.model(
            standings = listOf(standing1)
        )) }
        every { mockStandingsRepository.getDriverStandings(2021) } returns flow { emit(SeasonDriverStandings.model(
            standings = listOf(standing2)
        )) }
        every { mockCurrentSeasonHolder.currentSeason } returns 2019
        every { mockCurrentSeasonHolder.currentSeasonFlow } returns _fakeCurrentSeasonFlow
        everySuspend { mockSeasonRepository.populateSeason(any()) } returns Response.Successful
        underTest = DriverStandingsViewModel(
            standingsRepository = mockStandingsRepository,
            currentSeasonHolder = mockCurrentSeasonHolder,
            seasonRepository = mockSeasonRepository,
            mainDispatcher = testDispatcher
        )
    }

    @Test
    fun `initialise loads season into state`() = runTest {
        initUnderTest()
        underTest.uiState.test {
            val initial = awaitItem()
            assertEquals(2019, initial.season)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `initialise standings populates current season`() = runTest {
        initUnderTest()
        underTest.uiState.test {
            val initial = awaitItem()
            assertEquals(2019, initial.season)

            testDispatcher.scheduler.advanceUntilIdle()

            val initialObservable = awaitItem()
            assertEquals(2020, initialObservable.season)

            val afterPopulate = awaitItem()
            assertEquals(listOf(standing1), afterPopulate.standings)
            assertEquals(1.0, afterPopulate.maxPoints)
            assertEquals(false, afterPopulate.isLoading)
        }
    }

    @Test
    fun `current season update causes re-load`() = runTest {
        initUnderTest()
        underTest.uiState.test {
            val initial = awaitItem()
            assertEquals(2019, initial.season)

            testDispatcher.scheduler.advanceUntilIdle()

            val initialObservable = awaitItem()
            assertEquals(2020, initialObservable.season)

            val afterPopulate = awaitItem()
            assertEquals(listOf(standing1), afterPopulate.standings)

            _fakeCurrentSeasonFlow.emit(2021)

            testDispatcher.scheduler.advanceUntilIdle()

            val updatesInitial = awaitItem()
            assertEquals(2021, updatesInitial.season)

            val afterUpdate = awaitItem()
            assertEquals(listOf(standing2), afterUpdate.standings)
        }
    }

    @Test
    fun `refresh kicks off re-populate calls`() = runTest {
        initUnderTest()
        underTest.uiState.test {
            val initial = awaitItem()
            assertEquals(2019, initial.season)

            testDispatcher.scheduler.advanceUntilIdle()

            val initialObservable = awaitItem()
            assertEquals(2020, initialObservable.season)

            val afterPopulate = awaitItem()
            assertEquals(listOf(standing1), afterPopulate.standings)

            every { mockStandingsRepository.getDriverStandings(2020) } returns flow { emit(SeasonDriverStandings.model(
                standings = listOf(standing2)
            )) }

            underTest.refresh()

            testDispatcher.scheduler.advanceUntilIdle()

            val afterRefresh = awaitItem()
            assertEquals(true, afterRefresh.isLoading)

            val afterRefreshPopulate = awaitItem()
            assertEquals(listOf(standing2), afterRefreshPopulate.standings)

            verifySuspend {
                mockSeasonRepository.populateSeason(2020)
            }
        }
    }
}