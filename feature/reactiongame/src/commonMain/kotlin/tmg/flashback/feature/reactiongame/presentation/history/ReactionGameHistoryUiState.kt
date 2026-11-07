package tmg.flashback.feature.reactiongame.presentation.history

import tmg.flashback.feature.reactiongame.repositories.ReactionResultTier

internal data class ReactionGameHistoryUiState(
    val results: List<Pair<ReactionResultTier, List<History>>>,
    val jumpStarts: Int
)

internal data class History(
    val timeMillis: Int,
    val count: Int,
)