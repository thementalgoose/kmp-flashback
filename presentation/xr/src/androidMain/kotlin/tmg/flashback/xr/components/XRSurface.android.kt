package tmg.flashback.xr.components

import androidx.compose.runtime.Composable
import androidx.xr.compose.spatial.Subspace
import androidx.xr.compose.subspace.SpatialPanel
import androidx.xr.compose.subspace.layout.SubspaceModifier

@Composable
actual fun XRSurface(
    content: @Composable () -> Unit
) {
    Subspace {
        SpatialPanel(
            modifier = SubspaceModifier,
            content = content,
        )
    }
}