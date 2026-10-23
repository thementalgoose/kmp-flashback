package tmg.flashback.feature.weekend.presentation

import app.cash.turbine.test
import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import tmg.flashback.analytics.manager.AnalyticsManager
import tmg.flashback.data.repo.repository.OverviewRepository
import tmg.flashback.data.repo.repository.RaceRepository
import tmg.flashback.device.usecases.OpenLocationUseCase
import tmg.flashback.device.usecases.OpenWebpageUseCase
import tmg.flashback.feature.weekend.presentation.data.info.InfoDataMapper
import tmg.flashback.feature.weekend.presentation.data.info.InfoModel
import tmg.flashback.feature.weekend.presentation.data.info.model
import tmg.flashback.feature.weekend.presentation.data.qualifying.QualifyingDataMapper
import tmg.flashback.feature.weekend.presentation.data.race.RaceDataMapper
import tmg.flashback.feature.weekend.presentation.data.sprint_qualifying.SprintQualifyingDataMapper
import tmg.flashback.feature.weekend.presentation.data.sprint_race.SprintRaceDataMapper
import tmg.flashback.feature.weekend.usecases.GetPreviousRaceUseCase
import tmg.flashback.formula1.model.OverviewRace
import tmg.flashback.formula1.model.Race
import tmg.flashback.formula1.model.RaceInfo
import tmg.flashback.formula1.model.SprintResult
import tmg.flashback.formula1.model.model
import tmg.flashback.ui.toasts.ToastManager
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class WeekendViewModelTest {

    private val mockRacesRepository: RaceRepository = mock(autoUnit)
    private val mockOverviewRepository: OverviewRepository = mock(autoUnit)
    private val mockInfoDataMapper: InfoDataMapper = mock(autoUnit)
    private val mockRaceDataMapper: RaceDataMapper = mock(autoUnit)
    private val mockQualifyingDataMapper: QualifyingDataMapper = mock(autoUnit)
    private val mockSprintQualifyingDataMapper: SprintQualifyingDataMapper = mock(autoUnit)
    private val mockSprintRaceDataMapper: SprintRaceDataMapper = mock(autoUnit)
    private val mockOpenWebpageUseCase: OpenWebpageUseCase = mock(autoUnit)
    private val mockOpenLocationUseCase: OpenLocationUseCase = mock(autoUnit)
    private val mockGetPreviousRaceUseCase: GetPreviousRaceUseCase = mock(autoUnit)
    private val mockAnalyticsManager: AnalyticsManager = mock(autoUnit)
    private val mockToastManager: ToastManager = mock(autoUnit)

    private fun kotlinx.coroutines.test.TestScope.createViewModel(): WeekendViewModel {
        return WeekendViewModel(
            racesRepository = mockRacesRepository,
            overviewRepository = mockOverviewRepository,
            infoDataMapper = mockInfoDataMapper,
            raceDataMapper = mockRaceDataMapper,
            qualifyingDataMapper = mockQualifyingDataMapper,
            sprintQualifyingDataMapper = mockSprintQualifyingDataMapper,
            sprintRaceDataMapper = mockSprintRaceDataMapper,
            openWebpageUseCase = mockOpenWebpageUseCase,
            openLocationUseCase = mockOpenLocationUseCase,
            getPreviousRaceUseCase = mockGetPreviousRaceUseCase,
            analyticsManager = mockAnalyticsManager,
            toastManager = mockToastManager,
            coroutineScope = backgroundScope
        )
    }

    private fun setupMocks(race: Race) {
        every { mockRacesRepository.getRace(any(), any()) } returns flowOf(race)
        every { mockInfoDataMapper(race) } returns InfoModel.model()
        every { mockQualifyingDataMapper(race) } returns emptyList()
        every { mockRaceDataMapper(race, any()) } returns emptyList()
        every { mockSprintQualifyingDataMapper(race) } returns emptyList()
        every { mockSprintRaceDataMapper(race, any()) } returns emptyList()
        everySuspend { mockGetPreviousRaceUseCase(any(), any()) } returns null
    }

    @Test
    fun `load with defaultTab Race sets tab to Race`() = runTest {
        val race = Race.model(
            raceInfo = RaceInfo.model(season = 2024, round = 1)
        )
        setupMocks(race)

        val viewModel = createViewModel()
        viewModel.load(2024, 1, defaultTab = "Race")

        viewModel.uiState.test {
            val state = awaitItem()
            val dataState = if (state is WeekendUiState.Data) state else awaitItem()
            assertTrue(dataState is WeekendUiState.Data)
            assertEquals(WeekendTabs.Race, dataState.tab)
        }
    }

    @Test
    fun `load with defaultTab Sprint sets tab to SprintRace`() = runTest {
        val race = Race.model(
            raceInfo = RaceInfo.model(season = 2024, round = 1)
        )
        setupMocks(race)

        val viewModel = createViewModel()
        viewModel.load(2024, 1, defaultTab = "Sprint")

        viewModel.uiState.test {
            val state = awaitItem()
            val dataState = if (state is WeekendUiState.Data) state else awaitItem()
            assertTrue(dataState is WeekendUiState.Data)
            assertEquals(WeekendTabs.SprintRace, dataState.tab)
        }
    }

    @Test
    fun `load with defaultTab Sprint Qualifying sets tab to SprintQualifying in 2024`() = runTest {
        val race = Race.model(
            raceInfo = RaceInfo.model(season = 2024, round = 1)
        )
        setupMocks(race)

        val viewModel = createViewModel()
        viewModel.load(2024, 1, defaultTab = "Sprint Qualifying")

        viewModel.uiState.test {
            val state = awaitItem()
            val dataState = if (state is WeekendUiState.Data) state else awaitItem()
            assertTrue(dataState is WeekendUiState.Data)
            assertEquals(WeekendTabs.SprintQualifying, dataState.tab)
        }
    }

    @Test
    fun `load with defaultTab null defaults to Qualifying`() = runTest {
        val race = Race.model(
            raceInfo = RaceInfo.model(season = 2024, round = 1)
        )
        setupMocks(race)

        val viewModel = createViewModel()
        viewModel.load(2024, 1, defaultTab = null)

        viewModel.uiState.test {
            val state = awaitItem()
            val dataState = if (state is WeekendUiState.Data) state else awaitItem()
            assertTrue(dataState is WeekendUiState.Data)
            assertEquals(WeekendTabs.Qualifying, dataState.tab)
        }
    }

    @Test
    fun `load with defaultTab Free Practice defaults to Qualifying`() = runTest {
        val race = Race.model(
            raceInfo = RaceInfo.model(season = 2024, round = 1)
        )
        setupMocks(race)

        val viewModel = createViewModel()
        viewModel.load(2024, 1, defaultTab = "FP1")

        viewModel.uiState.test {
            val state = awaitItem()
            val dataState = if (state is WeekendUiState.Data) state else awaitItem()
            assertTrue(dataState is WeekendUiState.Data)
            assertEquals(WeekendTabs.Qualifying, dataState.tab)
        }
    }

    @Test
    fun `load with defaultTab Sprint on non-sprint race falls back to Qualifying`() = runTest {
        val race = Race.model(
            raceInfo = RaceInfo.model(season = 2024, round = 1),
            sprint = SprintResult(emptyList(), emptyList())
        )
        setupMocks(race)

        val viewModel = createViewModel()
        viewModel.load(2024, 1, defaultTab = "Sprint")

        viewModel.uiState.test {
            val state = awaitItem()
            val dataState = if (state is WeekendUiState.Data) state else awaitItem()
            assertTrue(dataState is WeekendUiState.Data)
            assertEquals(WeekendTabs.Qualifying, dataState.tab)
        }
    }
}
