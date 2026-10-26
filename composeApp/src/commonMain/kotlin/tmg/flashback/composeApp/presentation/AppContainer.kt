package tmg.flashback.composeApp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.window.core.layout.WindowSizeClass
import tmg.flashback.composeApp.presentation.navigation.AppNavigationViewModel
import tmg.flashback.composeApp.presentation.navigation.previewAppNavigationViewModel
import tmg.flashback.navigation.NavCalendar
import tmg.flashback.navigation.saveStateConfiguration
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewDevicePixel
import tmg.flashback.style.text.TextBody1

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppContainer(
    modifier: Modifier = Modifier,
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
            windowAdaptiveInfo = currentWindowAdaptiveInfoV2(),
            windowSizeClass = currentWindowAdaptiveInfoV2().windowSizeClass,
            backStack = rememberNavBackStack(saveStateConfiguration, NavCalendar),
            paddingValues = PaddingValues(0.dp),
            appNavigationViewModel = previewAppNavigationViewModel
        )
    }
}
