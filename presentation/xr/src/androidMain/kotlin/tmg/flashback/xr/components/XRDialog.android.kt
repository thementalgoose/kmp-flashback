package tmg.flashback.xr.components

import androidx.compose.runtime.Composable
import androidx.xr.compose.spatial.SpatialDialog

@Composable
actual fun XRDialog(
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    SpatialDialog(
        onDismissRequest = onDismissRequest,
        content = content
    )
}