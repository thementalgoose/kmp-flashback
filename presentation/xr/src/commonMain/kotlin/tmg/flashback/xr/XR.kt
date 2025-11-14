package tmg.flashback.xr

import androidx.compose.runtime.Composable

interface XR {
    val isSpatialUiEnabled: Boolean
        @Composable get

    fun goToHomeSpaceMode()

    fun goToFullSpaceMode()
}

class NoopXR: XR {
    override val isSpatialUiEnabled: Boolean
        @Composable get() = false
    override fun goToHomeSpaceMode() { }
    override fun goToFullSpaceMode() { }
}