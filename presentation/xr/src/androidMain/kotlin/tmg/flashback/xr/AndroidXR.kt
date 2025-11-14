package tmg.flashback.xr

import androidx.compose.runtime.Composable
import androidx.xr.compose.platform.LocalSpatialCapabilities
import androidx.xr.runtime.Session

class AndroidXR(
    val localSession: Session?
): XR {
    override val isSpatialUiEnabled: Boolean
        @Composable get() = LocalSpatialCapabilities.current.isSpatialUiEnabled

    override fun goToHomeSpaceMode() {

    }

    override fun goToFullSpaceMode() {

    }
}