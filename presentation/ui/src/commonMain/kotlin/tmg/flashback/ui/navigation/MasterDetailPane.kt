package tmg.flashback.ui.navigation

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider
import tmg.flashback.style.AppTheme
import tmg.flashback.style.AppThemePreview
import tmg.flashback.style.text.TextBody1
import tmg.flashback.ui.components.fade.Fade

private val detailsMinWidth = 320.dp
private const val DETAILS_RATIO = 0.58f

@Immutable
class MasterDetailPaneState<T>(
    initialDestination: T? = null
) {
    var destination by mutableStateOf<T?>(initialDestination)
        private set

    fun navigateTo(destination: T) {
        this.destination = destination
    }

    fun clear() {
        this.destination = null
    }
}

@Composable
fun <T> rememberMasterDetailPaneState(
    initialDestination: T? = null
): MasterDetailPaneState<T> {
    return remember(initialDestination) {
        MasterDetailPaneState<T>(
            initialDestination = initialDestination
        )
    }
}

@Composable
fun <T> MasterDetailsPane(
    navigator: MasterDetailPaneState<T> = rememberMasterDetailPaneState<T>(),
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass,
    master: @Composable () -> Unit,
    details: @Composable (T, closeDetails: () -> Unit) -> Unit,
    detailsActionUpClicked: () -> Unit
) {
    BoxWithConstraints {
        val detailsWidth = when {
            maxWidth <= (detailsMinWidth * 2f) -> maxWidth * 0.5f
            maxWidth >= ((maxWidth * DETAILS_RATIO) + detailsMinWidth) -> (maxWidth * DETAILS_RATIO).coerceAtLeast(detailsMinWidth)
            else -> maxWidth - detailsMinWidth
        }
        val detailsOffset = animateDpAsState(targetValue = when (navigator.destination != null) {
            true -> maxWidth - detailsWidth
            false -> maxWidth
        }, label = "mdp_offset")

        Box(
            Modifier
                .width(if (windowSizeClass.windowWidthSizeClass != WindowWidthSizeClass.EXPANDED) maxWidth else detailsOffset.value)
                .fillMaxHeight()
        ) {
            master()
            if (windowSizeClass.windowWidthSizeClass != WindowWidthSizeClass.EXPANDED) {
                Fade(visible = navigator.destination != null) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(AppTheme.colors.surface)
                    )
                    if (navigator.destination != null) {
                        details(navigator.destination!!, detailsActionUpClicked)
//                        BackHandler(onBack = detailsActionUpClicked)
                    }
                }
            }
        }
        if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED) {
            Box(
                modifier = Modifier
                    .offset(detailsOffset.value)
                    .width(detailsWidth)
                    .fillMaxHeight()
                    .background(AppTheme.colors.surface),
                contentAlignment = Alignment.Center
            ) {
                Fade(visible = navigator.destination != null) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(AppTheme.colors.surface)
                    )
                    if (navigator.destination != null) {
                        details(navigator.destination!!, detailsActionUpClicked)

//                        BackHandler(onBack = detailsActionUpClicked)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewPhoneNoDetails(
    @PreviewParameter(BooleanParamProvider::class) isLight: Boolean
) {
    AppThemePreview(isLight) {
        Box(Modifier.size(DpSize(500.dp, 600.dp))) {
            MasterDetailsPane<Any>(
                windowSizeClass = WindowSizeClass.compute(500f, 600f),
                master = @Composable { PreviewMaster() },
                details = @Composable { item, closeAction -> PreviewDetails() },
                detailsActionUpClicked = { }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewPhoneMasterDetails(
    @PreviewParameter(BooleanParamProvider::class) isLight: Boolean
) {
    AppThemePreview(isLight) {
        Box(Modifier.size(DpSize(500.dp, 600.dp))) {
            MasterDetailsPane<Any>(
                windowSizeClass = WindowSizeClass.compute(500f, 600f),
                master = @Composable { PreviewMaster() },
                details = @Composable { item, closeAction -> PreviewDetails() },
                detailsActionUpClicked = { }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewTabletNoDetails(
    @PreviewParameter(BooleanParamProvider::class) isLight: Boolean
) {
    AppThemePreview(isLight) {
        Box(Modifier.size(DpSize(1000.dp, 400.dp))) {
            MasterDetailsPane<Any>(
                windowSizeClass = WindowSizeClass.compute(1000f, 400f),
                master = @Composable { PreviewMaster() },
                details = @Composable { item, closeAction -> PreviewDetails() },
                detailsActionUpClicked = { }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewTabletMasterDetails(
    @PreviewParameter(BooleanParamProvider::class) isLight: Boolean
) {
    AppThemePreview(isLight) {
        Box(Modifier.size(DpSize(1000.dp, 400.dp))) {
            MasterDetailsPane<Any>(
                windowSizeClass = WindowSizeClass.compute(1000f, 400f),
                master = @Composable { PreviewMaster() },
                details = @Composable { item, closeAction -> PreviewDetails() },
                detailsActionUpClicked = { }
            )
        }
    }
}

@Composable
private fun PreviewDetails() {
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.Cyan)
    ) {
        TextBody1(text = "Details")
    }
}

@Composable
private fun PreviewMaster() {
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.Green)
    ) {
        TextBody1(text = "Master")
    }
}

private class BooleanParamProvider: PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean> = sequenceOf(true, false)
}