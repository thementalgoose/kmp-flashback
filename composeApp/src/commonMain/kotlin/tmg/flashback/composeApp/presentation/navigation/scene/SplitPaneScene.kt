package tmg.flashback.composeApp.presentation.navigation.scene

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.scene.Scene
import androidx.navigation3.scene.SceneStrategy
import androidx.navigation3.scene.SceneStrategyScope
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND
import tmg.flashback.composeApp.presentation.navigation.scene.SplitPaneScene.Companion.DETAIL_KEY
import tmg.flashback.composeApp.presentation.navigation.scene.SplitPaneScene.Companion.LIST_KEY
import tmg.flashback.style.AppTheme

private val spacingPadding = 12.dp

class SplitPaneScene<T : Any>(
    override val key: Any,
    override val previousEntries: List<NavEntry<T>>,
    val listEntry: NavEntry<T>,
    val detailEntry: NavEntry<T>?,
) : Scene<T> {
    override val entries: List<NavEntry<T>> = listOfNotNull(listEntry, detailEntry)
    override val content: @Composable (() -> Unit) = {
        val safeDrawingInsets = WindowInsets.safeDrawing.asPaddingValues()
        val paddingInsets = PaddingValues(
            start = 0.dp,
            end = 0.dp,
            top = safeDrawingInsets.calculateTopPadding(),
            bottom = safeDrawingInsets.calculateBottomPadding()
        )
        BoxWithConstraints(modifier = Modifier
            .fillMaxSize()
            .padding(paddingInsets)
            .padding(end = AppTheme.dimens.medium)
            .consumeWindowInsets(WindowInsets.safeDrawing),
        ) {
            val listWidth = animateDpAsState(
                targetValue = when {
                    detailEntry == null -> maxWidth
                    else -> (maxWidth / 2f) - spacingPadding
                },
                label = "listWidth"
            )
            val detailWidth = (maxWidth / 2f)

            Box(Modifier
                .width(listWidth.value)
                .fillMaxHeight()
            ) {
                Box(Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(AppTheme.dimens.radiusMedium))
                    .background(AppTheme.colors.surfaceContainer1)
                ) {
                    listEntry.Content()
                }
            }
            if (detailEntry != null) {
                Box(Modifier
                    .width(detailWidth)
                    .fillMaxHeight()
                    .offset(listWidth.value + spacingPadding)
                ) {
                    Box(Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(AppTheme.dimens.radiusMedium))
                        .background(AppTheme.colors.surfaceContainer1)
                    ) {
                        detailEntry.Content()
                    }
                }
            }
        }
    }

    companion object {
        internal const val LIST_KEY = "SplitPaneScene-List"
        internal const val DETAIL_KEY = "SplitPaneScene-Detail"

        fun listPane() = mapOf(LIST_KEY to true)
        fun detailPane() = mapOf(DETAIL_KEY to true)
    }
}

@Composable
fun <T : Any> rememberSplitPaneSceneStrategy(): ListDetailSceneStrategy<T> {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

    return remember(windowSizeClass) {
        ListDetailSceneStrategy(windowSizeClass)
    }
}

class ListDetailSceneStrategy<T : Any>(val windowSizeClass: WindowSizeClass) : SceneStrategy<T> {

    override fun SceneStrategyScope<T>.calculateScene(entries: List<NavEntry<T>>): Scene<T>? {

        if (!windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND)) {
            return null
        }

        val listEntry = entries
            .findLast { it.metadata.containsKey(LIST_KEY) } ?: return null
        val detailEntry = entries
            .lastOrNull()?.takeIf { it.metadata.containsKey(DETAIL_KEY) }
        return SplitPaneScene(
            key = listEntry.contentKey,
            previousEntries = entries.dropLast(1),
            listEntry = listEntry,
            detailEntry = detailEntry,
        )
    }
}