package tmg.flashback.feature.season.presentation.team_standings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import tmg.flashback.data.repo.repository.OverviewRepository
import tmg.flashback.data.repo.repository.RaceRepository
import tmg.flashback.data.repo.repository.SeasonRepository
import tmg.flashback.data.repo.repository.StandingsRepository
import tmg.flashback.feature.season.presentation.shared.seasonpicker.CurrentSeasonHolder
import org.koin.core.annotation.KoinViewModel
import tmg.flashback.feature.season.presentation.driver_standings.DriverStandingsStateInProgress

@KoinViewModel
class TeamStandingsViewModel(
    private val standingsRepository: StandingsRepository,
    private val overviewRepository: OverviewRepository,
    private val raceRepository: RaceRepository,
    private val currentSeasonHolder: CurrentSeasonHolder,
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
): ViewModel() {

    private val _uiState: MutableStateFlow<TeamStandingsState> = MutableStateFlow(
        TeamStandingsState(season = currentSeasonHolder.currentSeason)
    )
    val uiState: StateFlow<TeamStandingsState> = _uiState

    private val season: Int
        get() = _uiState.value.season


    init {
        viewModelScope.launch(mainDispatcher) {
            currentSeasonHolder.currentSeasonFlow.collectLatest {
                _uiState.value = _uiState.value.copy(season = it)
                if (!populate()) {
                    refresh()
                }
            }
        }
    }

    fun refresh() {
        viewModelScope.launch(mainDispatcher) {
            if (_uiState.value.standings.isEmpty()) {
                populate()
            }
            _uiState.value = _uiState.value.copy(isLoading = true)
            overviewRepository.populateOverview(season)
            raceRepository.populateRaces(season)
            standingsRepository.populateStandings(season)
            populate()
        }
    }

    private suspend fun populate(): Boolean {
        val currentStandings = standingsRepository.getConstructorStandings(season).firstOrNull()?.standings ?: emptyList()
        val maxPoints = currentStandings.maxOfOrNull { it.points } ?: 800.0

        val raceName = currentStandings.firstOrNull()?.inProgressName
        val round = currentStandings.firstOrNull()?.inProgressRound
        val inProgressContent = if (currentStandings.any { it.inProgress } && raceName != null && round != null) {
            TeamStandingsStateInProgress(
                raceName = raceName,
                round = round,
                championSecured = currentStandings.count { it.inProgressMaxPosition == 1 } == 1
            )
        } else {
            null
        }

        _uiState.value = _uiState.value.copy(
            standings = currentStandings,
            maxPoints = maxPoints,
            inProgress = inProgressContent,
            isLoading = false
        )

        return currentStandings.isNotEmpty()
    }

    companion object {
        internal const val SEASON = "season"
    }
}