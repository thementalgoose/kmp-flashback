package tmg.flashback.feature.weekend.presentation

import app.cash.turbine.test
import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.calls
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify
import dev.mokkery.verifySuspend
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.report_issue_thanks
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import tmg.flashback.analytics.constants.AnalyticsConstants.analyticsRound
import tmg.flashback.analytics.constants.AnalyticsConstants.analyticsSeason
import tmg.flashback.analytics.manager.AnalyticsManager
import tmg.flashback.data.repo.model.Response
import tmg.flashback.data.repo.repository.OverviewRepository
import tmg.flashback.data.repo.repository.RaceRepository
import tmg.flashback.device.usecases.OpenLocationUseCase
import tmg.flashback.device.usecases.OpenWebpageUseCase
import tmg.flashback.feature.weekend.presentation.data.QualifyingSortType
import tmg.flashback.feature.weekend.presentation.data.ResultType
import tmg.flashback.feature.weekend.presentation.data.info.InfoDataMapper
import tmg.flashback.feature.weekend.presentation.data.info.InfoModel
import tmg.flashback.feature.weekend.presentation.data.info.model
import tmg.flashback.feature.weekend.presentation.data.qualifying.QualifyingDataMapper
import tmg.flashback.feature.weekend.presentation.data.race.RaceDataMapper
import tmg.flashback.feature.weekend.presentation.data.sprint_qualifying.SprintQualifyingDataMapper
import tmg.flashback.feature.weekend.presentation.data.sprint_race.SprintRaceDataMapper
import tmg.flashback.feature.weekend.usecases.GetPreviousRaceUseCase
import tmg.flashback.formula1.model.Location
import tmg.flashback.formula1.model.OverviewRace
import tmg.flashback.formula1.model.Race
import tmg.flashback.formula1.model.model
import tmg.flashback.ui.toasts.ToastManager
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

@OptIn(ExperimentalCoroutinesApi::class)
internal class WeekendViewModelTest {

    private lateinit var underTest: WeekendViewModel

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

    private val fakeRace = Race.model()
    private val fakeInfoModel = InfoModel.model()
    private val fakePreviousRace = OverviewRace.model(season = 2023, round = 1)

    private val testDispatcher = UnconfinedTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun TestScope.initUnderTest(subscribe: Boolean = true) {
        every { mockRacesRepository.getRace(any<Int>(), any<Int>()) } returns flowOf(fakeRace)
        everySuspend { mockGetPreviousRaceUseCase.invoke(any<Int>(), any<Int>()) } returns fakePreviousRace
        every { mockInfoDataMapper.invoke(any<Race>()) } returns fakeInfoModel
        every { mockRaceDataMapper.invoke(any<Race>(), any<ResultType>()) } returns emptyList()
        every { mockQualifyingDataMapper.invoke(any<Race>()) } returns emptyList()
        every { mockSprintQualifyingDataMapper.invoke(any<Race>()) } returns emptyList()
        every { mockSprintRaceDataMapper.invoke(any<Race>(), any<ResultType>()) } returns emptyList()
        everySuspend { mockRacesRepository.populateRaces(any<Int>()) } returns Response.Successful
        everySuspend { mockOverviewRepository.populateOverview(any<Int>()) } returns Response.Successful

        underTest = WeekendViewModel(
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
            toastManager = mockToastManager
        )

        if (subscribe) {
            backgroundScope.launch(testDispatcher) { underTest.uiState.collect {} }
        }
    }

    @Test
    fun `load populates uiState with race data`() = runTest(testDispatcher) {
        initUnderTest(subscribe = false)
        underTest.uiState.test {
            assertEquals(WeekendUiState.Initial, awaitItem())

            underTest.load(2024, 1)

            val state = awaitItem()
            assertIs<WeekendUiState.Data>(state)
            assertEquals(2020, state.season)
            assertEquals(fakeInfoModel, state.info)
            assertEquals(fakePreviousRace, state.previousRace)
            assertEquals(WeekendTabs.Qualifying, state.tab)
        }
    }

    @Test
    fun `sortQualifyingBy updates qualifyingSort in uiState`() = runTest(testDispatcher) {
        initUnderTest()
        underTest.load(2024, 1)
        advanceUntilIdle()

        val initialData = underTest.uiState.value
        assertIs<WeekendUiState.Data>(initialData)
        assertEquals(QualifyingSortType.Qualified, initialData.qualifyingSort)

        underTest.sortQualifyingBy(QualifyingSortType.Q1)
        advanceUntilIdle()

        val updated = underTest.uiState.value
        assertIs<WeekendUiState.Data>(updated)
        assertEquals(QualifyingSortType.Q1, updated.qualifyingSort)
    }

    @Test
    fun `updateTab updates selected tab in uiState`() = runTest(testDispatcher) {
        initUnderTest()
        underTest.load(2024, 1)
        advanceUntilIdle()

        val initialData = underTest.uiState.value
        assertIs<WeekendUiState.Data>(initialData)
        assertEquals(WeekendTabs.Qualifying, initialData.tab)

        underTest.updateTab(WeekendTabs.Race)
        advanceUntilIdle()

        val updated = underTest.uiState.value
        assertIs<WeekendUiState.Data>(updated)
        assertEquals(WeekendTabs.Race, updated.tab)
    }

    @Test
    fun `selectResultType updates resultType in uiState`() = runTest(testDispatcher) {
        initUnderTest()
        underTest.load(2024, 1)
        advanceUntilIdle()

        val initialData = underTest.uiState.value
        assertIs<WeekendUiState.Data>(initialData)
        assertEquals(ResultType.DRIVERS, initialData.resultType)

        underTest.selectResultType(ResultType.CONSTRUCTORS)
        advanceUntilIdle()

        val updated = underTest.uiState.value
        assertIs<WeekendUiState.Data>(updated)
        assertEquals(ResultType.CONSTRUCTORS, updated.resultType)
    }

    @Test
    fun `openLink invokes openWebpageUseCase`() = runTest(testDispatcher) {
        initUnderTest(subscribe = false)

        underTest.openLink("https://f1.com")

        verify {
            mockOpenWebpageUseCase.invoke("https://f1.com")
        }
    }

    @Test
    fun `openMap invokes openLocationUseCase`() = runTest(testDispatcher) {
        initUnderTest(subscribe = false)
        val location = Location(lat = 52.07, lng = -1.01)

        underTest.openMap(location, "Silverstone")

        verify {
            mockOpenLocationUseCase.invoke(lat = 52.07, lng = -1.01, name = "Silverstone")
        }
    }

    @Test
    fun `reportIssue logs analytics event and shows toast`() = runTest(testDispatcher) {
        initUnderTest()

        underTest.reportIssue(2024, 1)
        advanceUntilIdle()

        verifySuspend {
            mockAnalyticsManager.logEvent(
                "report_issue",
                mapOf(
                    analyticsSeason to "2024",
                    analyticsRound to "1"
                )
            )
            mockToastManager.showMessage(string.report_issue_thanks)
        }
    }

    @Test
    fun `refresh populates races and overview repository`() = runTest(testDispatcher) {
        initUnderTest()
        underTest.load(2024, 1)
        advanceUntilIdle()

        assertIs<WeekendUiState.Data>(underTest.uiState.value)

        underTest.refresh()
        advanceUntilIdle()

        verifySuspend {
            mockRacesRepository.populateRaces(2024)
            mockOverviewRepository.populateOverview(2024)
        }
    }
}
