package tmg.flashback.ui.extensions

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun PaddingValues.add(
    top: Dp = 0.dp,
    start: Dp = 0.dp,
    bottom: Dp = 0.dp,
    end: Dp = 0.dp
): PaddingValues {
    val direction = LocalLayoutDirection.current
    return PaddingValues(
        top = this.calculateTopPadding() + top,
        bottom = this.calculateBottomPadding() + bottom,
        start = this.calculateStartPadding(direction) + start,
        end = this.calculateEndPadding(direction) + end
    )
}

@Composable
fun PaddingValues.only(
    horizontal: Boolean = false,
    vertical: Boolean = false
) = this.only(
    start = horizontal,
    end = horizontal,
    top = vertical,
    bottom = vertical
)

@Composable
fun PaddingValues.only(
    start: Boolean = false,
    end: Boolean = false,
    top: Boolean = false,
    bottom: Boolean = false
): PaddingValues {
    val layoutDirection = LocalLayoutDirection.current
    return PaddingValues(
        start = this.calculateStartPadding(layoutDirection).takeIf { start } ?: 0.dp,
        end = this.calculateEndPadding(layoutDirection).takeIf { end } ?: 0.dp,
        top = this.calculateTopPadding().takeIf { top } ?: 0.dp,
        bottom = this.calculateBottomPadding().takeIf { bottom } ?: 0.dp
    )
}