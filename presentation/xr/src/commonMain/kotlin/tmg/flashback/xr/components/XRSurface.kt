package tmg.flashback.xr.components

import androidx.compose.runtime.Composable

@Composable
expect fun XRSurface(
    content: @Composable () -> Unit
)