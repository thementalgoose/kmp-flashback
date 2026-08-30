package tmg.flashback.feature.lineup.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import tmg.flashback.data.repo.repository.LineupRepository
import tmg.flashback.formula1.model.LineupOverview
import tmg.flashback.formula1.model.LineupSeason
import kotlin.collections.emptyList
import kotlin.collections.map
import kotlin.to

class LineupViewModel(
    private val lineupRepository: LineupRepository
): ViewModel() {

    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false);

    val uiState: StateFlow<LineupUiState> =
        combine(
            isLoading,
            lineupRepository.getLineup()
        ) { isLoading, lineup ->
            val seasons = lineup?.distinctBy { it.season }?.map { it.season } ?: emptyList()
            LineupUiState(
                isLoading = isLoading,
                seasons = seasons,
                rows = generateRows(lineup)
            )
        }.stateIn(viewModelScope, SharingStarted.Lazily, LineupUiState())

    fun refresh() {
        viewModelScope.launch {
            isLoading.value = true
            lineupRepository.populateLineup()
            isLoading.value = false
        }
    }

    private fun generateRows(list: List<LineupSeason>?): List<ConstructorLineup> {
        if (list == null) {
            return emptyList()
        }
        return LineupOverview
            .from(list)
            .map {
                ConstructorLineup(
                    constructor = it.constructor,
                    contractBars = it.drivers
                )
            }
    }
}