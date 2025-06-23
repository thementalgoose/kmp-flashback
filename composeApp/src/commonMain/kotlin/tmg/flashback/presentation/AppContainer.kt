package tmg.flashback.presentation

//import androidx.compose.ui.backhandler.BackHandler
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import kotlinx.coroutines.launch
import tmg.flashback.eastereggs.presentation.snow
import tmg.flashback.eastereggs.presentation.summer
import tmg.flashback.infrastructure.log.logDebug
import tmg.flashback.presentation.navigation.AppNavigationDrawer
import tmg.flashback.presentation.navigation.AppNavigationRail
import tmg.flashback.presentation.navigation.AppNavigationViewModel
import tmg.flashback.style.AppTheme
import tmg.flashback.ui.navigation.OverlappingPanels
import tmg.flashback.ui.navigation.OverlappingPanelsState
import tmg.flashback.ui.navigation.OverlappingPanelsValue

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppContainer(
    windowAdaptiveInfo: WindowAdaptiveInfo,
    windowSizeClass: WindowSizeClass,
    navController: NavHostController,
    panelsState: OverlappingPanelsState,
    paddingValues: PaddingValues,
    appNavigationViewModel: AppNavigationViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    val menuAccessible = true // Derive from VM

    val appNavigationUiState = appNavigationViewModel.uiState.collectAsStateWithLifecycle()
    val easterEggModifier = Modifier
        .snow(appNavigationUiState.value.easterEggs.snow)
        .summer(appNavigationUiState.value.easterEggs.summer)

    val isCompact = windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT

    OverlappingPanels(
        modifier = Modifier
            .background(AppTheme.colors.surface),
        panelsState = when (isCompact) {
            true -> panelsState
            false -> OverlappingPanelsState(OverlappingPanelsValue.Closed)
        },
        gesturesEnabled = isCompact && menuAccessible,
        centerPanelElevation = if (isCompact) 8.dp else 0.dp,
        panelStart = {
            AppNavigationDrawer(
                appNavigationUiState = appNavigationUiState.value,
                navigationItemClicked = { navController.navigate(it) },
                insetPadding = paddingValues,
                modifier = if (isCompact) easterEggModifier else Modifier
            )
        },
        panelCenter = {
            Row(modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colors.surface)
                .then(if (!isCompact) easterEggModifier else Modifier)
            ) {
                if (windowSizeClass.windowWidthSizeClass != WindowWidthSizeClass.COMPACT) {
                    AppNavigationRail(
                        appNavigationUiState = appNavigationUiState.value,
                        navigationItemClicked = {
                            navController.navigate(it)
                        },
                        insetPadding = paddingValues
                    )
                }
                Row(modifier = Modifier
                    .weight(1f)
                ) {
                    AppGraph(
                        appNavigationViewModel = appNavigationViewModel,
                        navController = navController,
                        insetPadding = paddingValues,
                        windowAdaptiveInfo = windowAdaptiveInfo
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

//    // Close the menu if bck is pressed on the menu
//    BackHandler(panelsState.isStartPanelOpen) {
//        coroutineScope.launch {
//            panelsState.closePanels()
//        }
//    }
}