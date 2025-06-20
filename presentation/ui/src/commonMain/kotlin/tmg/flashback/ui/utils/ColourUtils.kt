package tmg.flashback.ui.utils

import androidx.compose.ui.graphics.Color

fun contrastTextLight(
    color: Int,
    threshold: Int = 180,
    redMultiplier: Float = 0.299f,
    greenMultiplier: Float = 0.587f,
    blueMultiplier: Float = 0.114f
): Boolean {
    val score = (Color(color.toLong()).red * redMultiplier) +
            (Color(color.toLong()).green * greenMultiplier) +
            (Color(color.toLong()).blue * blueMultiplier)
    return score < threshold
}

fun contrastTextDark(
    color: Int,
    threshold: Int = 180,
    redMultiplier: Float = 0.299f,
    greenMultiplier: Float = 0.587f,
    blueMultiplier: Float = 0.114f
): Boolean {
    return !contrastTextLight(color, threshold, redMultiplier, greenMultiplier, blueMultiplier)
}