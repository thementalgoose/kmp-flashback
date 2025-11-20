package tmg.flashback.xr

import androidx.compose.runtime.Composable
import androidx.xr.compose.platform.LocalSession
import androidx.xr.runtime.Session

@Composable
actual fun xr(): XR = AndroidXR(LocalSession.current)