package tmg.flashback.feature.weekend.presentation.weekend.components

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import tmg.flashback.formula1.enums.RaceStatus
import tmg.flashback.formula1.enums.isStatusFinished

fun Modifier.status(
    status: RaceStatus,
    background: Color
): Modifier {
    val backgroundColor = when (status.isStatusFinished()) {
        true -> Color.Transparent
        else -> background
    }
    val alpha = when (status.isStatusFinished()) {
        true -> 1f
        else -> 0.6f
    }
    return this
        .background(backgroundColor)
        .alpha(alpha)
}