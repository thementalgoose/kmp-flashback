package tmg.flashback.xr.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
expect fun XROrbiter(
    position: Position = Position.Start,
    offset: Dp = 0.dp,
    elevation: Dp = 16.dp,
    content: @Composable () -> Unit,
)

enum class Position {
    Top, End, Start, Bottom
}