package tmg.flashback.feature.reactiongame.repositories

import kotlinx.datetime.LocalDateTime

data class ReactionGameEntry(
    val timeMillis: Int,
    val date: LocalDateTime
)