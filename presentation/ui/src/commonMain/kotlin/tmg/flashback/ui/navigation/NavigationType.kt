package tmg.flashback.ui.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND

private val sideNavThreshold = 64.dp

enum class NavigationType(
    val enablePane: Boolean
) {
    NavDrawerBottomNav(enablePane = true),
    NavDrawerSideNav(enablePane = true),
    NavRail(enablePane = false),
    NavOrbiter(enablePane = false);
}

@Composable
fun rememberNavigationLayoutType(showXr: Boolean): NavigationType {
    if (showXr) {
        return NavigationType.NavOrbiter
    }
    val windowAdaptiveInfo = currentWindowAdaptiveInfoV2()
    val windowSizeClass = windowAdaptiveInfo.windowSizeClass
    val layoutDirection = LocalLayoutDirection.current
    val endInset = WindowInsets.safeDrawing.asPaddingValues().calculateEndPadding(layoutDirection)

    val hasProminentSideInset = endInset >= sideNavThreshold

    return when {
        hasProminentSideInset -> NavigationType.NavDrawerBottomNav
        windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND) -> NavigationType.NavRail
        else -> NavigationType.NavDrawerBottomNav
    }
}