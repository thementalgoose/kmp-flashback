package tmg.flashback.xr.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.xr.compose.spatial.ContentEdge
import androidx.xr.compose.spatial.Orbiter

@Composable
actual fun XROrbiter(
    position: Position,
    offset: Dp,
    elevation: Dp,
    content: @Composable () -> Unit,
) {
    when (position) {
        Position.Start, Position.End -> {
            Orbiter(
                position = if (position == Position.Start) ContentEdge.Start else ContentEdge.End,
                offset = offset,
                elevation = elevation,
                content = content,
            )
        }
        Position.Top, Position.Bottom -> {
            Orbiter(
                position = if (position == Position.Top) ContentEdge.Top else ContentEdge.Bottom,
                offset = offset,
                elevation = elevation,
                content = content,
            )
        }
    }
}