package tmg.flashback.feature.season.presentation.team_standings

import app.cash.turbine.test
import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import tmg.flashback.data.repo.model.Response
import tmg.flashback.data.repo.repository.OverviewRepository
import tmg.flashback.data.repo.repository.RaceRepository
import tmg.flashback.data.repo.repository.SeasonRepository
import tmg.flashback.feature.season.presentation.shared.seasonpicker.CurrentSeasonHolder
import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.SeasonConstructorStandingSeason
import tmg.flashback.formula1.model.SeasonConstructorStandings
import tmg.flashback.formula1.model.model
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TeamStandingsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val mockSeasonRepository: SeasonRepository = mock(autoUnit)
    private val mockCurrentSeasonHolder: CurrentSeasonHolder = mock(autoUnit)
    private val mockOverviewRepository: OverviewRepository = mock(autoUnit)
    private val mockRaceRepository: RaceRepository = mock(autoUnit)

    private val standing1 = SeasonConstructorStandingSeason.model()
    private val standing2 = SeasonConstructorStandingSeason.model(constructor = Constructor.model(id = "2"))

    private val _fakeCurrentSeasonFlow: MutableStateFlow<Int> = MutableStateFlow(2020)

    private lateinit var underTest: TeamStandingsViewModel

    private fun initUnderTest() = runBlocking {
        every { mockSeasonRepository.getConstructorStandings(2020) } returns flow { emit(
            SeasonConstructorStandings.model(
            standings = listOf(standing1)
        )) }
        every { mockSeasonRepository.getConstructorStandings(2021) } returns flow { emit(
            SeasonConstructorStandings.model(
            standings = listOf(standing2)
        )) }
        every { mockCurrentSeasonHolder.currentSeason } returns 2019
        every { mockCurrentSeasonHolder.currentSeasonFlow } returns _fakeCurrentSeasonFlow
        everySuspend { mockOverviewRepository.populateOverview(any()) } returns Response.Successful
        everySuspend { mockRaceRepository.populateRaces(any()) } returns Response.Successful
        underTest = TeamStandingsViewModel(
            seasonRepository = mockSeasonRepository,
            currentSeasonHolder = mockCurrentSeasonHolder,
            overviewRepository = mockOverviewRepository,
            raceRepository = mockRaceRepository,
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

            every { mockSeasonRepository.getConstructorStandings(2020) } returns flow { emit(
                SeasonConstructorStandings.model(
                standings = listOf(standing2)
            )) }

            underTest.refresh()

            testDispatcher.scheduler.advanceUntilIdle()

            val afterRefresh = awaitItem()
            assertEquals(true, afterRefresh.isLoading)

            val afterRefreshPopulate = awaitItem()
            assertEquals(listOf(standing2), afterRefreshPopulate.standings)

            verifySuspend {
                mockOverviewRepository.populateOverview(2020)
                mockRaceRepository.populateRaces(2020)
            }
        }
    }
}