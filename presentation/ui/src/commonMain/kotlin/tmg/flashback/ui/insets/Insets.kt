package tmg.flashback.ui.insets

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND

val WindowInsets.Companion.safeDrawingHorizontalOnly: WindowInsets
    @Composable
    get() = WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal)

@Composable
fun PaddingValues.asInsets(): WindowInsets {
    val layoutDirection = LocalLayoutDirection.current
    return WindowInsets(
        left = this.calculateLeftPadding(layoutDirection),
        right = this.calculateRightPadding(layoutDirection),
        top = this.calculateTopPadding(),
        bottom = this.calculateBottomPadding()
    )
}

@Composable
fun PaddingValues.compactOnly(windowSizeClass: WindowSizeClass): PaddingValues {
    return if (windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND)) {
        PaddingValues(0.dp)
    } else {
        this
    }
}