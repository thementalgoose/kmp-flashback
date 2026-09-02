package tmg.flashback.xr.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
actual fun XROrbiter(
    position: Position,
    offset: Dp,
    elevation: Dp,
    content: @Composable () -> Unit,
) {
    content()
}
