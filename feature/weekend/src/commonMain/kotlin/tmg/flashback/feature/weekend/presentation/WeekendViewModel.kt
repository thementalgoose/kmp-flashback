package tmg.flashback.feature.weekend.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tmg.flashback.data.repo.repository.OverviewRepository
import tmg.flashback.data.repo.repository.RaceRepository
import tmg.flashback.data.repo.repository.SeasonRepository
import tmg.flashback.feature.weekend.presentation.data.ResultType
import tmg.flashback.feature.weekend.presentation.data.info.InfoDataMapper
import tmg.flashback.feature.weekend.presentation.data.info.InfoModel
import tmg.flashback.feature.weekend.presentation.data.qualifying.QualifyingDataMapper
import tmg.flashback.feature.weekend.presentation.data.race.RaceDataMapper
import tmg.flashback.feature.weekend.presentation.data.sprint_qualifying.SprintQualifyingDataMapper
import tmg.flashback.feature.weekend.presentation.data.sprint_race.SprintRaceDataMapper

class WeekendViewModel(
    private val racesRepository: RaceRepository,
    private val overviewRepository: OverviewRepository,
    private val infoDataMapper: InfoDataMapper,
    private val raceDataMapper: RaceDataMapper,
    private val qualifyingDataMapper: QualifyingDataMapper,
    private val sprintQualifyingDataMapper: SprintQualifyingDataMapper,
    private val sprintRaceDataMapper: SprintRaceDataMapper
): ViewModel() {

    private val seasonRound: MutableStateFlow<Pair<Int, Int>?> = MutableStateFlow(null)

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val resultType: MutableStateFlow<ResultType> = MutableStateFlow(ResultType.DRIVERS)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<WeekendUiState> = seasonRound
        .filterNotNull()
        .flatMapLatest { (season, round) -> racesRepository.getRace(season, round) }
        .combine(resultType) { race, resultType ->
            if (race == null) {
                return@combine WeekendUiState.NotFound
            }
            _isLoading.update { false }
            return@combine WeekendUiState.Data(
                season = race.raceInfo.season,
                info = infoDataMapper(race),
                qualifyingResults = qualifyingDataMapper(race),
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
    }

    fun refresh() {
        viewModelScope.launch {
            _isLoading.update { true }
            val (season, _) = seasonRound.value ?: return@launch
            racesRepository.populateRaces(season)
            overviewRepository.populateOverview(season)
            _isLoading.update { false }
        }
    }
}