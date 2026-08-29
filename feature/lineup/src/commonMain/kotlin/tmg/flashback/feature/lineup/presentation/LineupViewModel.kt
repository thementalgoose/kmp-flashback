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
import tmg.flashback.formula1.model.LineupSeason
import tmg.flashback.formula1.model.toOverview
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
        val firstSeason = list.map { it.season }.distinct().sorted().firstOrNull()
        return list
            .toOverview()
            .map {
                ConstructorLineup(
                    constructor = it.constructor,
                    contractBars = it.drivers
                        .map { (driver, seasons) ->
                            driver to findSequenceAndGapLengths(firstSeason, seasons)
                        }
                        .toMap()
                )
            }
    }

    internal fun findSequenceAndGapLengths(firstNumber: Int?, numbers: List<Int>): List<ConstructorBar> {
        if (numbers.isEmpty()) return emptyList()
        val result = mutableListOf<ConstructorBar>()
        var currentSeqLen = 1
        for (i in 0 until numbers.lastIndex) {
            val current = numbers[i]
            val next = numbers[i + 1]
            if (next == current + 1) {
                currentSeqLen++
            } else {
                result.add(ConstructorBar(signedup = true, seasons = currentSeqLen))
                result.add(ConstructorBar(signedup = false, seasons = next - current - 1))
                currentSeqLen = 1
            }
        }
        result.add(ConstructorBar(signedup = true, seasons = currentSeqLen))

        if (firstNumber != null && !numbers.contains(firstNumber)) {
            result.add(0, ConstructorBar(signedup = false, seasons = (numbers.first() - firstNumber).coerceIn(0, 10)))
        }
        return result
    }
}