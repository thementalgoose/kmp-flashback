package tmg.flashback.feature.reactiongame.presentation

import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.reaction_tier_average
import flashback.presentation.localisation.generated.resources.reaction_tier_average_desc
import flashback.presentation.localisation.generated.resources.reaction_tier_good
import flashback.presentation.localisation.generated.resources.reaction_tier_good_desc
import flashback.presentation.localisation.generated.resources.reaction_tier_great
import flashback.presentation.localisation.generated.resources.reaction_tier_great_desc
import flashback.presentation.localisation.generated.resources.reaction_tier_not_good
import flashback.presentation.localisation.generated.resources.reaction_tier_not_good_desc
import flashback.presentation.localisation.generated.resources.reaction_tier_poor
import flashback.presentation.localisation.generated.resources.reaction_tier_poor_desc
import flashback.presentation.localisation.generated.resources.reaction_tier_superhuman
import flashback.presentation.localisation.generated.resources.reaction_tier_superhuman_desc
import org.jetbrains.compose.resources.StringResource

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

enum class ReactionResultTier(
    val range: IntRange
) {
    SUPERHUMAN(0..<150),
    EXCEPTIONAL(150..<180),
    GOOD(180..<230),
    AVERAGE(230..<280),
    NOT_GOOD(280..<400),
    POOR(400..Int.MAX_VALUE);

    companion object {
        fun toTier(millis: Long): ReactionResultTier {
            if (millis < 0) return SUPERHUMAN
            return entries.firstOrNull { millis in it.range } ?: POOR
        }
    }
}

val ReactionResultTier.label: StringResource
    get() = when (this) {
        ReactionResultTier.SUPERHUMAN -> string.reaction_tier_superhuman
        ReactionResultTier.EXCEPTIONAL -> string.reaction_tier_great
        ReactionResultTier.GOOD -> string.reaction_tier_good
        ReactionResultTier.AVERAGE -> string.reaction_tier_average
        ReactionResultTier.NOT_GOOD -> string.reaction_tier_not_good
        ReactionResultTier.POOR -> string.reaction_tier_poor
    }

val ReactionResultTier.desc: StringResource
    get() = when (this) {
        ReactionResultTier.SUPERHUMAN -> string.reaction_tier_superhuman_desc
        ReactionResultTier.EXCEPTIONAL -> string.reaction_tier_great_desc
        ReactionResultTier.GOOD -> string.reaction_tier_good_desc
        ReactionResultTier.AVERAGE -> string.reaction_tier_average_desc
        ReactionResultTier.NOT_GOOD -> string.reaction_tier_not_good_desc
        ReactionResultTier.POOR -> string.reaction_tier_poor_desc
    }