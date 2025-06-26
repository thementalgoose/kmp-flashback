package tmg.flashback.feature.season.presentation.team_standings

import app.cash.turbine.test
import dev.mokkery.MockMode
import dev.mokkery.MockMode.autoUnit
import dev.mokkery.MockMode.original
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify.VerifyMode.Companion.exactly
import dev.mokkery.verifySuspend
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
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

internal class CalendarScreenViewModelTest {

    private val mockSeasonRepository: SeasonRepository = mock(autoUnit)
    private val mockCurrentSeasonHolder: CurrentSeasonHolder = mock(autoUnit)
    private val mockOverviewRepository: OverviewRepository = mock(original)
    private val mockRaceRepository: RaceRepository = mock(original)

    private lateinit var underTest: TeamStandingsViewModel

    private fun initUnderTest() {
        every { mockSeasonRepository.getConstructorStandings(2020) } returns flow { emit(
            SeasonConstructorStandings.model(standings = listOf(standing1))
        ) }
        every { mockSeasonRepository.getConstructorStandings(2021) } returns flow { emit(
            SeasonConstructorStandings.model(standings = listOf(standing2))
        ) }
        everySuspend { mockOverviewRepository.populateOverview(any<Int>()) } returns Response.Successful
        everySuspend { mockRaceRepository.populateRaces(any<Int>()) } returns Response.Successful
        every { mockCurrentSeasonHolder.currentSeason } returns 2020
        every { mockCurrentSeasonHolder.currentSeasonFlow } returns fakeCurrentSeasonFlow
        underTest = TeamStandingsViewModel(
            seasonRepository = mockSeasonRepository,
            currentSeasonHolder = mockCurrentSeasonHolder,
            overviewRepository = mockOverviewRepository,
            raceRepository = mockRaceRepository
        )
    }

    private val standing1 = SeasonConstructorStandingSeason.model()
    private val standing2 = SeasonConstructorStandingSeason.model(constructor = Constructor.model(id = "2"))
    private val fakeCurrentSeasonFlow: MutableStateFlow<Int> = MutableStateFlow(2020)

    @Test
    fun `current season holder emits new season calls populate and refresh`() = runTest {
        initUnderTest()
        underTest.uiState.test {
            val initial = awaitItem()
            assertEquals(2020, initial.season)
            assertEquals(emptyList(), initial.standings)
            val initialWithResults = awaitItem()
            assertEquals(listOf(standing1), initialWithResults.standings)

            fakeCurrentSeasonFlow.emit(2021)

            val item = awaitItem()
            assertEquals(2021, item.season)
            val itemWithResults = awaitItem()
            assertEquals(listOf(standing2), itemWithResults.standings)
        }
    }

    @Test
    fun `initial load sets default season`() = runTest {
        initUnderTest()
        underTest.uiState.test {
            val initial = awaitItem()
            assertEquals(2020, initial.season)
        }
    }

    @Test
    fun `refresh calls fetch`() = runTest {
        initUnderTest()
        underTest.uiState.test {
            val initial = awaitItem()
            assertEquals(2020, initial.season)
            val initialWithResults = awaitItem()
            assertEquals(listOf(standing1), initialWithResults.standings)

            every { mockSeasonRepository.getConstructorStandings(2020) } returns flow { emit(
                SeasonConstructorStandings.model(
                    standings = listOf(standing1, standing2)
                )
            ) }

            underTest.refresh()
            verifySuspend {
                mockRaceRepository.populateRaces(2020)
                mockOverviewRepository.populateOverview(2020)
            }
            val loading = awaitItem()
            assertEquals(true, loading.isLoading)

            val withNewResults = awaitItem()
            assertEquals(listOf(standing1, standing2), withNewResults.standings)
        }
    }

    @Test
    fun `initial load populates data from repo`() = runTest {
        initUnderTest()
        underTest.uiState.test {
            val initial = awaitItem()
            assertEquals(2020, initial.season)
            assertEquals(emptyList(), initial.standings)
            val initialWithResults = awaitItem()
            assertEquals(listOf(standing1), initialWithResults.standings)
        }
        verifySuspend(exactly(0)) {
            mockRaceRepository.populateRaces(any<Int>())
            mockOverviewRepository.populateOverview(any<Int>())
        }
    }
}