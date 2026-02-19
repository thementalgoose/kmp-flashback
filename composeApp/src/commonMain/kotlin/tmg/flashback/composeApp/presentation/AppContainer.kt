package tmg.flashback.composeApp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
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
import tmg.flashback.ui.navigation.OverlappingPanels
import tmg.flashback.ui.navigation.OverlappingPanelsState
import tmg.flashback.ui.navigation.OverlappingPanelsValue
import tmg.flashback.xr.LocalXR

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppContainer(
    openPanel: () -> Unit,
    windowAdaptiveInfo: WindowAdaptiveInfo,
    windowSizeClass: WindowSizeClass,
    backStack: NavBackStack<NavKey>,
    panelsState: OverlappingPanelsState,
    paddingValues: PaddingValues,
    appNavigationViewModel: AppNavigationViewModel
) {
    val coroutineScope = rememberCoroutineScope()

    val appNavigationUiState = appNavigationViewModel.uiState.collectAsStateWithLifecycle()
    val menuAccessible = !appNavigationUiState.value.intoSubNavigation // Derive from VM
    val isCompact = !windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND)

    val compactEasterEggModifier = Modifier
        .snow(appNavigationUiState.value.easterEggs.snow)
        .summer(appNavigationUiState.value.easterEggs.summer)
        .takeIf { isCompact } ?: Modifier
    val expandedEasterEggModifier = Modifier
        .snow(appNavigationUiState.value.easterEggs.snow, drawOver = false)
        .summer(appNavigationUiState.value.easterEggs.summer, drawOver = false)
        .takeIf { !isCompact } ?: Modifier

    val isXrDevice = LocalXR.current.isXrDevice
    val isSpacialUiEnabled: Boolean = LocalXR.current.isSpatialUiEnabled
    if (isSpacialUiEnabled) {
        AppNavigationOrbiter(
            appNavigationUiState = appNavigationUiState.value,
            navigationItemClicked = {
                backStack.clear()
                backStack.add(it)
            }
        )
    }


    OverlappingPanels(
        modifier = Modifier
            .background(AppTheme.colors.surface),
        panelsState = when (isCompact && menuAccessible && !isSpacialUiEnabled) {
            true -> panelsState
            false -> OverlappingPanelsState(OverlappingPanelsValue.Closed)
        },
        gesturesEnabled = isCompact && menuAccessible && !isSpacialUiEnabled,
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
                showXr = isXrDevice,
                modifier = compactEasterEggModifier
            )
        },
        panelCenter = {
            Row(modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colors.surface)
                .then(expandedEasterEggModifier)
            ) {
                if (windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND) && !isSpacialUiEnabled) {
                    AppNavigationRail(
                        appNavigationUiState = appNavigationUiState.value,
                        navigationItemClicked = {
                            backStack.clear()
                            backStack.add(it)
                        },
                        showXr = isXrDevice,
                        insetPadding = paddingValues
                    )
                }
                Row(modifier = Modifier
                    .weight(1f)
                ) {
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
    )

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