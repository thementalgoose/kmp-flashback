package tmg.flashback.feature.reactiongame.repositories

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