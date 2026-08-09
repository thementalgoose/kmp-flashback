package tmg.flashback.ui.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import multiplatform.network.cmptoast.toPx

val Dp.px: Int
    @Composable
    get() {
        return this.toPx(LocalDensity.current)
    }