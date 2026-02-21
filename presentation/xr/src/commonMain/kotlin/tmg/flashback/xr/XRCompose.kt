package tmg.flashback.xr

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf

val LocalXR = compositionLocalOf<XR> { NoopXR() }

@Composable
expect fun xr(): XR

@Composable
fun noopXr(): XR = object : XR {
    override val isSpatialUiEnabled: Boolean @Composable get() = false
    override val isXrDevice: Boolean @Composable get() = false
    override fun requestPassthroughMode() { }
    override fun requestImmersiveMode() { }
}