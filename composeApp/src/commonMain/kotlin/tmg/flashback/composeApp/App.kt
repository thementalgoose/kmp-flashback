@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)

package tmg.flashback.composeApp

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarDuration.Indefinite
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.feature_banner_soft_upgrade
import flashback.presentation.localisation.generated.resources.feature_banner_soft_upgrade_prompt
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.composeApp.presentation.AppContainer
import tmg.flashback.composeApp.presentation.MenuItem
import tmg.flashback.composeApp.presentation.navigation.AppNavigationViewModel
import tmg.flashback.composeApp.presentation.sync.SyncBottomSheet
import tmg.flashback.composeApp.presentation.toNavigationItem
import tmg.flashback.composeApp.presentation.toScreen
import tmg.flashback.infrastructure.extensions.toEnum
import tmg.flashback.infrastructure.log.logDebug
import tmg.flashback.navigation.NavCalendar
import tmg.flashback.navigation.NavDriverStandings
import tmg.flashback.navigation.NavTeamStandings
import tmg.flashback.navigation.saveStateConfiguration
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationTheme
import tmg.flashback.ui.components.AppScaffold
import tmg.flashback.ui.navigation.FloatingNavigationBar
import tmg.flashback.ui.navigation.OverlappingPanelsValue
import tmg.flashback.ui.navigation.appBarHeight
import tmg.flashback.ui.navigation.rememberOverlappingPanelsState
import tmg.flashback.ui.toasts.ToastManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val appNavigationViewModel: AppNavigationViewModel = koinViewModel()
    val appNavigationUiState = appNavigationViewModel.uiState.collectAsStateWithLifecycle()

    val windowAdaptiveInfo = currentWindowAdaptiveInfo()
    val windowSizeClass = windowAdaptiveInfo.windowSizeClass
    val isCompact = !windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND)

    val toastManager: ToastManager = koinInject()
    val toastBackground: Color = AppTheme.colors.primaryContainer
    val toastForeground: Color = AppTheme.colors.onPrimaryContainer
    LaunchedEffect(toastBackground, toastForeground) {
        toastManager.backgroundColor = toastBackground
        toastManager.foregroundColor = toastForeground
    }

    val backStack = rememberNavBackStack(saveStateConfiguration, NavCalendar)
    DisposableEffect(backStack.lastOrNull()) {
        logDebug("Stack", "Back Stack contents: \n${backStack.joinToString(separator = "\n") { "- $it" }}")
        appNavigationViewModel.destinationUpdated(backStack.lastOrNull())
        return@DisposableEffect onDispose {  }
    }

    val panelsState = rememberOverlappingPanelsState(OverlappingPanelsValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    val showBottomBar = isCompact && appNavigationUiState.value.screen in listOf(NavCalendar, NavDriverStandings, NavTeamStandings) && !appNavigationUiState.value.intoSubNavigation
    val systemNavigationBarHeight = WindowInsets.safeDrawing.asPaddingValues().calculateBottomPadding() + 16.dp
    val navigationBarHeight = appBarHeight + systemNavigationBarHeight
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
            modifier = Modifier.testTag("App"),
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
                    backStack = backStack
                )

                // Fake translucent status bar
                Box(
                    modifier = Modifier
                        .height(WindowInsets.statusBars.asPaddingValues().calculateTopPadding())
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .background(Brush.verticalGradient(
                            colors = listOf(AppTheme.colors.surface, Color.Transparent)
                        ))
                )
            },
            bottomBar = {
                if (!windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND)) {
                    val screen = appNavigationUiState.value.screen
                    val items = listOf(
                        MenuItem.Calendar.toNavigationItem(screen == NavCalendar),
                        MenuItem.DriversStandings.toNavigationItem(screen == NavDriverStandings),
                        MenuItem.TeamsStandings.toNavigationItem(screen == NavTeamStandings)
                    )
                    FloatingNavigationBar(
                        bottomPadding = systemNavigationBarHeight,
                        modifier = Modifier
                            .offset(y = navigationBarPosition.value)
                            .background(Brush.verticalGradient(listOf(Color.Transparent, AppTheme.colors.surface)))
                            .padding(horizontal = AppTheme.dimens.medium),
                        list = items,
                        itemClicked = { item ->
                            val menuItem = item.id.toEnum<MenuItem> { it.key } ?: return@FloatingNavigationBar
                            val screen = menuItem.toScreen() ?: return@FloatingNavigationBar
                            backStack.clear()
                            backStack.add(screen)
                        }
                    )
                }
            },
            snackbarHostState = snackbarHostState,
        )

        // Initial sync
        val isOpen = remember { mutableStateOf(appNavigationUiState.value.promptContentSync) }
        if (isOpen.value) {
            SyncBottomSheet(
                hide = { isOpen.value = false },
                windowSizeClass = windowSizeClass
            )
        }
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