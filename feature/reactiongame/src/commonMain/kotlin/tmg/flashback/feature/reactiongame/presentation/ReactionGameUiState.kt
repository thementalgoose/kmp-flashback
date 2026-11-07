package tmg.flashback.feature.reactiongame.presentation

import tmg.flashback.feature.reactiongame.repositories.ReactionResultTier

sealed class ReactionUiState {

    data class Game(
        val lights: Int,
        val hasDisplayedSequence: Boolean = false
    ): ReactionUiState()

    data object Start: ReactionUiState()

    data object JumpStart: ReactionUiState()

    data object Missed: ReactionUiState()

    data class Results(
        val tier: ReactionResultTier,
        val timeMillis: Long,
        val percentage: Float
    ): ReactionUiState()
}