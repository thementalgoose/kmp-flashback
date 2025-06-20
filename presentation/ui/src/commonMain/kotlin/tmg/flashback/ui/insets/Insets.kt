package tmg.flashback.ui.insets

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.runtime.Composable

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