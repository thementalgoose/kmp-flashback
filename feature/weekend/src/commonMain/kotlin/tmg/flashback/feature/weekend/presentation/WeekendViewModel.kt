package tmg.flashback.feature.weekend.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tmg.flashback.data.repo.repository.OverviewRepository
import tmg.flashback.data.repo.repository.RaceRepository
import tmg.flashback.device.usecases.OpenLocationUseCase
import tmg.flashback.device.usecases.OpenWebpageUseCase
import tmg.flashback.feature.weekend.presentation.data.ResultType
import tmg.flashback.feature.weekend.presentation.data.info.InfoDataMapper
import tmg.flashback.feature.weekend.presentation.data.qualifying.QualifyingDataMapper
import tmg.flashback.feature.weekend.presentation.data.race.RaceDataMapper
import tmg.flashback.feature.weekend.presentation.data.sprint_qualifying.SprintQualifyingDataMapper
import tmg.flashback.feature.weekend.presentation.data.sprint_race.SprintRaceDataMapper
import tmg.flashback.feature.weekend.utils.getWeekendEventOrder
import tmg.flashback.formula1.model.Location
import tmg.flashback.infrastructure.log.logDebug
import tmg.flashback.infrastructure.log.logInfo

class WeekendViewModel(
    private val racesRepository: RaceRepository,
    private val overviewRepository: OverviewRepository,
    private val infoDataMapper: InfoDataMapper,
    private val raceDataMapper: RaceDataMapper,
    private val qualifyingDataMapper: QualifyingDataMapper,
    private val sprintQualifyingDataMapper: SprintQualifyingDataMapper,
    private val sprintRaceDataMapper: SprintRaceDataMapper,
    private val openWebpageUseCase: OpenWebpageUseCase,
    private val openLocationUseCase: OpenLocationUseCase
): ViewModel() {

    private val seasonRound: MutableStateFlow<Pair<Int, Int>?> = MutableStateFlow(null)

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val resultType: MutableStateFlow<ResultType> = MutableStateFlow(ResultType.DRIVERS)

    private val tab: MutableStateFlow<WeekendTabs> = MutableStateFlow(WeekendTabs.Qualifying)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<WeekendUiState> =
        combine(
            seasonRound
                .filterNotNull()
                .flatMapLatest { (season, round) -> racesRepository.getRace(season, round) },
            resultType,
            tab
        ) { race, resultType, tab ->
            logDebug("WeekendVM", "Calculating WeekendUiState ${race?.raceInfo} - $resultType - $tab")
            if (race == null) {
                return@combine WeekendUiState.NotFound
            }
            _isLoading.update { false }
            return@combine WeekendUiState.Data(
                season = race.raceInfo.season,
                info = infoDataMapper(race),
                tab = tab,
                tabs = getWeekendEventOrder(
                    isSprint = race.hasSprint,
                    season = race.raceInfo.season
                ),
                resultType = resultType,
                qualifyingResults = qualifyingDataMapper(race),
                qualifyingColumns = race.qualifying.maxOfOrNull { it.label },
                raceResults = raceDataMapper(race, resultType),
                sprintQualifyingResults = sprintQualifyingDataMapper(race),
                sprintRaceResults = sprintRaceDataMapper(race, resultType),
            )
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, WeekendUiState.Initial)

    fun load(season: Int, round: Int) {
        this.seasonRound.update {
            season to round
        }
        this.tab.update { WeekendTabs.Qualifying }
        viewModelScope.launch {
            val data = racesRepository.getRace(season, round).firstOrNull()
            if (data?.race?.isEmpty() == true && data.qualifying.isEmpty() == true) {
                refresh(season)
            }
        }
    }

    fun updateTab(tab: WeekendTabs) {
        logDebug("Weeekend", "Selecting tab $tab")
        this.tab.update { tab }
    }

    fun openLink(link: String) {
        openWebpageUseCase(link)
    }

    fun openMap(location: Location, name: String) {
        openLocationUseCase(
            lat = location.lat,
            lng = location.lng,
            name = name,
        )
    }

    fun selectResultType(resultType: ResultType) {
        this.resultType.value = resultType
    }

    private suspend fun refresh(season: Int) {
        _isLoading.update { true }
        racesRepository.populateRaces(season)
        overviewRepository.populateOverview(season)
        _isLoading.update { false }
    }

    fun refresh() {
        viewModelScope.launch {
            val (season, _) = seasonRound.value ?: return@launch
            refresh(season)
        }
    }
}