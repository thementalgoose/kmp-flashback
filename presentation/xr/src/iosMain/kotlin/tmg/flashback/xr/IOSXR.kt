package tmg.flashback.xr

import androidx.compose.runtime.Composable

object IOSXR: XR {
    override val isSpatialUiEnabled: Boolean
        @Composable get() = false

    override fun goToHomeSpaceMode() { /* no op */ }

    override fun goToFullSpaceMode() { /* no op */ }
}