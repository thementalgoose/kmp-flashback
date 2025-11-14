package tmg.flashback.xr

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf

val LocalXRSession = compositionLocalOf<XR> { NoopXR() }

@Composable
expect fun xr(): XR