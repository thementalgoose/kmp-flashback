package tmg.flashback.xr

import androidx.compose.runtime.Composable
import androidx.xr.compose.platform.LocalSpatialCapabilities
import androidx.xr.runtime.Session
import androidx.xr.scenecore.scene

class AndroidXR(
    val localSession: Session?
): XR {
    override val isSpatialUiEnabled: Boolean
        @Composable get() = LocalSpatialCapabilities.current.isSpatialUiEnabled

    override fun requestPassthroughMode() {
        localSession?.scene?.requestHomeSpaceMode()
    }

    override fun requestImmersiveMode() {
        localSession?.scene?.requestFullSpaceMode()
    }
}