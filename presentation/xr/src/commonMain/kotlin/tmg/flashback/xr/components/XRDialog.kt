package tmg.flashback.xr.components

import androidx.compose.runtime.Composable

@Composable
expect fun XRDialog(
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
)