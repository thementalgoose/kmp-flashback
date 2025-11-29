package tmg.flashback.xr

import androidx.compose.runtime.Composable

interface XR {
    val isSpatialUiEnabled: Boolean
        @Composable get

    val isXrDevice: Boolean
        @Composable get

    fun requestPassthroughMode()

    fun requestImmersiveMode()
}

class NoopXR: XR {
    override val isSpatialUiEnabled: Boolean
        @Composable get() = false
    override val isXrDevice: Boolean
        @Composable get() = false
    override fun requestPassthroughMode() { }
    override fun requestImmersiveMode() { }
}