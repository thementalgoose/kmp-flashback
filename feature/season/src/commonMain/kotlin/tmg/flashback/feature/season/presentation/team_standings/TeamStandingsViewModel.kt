package tmg.flashback.feature.season.presentation.team_standings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import tmg.flashback.data.repo.repository.OverviewRepository
import tmg.flashback.data.repo.repository.RaceRepository
import tmg.flashback.data.repo.repository.SeasonRepository
import tmg.flashback.feature.season.presentation.shared.seasonpicker.CurrentSeasonHolder

class TeamStandingsViewModel(
    private val seasonRepository: SeasonRepository,
    private val overviewRepository: OverviewRepository,
    private val raceRepository: RaceRepository,
    private val currentSeasonHolder: CurrentSeasonHolder,
): ViewModel() {

    private val uiState: MutableStateFlow<TeamStandingsState> = MutableStateFlow(
        TeamStandingsState(season = currentSeasonHolder.currentSeason)
    )
    private val season: Int
        get() = uiState.value.season


    init {
        viewModelScope.launch {
            currentSeasonHolder.currentSeasonFlow.collectLatest {
                uiState.value = uiState.value.copy(season = it)
                if (!populate()) {
                    refresh()
                }
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            if (uiState.value.standings.isEmpty()) {
                populate()
            }
            uiState.value = uiState.value.copy(isLoading = true)
            overviewRepository.populateOverview(season)
            raceRepository.populateRaces(season)
            populate()
        }
    }

    private suspend fun populate(): Boolean {
        val currentStandings = seasonRepository.getConstructorStandings(season).firstOrNull()?.standings ?: emptyList()
        val maxPoints = currentStandings.maxOfOrNull { it.points } ?: 800.0
        uiState.value = uiState.value.copy(
            standings = currentStandings,
            maxPoints = maxPoints,
            inProgress = currentStandings.firstOrNull()?.inProgressContent,
            isLoading = false
        )

        return currentStandings.isNotEmpty()
    }

    companion object {
        internal const val SEASON = "season"
    }
}