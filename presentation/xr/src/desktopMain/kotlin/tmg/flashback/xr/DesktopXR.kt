package tmg.flashback.xr

import androidx.compose.runtime.Composable

object DesktopXR: XR {

    override val isSpatialUiEnabled: Boolean
        @Composable get() = false

    override val isXrDevice: Boolean
        @Composable get() = false

    override fun requestPassthroughMode() {
        /* no op */
    }

    override fun requestImmersiveMode() {
        /* no op */
    }
}