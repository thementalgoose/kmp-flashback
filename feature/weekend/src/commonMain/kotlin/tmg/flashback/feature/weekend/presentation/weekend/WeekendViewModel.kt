package tmg.flashback.feature.weekend.presentation.weekend

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

class WeekendViewModel(
    private val racesRepository: RaceRepository,
    private val overviewRepository: OverviewRepository,
    private val seasonRepository: SeasonRepository
): ViewModel() {

    private val season: MutableStateFlow<Int?> = MutableStateFlow(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<WeekendUiState> = season
        .filterNotNull()
        .flatMapLatest { seasonRepository.getSeason(it) }
        .map { season ->
            return@map WeekendUiState.Initial
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, WeekendUiState.Initial)

    fun loadSeason(season: Int) {
        this.season.update { season }
    }

    fun refresh() {
        viewModelScope.launch {
            val season = season.value ?: return@launch
            racesRepository.populateRaces(season)
            overviewRepository.populateOverview(season)
        }
    }
}