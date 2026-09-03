package tmg.flashback.ui.components.swiperefresh

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_EXPANDED_LOWER_BOUND

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun SwipeRefresh(
    isLoading: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    // Disable pull-to-refresh on expanded (or larger) window sizes
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val isExpandedOrLarger = windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_EXPANDED_LOWER_BOUND)

    if (isExpandedOrLarger) {
        // Render content without pull-to-refresh behavior
        Box(modifier = modifier) {
            this.content()
        }
        return
    }

    val pullRefreshState = rememberPullRefreshState(isLoading, onRefresh)
    Box(modifier = modifier.pullRefresh(pullRefreshState)) {
        this.content()

        PullRefreshIndicator(
            refreshing = isLoading,
            state = pullRefreshState,
            modifier = Modifier
                .statusBarsPadding()
                .align(Alignment.TopCenter)
        )
    }
}