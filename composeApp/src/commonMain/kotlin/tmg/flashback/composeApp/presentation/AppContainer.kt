package tmg.flashback.composeApp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.movableContentOf
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
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND
import kotlinx.coroutines.launch
import tmg.flashback.composeApp.presentation.navigation.AppNavigationDrawer
import tmg.flashback.composeApp.presentation.navigation.AppNavigationOrbiter
import tmg.flashback.composeApp.presentation.navigation.AppNavigationRail
import tmg.flashback.composeApp.presentation.navigation.AppNavigationViewModel
import tmg.flashback.eastereggs.presentation.snow
import tmg.flashback.eastereggs.presentation.summer
import tmg.flashback.infrastructure.log.logDebug
import tmg.flashback.style.AppTheme
import tmg.flashback.style.text.TextBody1
import tmg.flashback.ui.navigation.NavigationType
import tmg.flashback.ui.navigation.OverlappingPanels
import tmg.flashback.ui.navigation.OverlappingPanelsState
import tmg.flashback.ui.navigation.OverlappingPanelsValue
import tmg.flashback.xr.LocalXR

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppContainer(
    openPanel: () -> Unit,
    navigationType: NavigationType,
    windowAdaptiveInfo: WindowAdaptiveInfo,
    windowSizeClass: WindowSizeClass,
    backStack: NavBackStack<NavKey>,
    panelsState: OverlappingPanelsState,
    paddingValues: PaddingValues,
    appNavigationViewModel: AppNavigationViewModel,
) {
    val coroutineScope = rememberCoroutineScope()

    val appGraph = movableContentOf {
        AppGraph(
            openPanel = {
                openPanel()
            },
            overlappingPanelsState = panelsState,
            backStack = backStack,
            appNavigationViewModel = appNavigationViewModel,
            insetPadding = paddingValues,
            windowAdaptiveInfo = windowAdaptiveInfo,
        )
    }

    val appNavigationUiState = appNavigationViewModel.uiState.collectAsStateWithLifecycle()
    val menuAccessible = !appNavigationUiState.value.intoSubNavigation // Derive from VM
    val isCompact = !windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND)

    val compactEasterEggModifier = Modifier
        .background(AppTheme.colors.surfaceNav)
        .snow(appNavigationUiState.value.easterEggs.snow)
        .summer(appNavigationUiState.value.easterEggs.summer)
        .takeIf { isCompact } ?: Modifier
    val expandedEasterEggModifier = Modifier
        .snow(appNavigationUiState.value.easterEggs.snow, drawOver = false)
        .summer(appNavigationUiState.value.easterEggs.summer, drawOver = false)
        .takeIf { !isCompact } ?: Modifier

    if (navigationType.enablePane) {
        OverlappingPanels(
            modifier = Modifier
                .background(AppTheme.colors.surface),
            panelsState = when (menuAccessible) {
                true -> panelsState
                false -> OverlappingPanelsState(OverlappingPanelsValue.Closed)
            },
            gesturesEnabled = menuAccessible,
            centerPanelElevation = if (isCompact) 8.dp else 0.dp,
            panelStart = {
                AppNavigationDrawer(
                    appNavigationUiState = appNavigationUiState.value,
                    navigationItemClicked = {
                        backStack.clear()
                        backStack.add(it)
                    },
                    insetPadding = paddingValues,
                    closeMenu = {
                        coroutineScope.launch { panelsState.closePanels() }
                    },
                    openUrl = appNavigationViewModel::openWebpage,
                    modifier = compactEasterEggModifier
                )
            },
            panelCenter = {
                Row(modifier = Modifier
                    .fillMaxSize()
                    .background(AppTheme.colors.surface)
                    .then(expandedEasterEggModifier)
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
                        appGraph()
                    }
                }
            }
        )
    } else {
        Row(modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.surface)
            .then(expandedEasterEggModifier)
        ) {
            AppNavigationRail(
                modifier = Modifier.fillMaxHeight(),
                appNavigationUiState = appNavigationUiState.value,
                navigationItemClicked = {
                    backStack.clear()
                    backStack.add(it)
                },
                insetPadding = paddingValues
            )
            Box(
                modifier = Modifier.weight(1f)
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
                    appGraph()
                }
            }
        }
    }


    // Close panel if window size is changes via. configuration change
    DisposableEffect(windowSizeClass) {
        logDebug("Orientation change, closing panels if open")
        coroutineScope.launch { panelsState.closePanels() }
        return@DisposableEffect onDispose { }
    }

    // Close the menu if we shouldn't be showing it
    LaunchedEffect(menuAccessible, block = {
        if (!menuAccessible) {
            panelsState.closePanels()
        }
    })

    // Close the menu if bck is pressed on the menu
    BackHandler(panelsState.isStartPanelOpen) {
        coroutineScope.launch {
            panelsState.closePanels()
        }
    }
}