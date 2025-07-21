package tmg.flashback

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarDuration.Indefinite
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowWidthSizeClass
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.feature_banner_soft_upgrade
import flashback.presentation.localisation.generated.resources.feature_banner_soft_upgrade_prompt
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.infrastructure.extensions.toEnum
import tmg.flashback.infrastructure.log.logInfo
import tmg.flashback.navigation.Screen
import tmg.flashback.presentation.AppContainer
import tmg.flashback.presentation.MenuItem
import tmg.flashback.presentation.navigation.AppNavigationViewModel
import tmg.flashback.presentation.sync.SyncBottomSheet
import tmg.flashback.presentation.toNavigationItem
import tmg.flashback.presentation.toScreen
import tmg.flashback.style.ApplicationTheme
import tmg.flashback.ui.components.AppScaffold
import tmg.flashback.ui.navigation.NavigationBar
import tmg.flashback.ui.navigation.OverlappingPanelsValue
import tmg.flashback.ui.navigation.appBarHeightWhenVertical
import tmg.flashback.ui.navigation.rememberOverlappingPanelsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val appNavigationViewModel: AppNavigationViewModel = koinViewModel()
    val appNavigationUiState = appNavigationViewModel.uiState.collectAsStateWithLifecycle()

    val windowAdaptiveInfo = currentWindowAdaptiveInfo()
    val windowSizeClass = windowAdaptiveInfo.windowSizeClass
    val isCompact = windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT

    val navController = rememberNavController()
    DisposableEffect(key1 = navController, effect = {
        logInfo("AppContainer", "Configuring navController to viewmodel")
        navController.addOnDestinationChangedListener(appNavigationViewModel)
        return@DisposableEffect onDispose {  }
    })

    val panelsState = rememberOverlappingPanelsState(OverlappingPanelsValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    val showBottomBar = isCompact && appNavigationUiState.value.screen in listOf(Screen.Calendar, Screen.DriverStandings, Screen.TeamStandings) && !appNavigationUiState.value.intoSubNavigation
    val systemNavigationBarHeight = WindowInsets.safeDrawing.asPaddingValues().calculateBottomPadding()
    val navigationBarHeight = appBarHeightWhenVertical + systemNavigationBarHeight
    val navigationBarPosition = animateDpAsState(
        targetValue = if (panelsState.isStartPanelOpen || !showBottomBar) navigationBarHeight else 0.dp,
        label = "navigationBarPosition"
    )

    val snackbarHostState = remember { SnackbarHostState() }

    // Soft upgrade
    snackbarHostState.SoftUpgrade(
        show = appNavigationUiState.value.promptSoftUpgrade,
        dismiss = { appNavigationViewModel.dismissSoftUpgrade() },
        actionPerformed = { appNavigationViewModel.openStore() }
    )

    // Screen
    ApplicationTheme {
        AppScaffold(
            content = { paddingValues ->
                AppContainer(
                    openPanel = {
                        coroutineScope.launch { panelsState.openStartPanel() }
                    },
                    panelsState = panelsState,
                    windowAdaptiveInfo = windowAdaptiveInfo,
                    windowSizeClass = windowSizeClass,
                    paddingValues = paddingValues,
                    appNavigationViewModel = appNavigationViewModel,
                    navController = navController
                )
            },
            bottomBar = {
                if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT) {
                    val screen = appNavigationUiState.value.screen
                    val items = listOf(
                        MenuItem.Calendar.toNavigationItem(screen == Screen.Calendar),
                        MenuItem.DriversStandings.toNavigationItem(screen == Screen.DriverStandings),
                        MenuItem.TeamsStandings.toNavigationItem(screen == Screen.TeamStandings)
                    )
                    NavigationBar(
                        bottomPadding = systemNavigationBarHeight,
                        modifier = Modifier
                            .offset(y = navigationBarPosition.value),
                        list = items,
                        itemClicked = { item ->
                            val menuItem = item.id.toEnum<MenuItem> { it.key } ?: return@NavigationBar
                            navController.navigate(menuItem.toScreen())
                        }
                    )
                }
            },
            snackbarHostState = snackbarHostState,
        )

        // Initial sync
        val promptContentSync = remember(appNavigationUiState.value.promptContentSync) {
            mutableStateOf(appNavigationUiState.value.promptContentSync)
        }
        SyncBottomSheet(
            show = promptContentSync.value,
            unlock = { promptContentSync.value = false },
            windowSizeClass = windowSizeClass
        )
    }
}

@Composable
fun SnackbarHostState.SoftUpgrade(
    show: Boolean,
    dismiss: () -> Unit,
    actionPerformed: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val message = stringResource(string.feature_banner_soft_upgrade)
    val prompt = stringResource(string.feature_banner_soft_upgrade_prompt)
    LaunchedEffect(Unit) {
        if (show) {
            coroutineScope.launch {
                val result = this@SoftUpgrade.showSnackbar(
                    message = message,
                    actionLabel = prompt,
                    withDismissAction = true,
                    duration = Indefinite
                )
                dismiss()
                if (result == SnackbarResult.ActionPerformed) {
                    actionPerformed()
                }
            }
        }
    }
}