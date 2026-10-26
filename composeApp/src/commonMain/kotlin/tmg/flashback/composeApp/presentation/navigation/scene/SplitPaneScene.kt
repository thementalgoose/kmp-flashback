package tmg.flashback.composeApp.presentation.navigation.scene

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
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
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_EXPANDED_LOWER_BOUND
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND
import tmg.flashback.composeApp.presentation.navigation.scene.SplitPaneScene.Companion.DETAIL_KEY
import tmg.flashback.composeApp.presentation.navigation.scene.SplitPaneScene.Companion.LIST_KEY
import tmg.flashback.style.AppTheme
import tmg.flashback.ui.navigation.NavigationType

val LocalConsumedInsets = compositionLocalOf<WindowInsets> {
    WindowInsets(0, 0, 0, 0)
}

private val spacingPadding = AppTheme.dimens.xsmall

class SplitPaneScene<T : Any>(
    override val key: Any,
    override val previousEntries: List<NavEntry<T>>,
    val listEntry: NavEntry<T>,
    val detailEntry: NavEntry<T>?,
    val navigationType: NavigationType,
) : Scene<T> {
    override val entries: List<NavEntry<T>> = listOfNotNull(listEntry, detailEntry)
    override val content: @Composable (() -> Unit) = {
        BoxWithConstraints(modifier = Modifier
            .fillMaxSize()
        ) {
            val listWidth = animateDpAsState(
                targetValue = when {
                    detailEntry == null -> maxWidth
                    else -> (maxWidth / 2f) - spacingPadding
                },
                label = "listWidth"
            )
            val detailWidth = (maxWidth / 2f)

            val listConsumed = when {
                detailEntry != null -> {
                    if (navigationType.isSides) {
                        WindowInsets.safeDrawing.only(WindowInsetsSides.End)
                    } else {
                        WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal)
                    }
                }
                else -> {
                    if (navigationType.isSides) {
                        WindowInsets(0, 0, 0, 0)
                    } else {
                        WindowInsets.safeDrawing.only(WindowInsetsSides.Start)
                    }
                }
            }

            Box(Modifier
                .width(listWidth.value)
                .fillMaxHeight()
            ) {
                Box(Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(AppTheme.dimens.radiusMedium))
                    .consumeWindowInsets(listConsumed)
                ) {
                    CompositionLocalProvider(LocalConsumedInsets provides listConsumed) {
                        listEntry.Content()
                    }
                }
            }
            if (detailEntry != null) {
                val detailConsumed = WindowInsets.safeDrawing.only(WindowInsetsSides.Start)
                Box(Modifier
                    .width(detailWidth)
                    .fillMaxHeight()
                    .offset(listWidth.value + spacingPadding)
                ) {
                    Box(Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(AppTheme.dimens.radiusMedium))
                        .consumeWindowInsets(detailConsumed)
                    ) {
                        CompositionLocalProvider(LocalConsumedInsets provides detailConsumed) {
                            detailEntry.Content()
                        }
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
fun <T : Any> rememberSplitPaneSceneStrategy(navigationType: NavigationType): ListDetailSceneStrategy<T> {
    val windowSizeClass = currentWindowAdaptiveInfoV2().windowSizeClass

    return remember(windowSizeClass, navigationType) {
        ListDetailSceneStrategy(windowSizeClass, navigationType)
    }
}

class ListDetailSceneStrategy<T : Any>(
    val windowSizeClass: WindowSizeClass,
    val navigationType: NavigationType,
) : SceneStrategy<T> {

    override fun SceneStrategyScope<T>.calculateScene(entries: List<NavEntry<T>>): Scene<T>? {

        if (!windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_EXPANDED_LOWER_BOUND)) {
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
            navigationType = navigationType,
        )
    }
}