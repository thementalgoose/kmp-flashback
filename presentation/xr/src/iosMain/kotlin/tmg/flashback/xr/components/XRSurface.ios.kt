package tmg.flashback.xr.components

import androidx.compose.runtime.Composable

@Composable
actual fun XRSurface(
    content: @Composable (() -> Unit)
) {
    content.invoke()
}