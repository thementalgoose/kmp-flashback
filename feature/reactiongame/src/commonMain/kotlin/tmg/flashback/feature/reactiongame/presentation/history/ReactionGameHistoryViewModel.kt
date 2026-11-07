package tmg.flashback.feature.reactiongame.presentation.history

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.annotation.KoinViewModel
import tmg.flashback.feature.reactiongame.repositories.ReactionGameRepository
import tmg.flashback.feature.reactiongame.repositories.ReactionResultTier.Companion.toTier

@KoinViewModel
internal class ReactionGameHistoryViewModel(
    private val reactionGameRepository: ReactionGameRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<ReactionGameHistoryUiState> = MutableStateFlow(getList())
    val uiState: StateFlow<ReactionGameHistoryUiState> = _uiState

    fun refresh() {
        _uiState.update { getList() }
    }

    private fun getList(): ReactionGameHistoryUiState {
        val rawResults = reactionGameRepository.results
        val jumpStarts = rawResults.count { it.timeMillis <= 0 }
        val results = rawResults
            .filter { it.timeMillis > 0 }
            .groupBy { toTier(it.timeMillis.toLong()) }
            .mapValues { (tier, entries) ->
                entries
                    .groupBy { it.timeMillis }
                    .map { History(it.key, it.value.size) }
                    .sortedBy { it.timeMillis }
            }
            .toList()
            .sortedBy { it.first.ordinal }


        return ReactionGameHistoryUiState(
            results = results,
            jumpStarts = jumpStarts
        )
    }
}