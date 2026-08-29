package tmg.flashback.feature.lineup.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import tmg.flashback.data.repo.repository.LineupRepository

class LineupViewModel(
    private val lineupRepository: LineupRepository
): ViewModel() {

    val uiState: StateFlow<LineupUiState> = lineupRepository
        .getLineup()
        .map {
            LineupUiState(2020, emptyList())
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, LineupUiState(-1, emptyList()))

    fun refresh() {
        viewModelScope.launch {
            lineupRepository.populateLineup()
        }
    }
}