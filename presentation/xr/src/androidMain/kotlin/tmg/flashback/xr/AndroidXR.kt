package tmg.flashback.xr

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.xr.compose.platform.LocalSession
import androidx.xr.compose.platform.LocalSpatialCapabilities
import androidx.xr.runtime.Session
import androidx.xr.scenecore.scene

class AndroidXR(
    val localSession: Session?
): XR {
    override val isSpatialUiEnabled: Boolean
        @Composable get() {
            return LocalSpatialCapabilities.current.isSpatialUiEnabled.also {
                Log.d("AppXR", "isSpatialUiEnabled $it")
            }
        }

    override val isXrDevice: Boolean
        @Composable get() = LocalSession.current != null

    override fun requestPassthroughMode() {
        localSession?.scene?.requestHomeSpaceMode()
    }

    override fun requestImmersiveMode() {
        localSession?.scene?.requestFullSpaceMode()
    }
}