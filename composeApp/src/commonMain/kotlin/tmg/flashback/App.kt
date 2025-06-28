package tmg.flashback

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowWidthSizeClass
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.infrastructure.extensions.toEnum
import tmg.flashback.infrastructure.log.logInfo
import tmg.flashback.navigation.Screen
import tmg.flashback.presentation.AppContainer
import tmg.flashback.presentation.MenuItem
import tmg.flashback.presentation.navigation.AppNavigationViewModel
import tmg.flashback.presentation.toNavigationItem
import tmg.flashback.presentation.toScreen
import tmg.flashback.style.AppTheme
import tmg.flashback.style.text.TextBody1
import tmg.flashback.ui.components.AppScaffold
import tmg.flashback.ui.navigation.NavigationBar
import tmg.flashback.ui.navigation.OverlappingPanelsValue
import tmg.flashback.ui.navigation.appBarHeightWhenVertical
import tmg.flashback.ui.navigation.rememberOverlappingPanelsState

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

    val showBottomBar = isCompact && appNavigationUiState.value.screen in listOf(Screen.Calendar, Screen.DriverStandings, Screen.TeamStandings) && !appNavigationUiState.value.forceHideNavigationBar
    val systemNavigationBarHeight = WindowInsets.safeDrawing.asPaddingValues().calculateBottomPadding()
    val navigationBarHeight = appBarHeightWhenVertical + systemNavigationBarHeight
    val navigationBarPosition = animateDpAsState(
        targetValue = if (panelsState.isStartPanelOpen || !showBottomBar) navigationBarHeight else 0.dp,
        label = "navigationBarPosition"
    )

    AppTheme {
        AppScaffold(
            content = { paddingValues ->
                AppContainer(
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
                        modifier = Modifier.offset(y = navigationBarPosition.value),
                        list = items,
                        itemClicked = { item ->
                            val menuItem = item.id.toEnum<MenuItem> { it.key } ?: return@NavigationBar
                            navController.navigate(menuItem.toScreen())
                        }
                    )
                }
            }
        )
    }
}