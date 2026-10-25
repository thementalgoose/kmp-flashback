package tmg.flashback.composeApp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.movableContentWithReceiverOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND
import kotlinx.coroutines.launch
import tmg.flashback.composeApp.presentation.navigation.AppNavigationDrawer
import tmg.flashback.composeApp.presentation.navigation.AppNavigationOrbiter
import tmg.flashback.composeApp.presentation.navigation.AppNavigationRail
import tmg.flashback.composeApp.presentation.navigation.AppNavigationViewModel
import tmg.flashback.composeApp.presentation.navigation.previewAppNavigationViewModel
import tmg.flashback.eastereggs.presentation.snow
import tmg.flashback.eastereggs.presentation.summer
import tmg.flashback.infrastructure.log.logDebug
import tmg.flashback.navigation.NavCalendar
import tmg.flashback.navigation.saveStateConfiguration
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewDeviceFoldable
import tmg.flashback.style.preview.PreviewDevicePixel
import tmg.flashback.style.preview.PreviewDeviceTablet
import tmg.flashback.style.preview.PreviewDevices
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.style.text.TextBody1
import tmg.flashback.ui.extensions.only
import tmg.flashback.ui.navigation.NavigationType
import tmg.flashback.ui.navigation.OverlappingPanels
import tmg.flashback.ui.navigation.OverlappingPanelsState
import tmg.flashback.ui.navigation.OverlappingPanelsValue
import tmg.flashback.ui.navigation.rememberOverlappingPanelsState
import tmg.flashback.xr.LocalXR

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppContainer(
    modifier: Modifier = Modifier,
    openPanel: () -> Unit,
    windowAdaptiveInfo: WindowAdaptiveInfo,
    windowSizeClass: WindowSizeClass,
    backStack: NavBackStack<NavKey>,
    paddingValues: PaddingValues,
    appNavigationViewModel: AppNavigationViewModel,
) {
    Box(
        modifier = modifier
    ) {
        if (LocalInspectionMode.current) {
            Box(Modifier
                .fillMaxSize()
                .background(AppTheme.colors.surfaceContainer5)
                .padding(16.dp)
                .background(AppTheme.colors.surfaceContainer4)
            ) {
                TextBody1(
                    text = "App Content",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            AppGraph(
                openPanel = {
                    openPanel()
                },
                backStack = backStack,
                appNavigationViewModel = appNavigationViewModel,
                insetPadding = paddingValues,
                windowAdaptiveInfo = windowAdaptiveInfo,
            )
        }
    }
}

@PreviewDevicePixel
@Composable
private fun Preview() {
    ApplicationThemePreview {
        AppContainer(
            openPanel = { },
            windowAdaptiveInfo = currentWindowAdaptiveInfoV2(),
            windowSizeClass = currentWindowAdaptiveInfoV2().windowSizeClass,
            backStack = rememberNavBackStack(saveStateConfiguration, NavCalendar),
            paddingValues = PaddingValues(0.dp),
            appNavigationViewModel = previewAppNavigationViewModel
        )
    }
}
