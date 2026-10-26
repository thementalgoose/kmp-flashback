package tmg.flashback.ui.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND
import tmg.flashback.infrastructure.log.logInfo
import tmg.flashback.style.AppTheme

private val sideNavThreshold = 72.dp

enum class NavigationType {
    BottomNav,
    SideNav,
    NavRail,
    NavOrbiter;
}

@Composable
fun rememberNavigationLayoutType(showXr: Boolean): NavigationType {
    if (showXr) {
        return NavigationType.NavOrbiter
    }
    val windowAdaptiveInfo = currentWindowAdaptiveInfoV2()
    val windowSizeClass = windowAdaptiveInfo.windowSizeClass
    val layoutDirection = LocalLayoutDirection.current
    val insets = WindowInsets.safeDrawing.asPaddingValues()

    val hasProminentSideInset = insets.calculateEndPadding(layoutDirection) >= sideNavThreshold

    return when {
        // hasProminentSideInset -> NavigationType.BottomNav
        windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND) -> NavigationType.NavRail
        else -> NavigationType.BottomNav
    }
}