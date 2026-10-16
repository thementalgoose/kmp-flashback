@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)

package tmg.flashback.composeApp

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarDuration.Indefinite
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.rememberNavBackStack
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.feature_banner_soft_upgrade
import flashback.presentation.localisation.generated.resources.feature_banner_soft_upgrade_prompt
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.composeApp.presentation.AppContainer
import tmg.flashback.composeApp.presentation.MenuItem
import tmg.flashback.composeApp.presentation.generateFloatingNavItems
import tmg.flashback.composeApp.presentation.navigation.AppNavigationOrbiter
import tmg.flashback.composeApp.presentation.navigation.AppNavigationViewModel
import tmg.flashback.composeApp.presentation.navigation.previewAppNavigationViewModel
import tmg.flashback.composeApp.presentation.sync.SyncBottomSheet
import tmg.flashback.composeApp.presentation.toScreen
import tmg.flashback.feature.rss.usecases.IsRssEnabledUseCase
import tmg.flashback.infrastructure.device.Device
import tmg.flashback.infrastructure.extensions.toEnum
import tmg.flashback.infrastructure.log.logDebug
import tmg.flashback.navigation.NavCalendar
import tmg.flashback.navigation.NavDriverStandings
import tmg.flashback.navigation.NavTeamStandings
import tmg.flashback.navigation.Screen
import tmg.flashback.navigation.saveStateConfiguration
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewDevices
import tmg.flashback.style.text.TextBody1
import tmg.flashback.ui.navigation.FloatingHorizontalNavigationBar
import tmg.flashback.ui.navigation.FloatingVerticalNavigationBar
import tmg.flashback.ui.navigation.NavigationType
import tmg.flashback.ui.navigation.NavigationType.NavDrawerBottomNav
import tmg.flashback.ui.navigation.NavigationType.NavDrawerSideNav
import tmg.flashback.ui.navigation.NavigationType.NavOrbiter
import tmg.flashback.ui.navigation.OverlappingPanelsValue
import tmg.flashback.ui.navigation.floatingHorizontalNavHeight
import tmg.flashback.ui.navigation.floatingVerticalAppWidth
import tmg.flashback.ui.navigation.rememberNavigationLayoutType
import tmg.flashback.ui.navigation.rememberOverlappingPanelsState
import tmg.flashback.ui.toasts.ToastManager
import tmg.flashback.xr.LocalXR
import kotlin.text.clear

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val appNavigationViewModel: AppNavigationViewModel = koinViewModel()

    val toastManager: ToastManager = koinInject()
    val toastBackground: Color = AppTheme.colors.primaryContainer
    val toastForeground: Color = AppTheme.colors.onPrimaryContainer
    LaunchedEffect(toastBackground, toastForeground) {
        toastManager.backgroundColor = toastBackground
        toastManager.foregroundColor = toastForeground
    }

    ApplicationTheme {
        AppContent(
            appNavigationViewModel = appNavigationViewModel
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent(
    appNavigationViewModel: AppNavigationViewModel
) {
    val appNavigationUiState = appNavigationViewModel.uiState.collectAsStateWithLifecycle()

    val windowAdaptiveInfo = currentWindowAdaptiveInfoV2()
    val windowSizeClass = windowAdaptiveInfo.windowSizeClass

    val backStack = rememberNavBackStack(saveStateConfiguration, NavCalendar)
    DisposableEffect(backStack.lastOrNull()) {
        logDebug("Stack", "Back Stack contents: \n${backStack.joinToString(separator = "\n") { "- $it" }}")
        appNavigationViewModel.destinationUpdated(backStack.lastOrNull() as? Screen)
        return@DisposableEffect onDispose {  }
    }

    val panelsState = rememberOverlappingPanelsState(OverlappingPanelsValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // Soft upgrade
    snackbarHostState.SoftUpgrade(
        show = appNavigationUiState.value.promptSoftUpgrade,
        dismiss = { appNavigationViewModel.dismissSoftUpgrade() },
        actionPerformed = { appNavigationViewModel.openStore() }
    )

    // Screen
    val navigationType = rememberNavigationLayoutType(
        showXr = LocalXR.current.isSpatialUiEnabled
    )
    val isSubNavigation = appNavigationUiState.value.intoSubNavigation
    val showFloatingBar = (navigationType == NavDrawerBottomNav || navigationType == NavDrawerSideNav) &&
            appNavigationUiState.value.screen in listOf(NavCalendar, NavDriverStandings, NavTeamStandings) &&
            !isSubNavigation &&
            panelsState.isPanelsClosed

    Box(
        modifier = Modifier.testTag("App")
    ) {
        // App Content
        AppContainer(
            openPanel = {
                coroutineScope.launch { panelsState.openStartPanel() }
            },
            navigationType = navigationType,
            panelsState = panelsState,
            windowAdaptiveInfo = windowAdaptiveInfo,
            windowSizeClass = windowSizeClass,
            paddingValues = WindowInsets.safeDrawing.asPaddingValues(),
            appNavigationViewModel = appNavigationViewModel,
            backStack = backStack,
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

        // Floating Nav from bottom
        BottomNav(
            navigationType = navigationType,
            showFloatingBar = showFloatingBar,
            screen = appNavigationUiState.value.screen,
            itemClicked = {
                backStack.clear()
                backStack.add(it)
            }
        )

        // Right Nav
        RightNav(
            navigationType = navigationType,
            showFloatingBar = showFloatingBar,
            screen = appNavigationUiState.value.screen,
            itemClicked = {
                backStack.clear()
                backStack.add(it)
            }
        )

        // Snackbar
        SnackbarHost(
            navigationType = navigationType,
            showFloatingBar = showFloatingBar,
            snackbarHostState = snackbarHostState
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

    // XR
    if (navigationType == NavOrbiter) {
        AppNavigationOrbiter(
            appNavigationUiState = appNavigationUiState.value,
            navigationItemClicked = {
                backStack.clear()
                backStack.add(it)
            }
        )
    }
}

@Composable
private fun BoxScope.BottomNav(
    navigationType: NavigationType,
    showFloatingBar: Boolean,
    screen: Screen?,
    itemClicked: (Screen) -> Unit
) {
    val bottomNavPadding = AppTheme.dimens.medium
    val systemNavigationBarHeight = WindowInsets.safeDrawing.asPaddingValues().calculateBottomPadding()
    val bottomNavPosition = animateDpAsState(
        targetValue = when ((navigationType == NavDrawerBottomNav) && showFloatingBar) {
            true -> 0.dp
            false -> floatingHorizontalNavHeight + systemNavigationBarHeight + bottomNavPadding
        },
        label = "BottomNavPosition"
    )
    if (navigationType == NavDrawerBottomNav) {
        val screen = screen
        val items = generateFloatingNavItems(screen)
        FloatingHorizontalNavigationBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .offset(y = bottomNavPosition.value)
                .background(Brush.verticalGradient(listOf(Color.Transparent, AppTheme.colors.surface)))
                .padding(horizontal = AppTheme.dimens.medium)
                .padding(bottom = systemNavigationBarHeight + bottomNavPadding),
            list = items,
            itemClicked = { item ->
                val menuItem = item.id.toEnum<MenuItem> { it.key } ?: return@FloatingHorizontalNavigationBar
                val screen = menuItem.toScreen() ?: return@FloatingHorizontalNavigationBar
                itemClicked(screen)
            }
        )
    }
}

@Composable
private fun BoxScope.RightNav(
    navigationType: NavigationType,
    showFloatingBar: Boolean,
    screen: Screen?,
    itemClicked: (Screen) -> Unit
) {
    val rightNavPadding = AppTheme.dimens.medium
    val systemNavigationBarHeight = WindowInsets.safeDrawing.asPaddingValues().calculateBottomPadding()
    // Floating Nav from right
    val rightNavPosition = animateDpAsState(
        targetValue = when ((navigationType == NavDrawerSideNav) && showFloatingBar) {
            true -> 0.dp
            false -> floatingVerticalAppWidth + rightNavPadding
        },
        label = "SideNavPosition"
    )
    if (navigationType == NavDrawerSideNav) {
        val screen = screen
        val items = generateFloatingNavItems(screen)
        FloatingVerticalNavigationBar(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = rightNavPosition.value)
                .background(Brush.verticalGradient(listOf(Color.Transparent, AppTheme.colors.surface)))
                .padding(end = rightNavPadding)
                .padding(bottom = systemNavigationBarHeight + AppTheme.dimens.medium),
            list = items,
            itemClicked = { item ->
                val menuItem = item.id.toEnum<MenuItem> { it.key } ?: return@FloatingVerticalNavigationBar
                val screen = menuItem.toScreen() ?: return@FloatingVerticalNavigationBar
                itemClicked(screen)
            }
        )
    }
}

@Composable
private fun BoxScope.SnackbarHost(
    navigationType: NavigationType,
    showFloatingBar: Boolean,
    snackbarHostState: SnackbarHostState
) {
    val snackbarHostPadding = animateDpAsState(
        targetValue = if (showFloatingBar && navigationType == NavDrawerBottomNav) floatingHorizontalNavHeight else 0.dp,
        label = "SnackbarHostPadding"
    )
    // Snackbar host
    SnackbarHost(
        hostState = snackbarHostState,
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = snackbarHostPadding.value)
            .navigationBarsPadding()
            .imePadding()
            .padding(bottom = AppTheme.dimens.medium)
    )
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

@PreviewDevices
@Composable
private fun AppScreen() {
    ApplicationThemePreview {
        AppContent(
            appNavigationViewModel = previewAppNavigationViewModel
        )
    }
}