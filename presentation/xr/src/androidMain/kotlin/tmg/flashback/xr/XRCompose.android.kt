package tmg.flashback.xr

import androidx.compose.runtime.Composable
import androidx.xr.compose.platform.LocalSession

@Composable
actual fun xr(): XR = AndroidXR(
    LocalSession.current
)