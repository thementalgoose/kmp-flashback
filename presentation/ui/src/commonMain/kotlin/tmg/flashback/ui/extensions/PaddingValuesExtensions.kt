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