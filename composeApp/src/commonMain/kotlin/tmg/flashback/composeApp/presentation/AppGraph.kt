package tmg.flashback.composeApp.presentation

import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.defaultTransitionSpec
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_EXPANDED_LOWER_BOUND
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND
import flashback.composeapp.generated.resources.Res
import flashback.composeapp.generated.resources.logo
import tmg.flashback.composeApp.presentation.navigation.AppNavigationViewModel
import tmg.flashback.composeApp.presentation.navigation.scene.SplitPaneScene
import tmg.flashback.composeApp.presentation.navigation.scene.rememberSplitPaneSceneStrategy
import tmg.flashback.composeApp.presentation.settings.AllSettingsScreen
import tmg.flashback.composeApp.presentation.settings.browser.SettingsBrowserScreen
import tmg.flashback.composeApp.presentation.settings.darkmode.SettingsDarkModeScreen
import tmg.flashback.composeApp.presentation.settings.layout.SettingsLayoutScreen
import tmg.flashback.composeApp.presentation.settings.notifications.results.SettingsNotificationResultsScreen
import tmg.flashback.composeApp.presentation.settings.notifications.upcoming.SettingsNotificationUpcomingScreen
import tmg.flashback.composeApp.presentation.settings.privacy.SettingsPrivacyScreen
import tmg.flashback.composeApp.presentation.settings.theme.SettingsThemeScreen
import tmg.flashback.composeApp.presentation.settings.weather.SettingsWeatherScreen
import tmg.flashback.composeApp.presentation.settings.widgets.SettingsWidgetScreen
import tmg.flashback.feature.about.presentation.AboutScreen
import tmg.flashback.feature.circuits.presentation.all.AllCircuitsScreen
import tmg.flashback.feature.circuits.presentation.circuit.CircuitScreen
import tmg.flashback.feature.constructors.presentation.stats.ConstructorStatsScreen
import tmg.flashback.feature.drivers.presentation.comparison.DriverComparisonScreen
import tmg.flashback.feature.drivers.presentation.stats.DriverStatsScreen
import tmg.flashback.feature.privacypolicy.presentation.PrivacyPolicyScreen
import tmg.flashback.feature.reactiongame.presentation.ReactionGameScreen
import tmg.flashback.feature.rss.presentation.configure.RssConfigureScreen
import tmg.flashback.feature.rss.presentation.feed.RSSScreen
import tmg.flashback.feature.season.presentation.calendar.CalendarScreen
import tmg.flashback.feature.season.presentation.driver_standings.DriverStandingsScreen
import tmg.flashback.feature.season.presentation.team_standings.TeamStandingsScreen
import tmg.flashback.feature.weekend.presentation.WeekendScreen
import tmg.flashback.navigation.NavAbout
import tmg.flashback.navigation.NavCalendar
import tmg.flashback.navigation.NavCircuit
import tmg.flashback.navigation.NavCircuits
import tmg.flashback.navigation.NavDriver
import tmg.flashback.navigation.NavDriverComparison
import tmg.flashback.navigation.NavDriverStandings
import tmg.flashback.navigation.NavPrivacyPolicy
import tmg.flashback.navigation.NavReactionGame
import tmg.flashback.navigation.NavRss
import tmg.flashback.navigation.NavSettings
import tmg.flashback.navigation.NavSettingsDarkMode
import tmg.flashback.navigation.NavSettingsInAppBrowser
import tmg.flashback.navigation.NavSettingsLayout
import tmg.flashback.navigation.NavSettingsNotificationResults
import tmg.flashback.navigation.NavSettingsNotificationUpcoming
import tmg.flashback.navigation.NavSettingsPrivacy
import tmg.flashback.navigation.NavSettingsRssConfigure
import tmg.flashback.navigation.NavSettingsTheme
import tmg.flashback.navigation.NavSettingsWeather
import tmg.flashback.navigation.NavSettingsWidgets
import tmg.flashback.navigation.NavTeam
import tmg.flashback.navigation.NavTeamStandings
import tmg.flashback.navigation.NavWebpage
import tmg.flashback.navigation.NavWeekend
import tmg.flashback.navigation.removeDetail
import tmg.flashback.navigation.replaceDetail
import tmg.flashback.navigation.replaceList
import tmg.flashback.style.AppTheme
import tmg.flashback.ui.insets.compactOnly
import tmg.flashback.webbrowser.presentation.WebScreen

@Composable
fun AppGraph(
    openPanel: () -> Unit,
    appNavigationViewModel: AppNavigationViewModel,
    insetPadding: PaddingValues,
    windowAdaptiveInfo: WindowAdaptiveInfo,
    backStack: NavBackStack<NavKey>,
) {
    val listDetailStrategy = rememberSplitPaneSceneStrategy<NavKey>()

    val isCompact = !windowAdaptiveInfo.windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND)
    val isExpanded = windowAdaptiveInfo.windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_EXPANDED_LOWER_BOUND)
    val navDisplayModifier = when (!isCompact && !isExpanded) {
        true -> Modifier
            .fillMaxSize()
            .padding(insetPadding)
            .padding(end = AppTheme.dimens.medium)
            .clip(RoundedCornerShape(AppTheme.dimens.radiusMedium))
            .background(AppTheme.colors.surfaceContainer1)
        false -> Modifier
            .fillMaxSize()
    }
    val insetPadding = when (isCompact) {
        true -> insetPadding
        false -> PaddingValues(0.dp)
    }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeDetail() },
        sceneStrategies = listOf(listDetailStrategy),
        transitionSpec = {
            fadeIn() togetherWith fadeOut()
        },
        popTransitionSpec = {
            fadeIn() togetherWith fadeOut()
        },
        predictivePopTransitionSpec = {
            fadeIn() togetherWith fadeOut()
        },
        modifier = navDisplayModifier,
        entryProvider = entryProvider {
            entry<NavCalendar>(metadata = SplitPaneScene.listPane()) {
                CalendarScreen(
                    paddingValues = insetPadding,
                    actionUpClicked = openPanel,
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass,
                    navigateTo = {
                        backStack.replaceDetail(it)
                    }
                )
            }
            entry<NavDriverStandings>(metadata = SplitPaneScene.listPane()) {
                DriverStandingsScreen(
                    paddingValues = insetPadding,
                    actionUpClicked = openPanel,
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass,
                    navigateTo = {
                        backStack.replaceDetail(it)
                    }
                )
            }
            entry<NavTeamStandings>(metadata = SplitPaneScene.listPane()) {
                TeamStandingsScreen(
                    paddingValues = insetPadding,
                    actionUpClicked = openPanel,
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass,
                    navigateTo = {
                        backStack.replaceDetail(it)
                    }
                )
            }
            entry<NavCircuits>(metadata = SplitPaneScene.listPane()) {
                AllCircuitsScreen(
                    paddingValues = insetPadding,
                    actionUpClicked = openPanel,
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass,
                    navigateTo = {
                        backStack.replaceDetail(it)
                    }
                )
            }

            entry<NavWeekend>(metadata = SplitPaneScene.detailPane()) {
                WeekendScreen(
                    data = it,
                    paddingValues = insetPadding,
                    showBack = !isExpanded,
                    actionUpClicked = { backStack.removeDetail() },
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass
                )
            }
            entry<NavCircuit>(metadata = SplitPaneScene.detailPane()) {
                CircuitScreen(
                    data = it,
                    paddingValues = insetPadding,
                    actionUpClicked = { backStack.removeDetail() },
                    showBack = !isExpanded,
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass
                )
            }
            entry<NavDriver>(metadata = SplitPaneScene.detailPane()) {
                DriverStatsScreen(
                    data = it,
                    paddingValues = insetPadding,
                    actionUpClicked = { backStack.removeDetail() },
                    showBack = !isExpanded,
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass,
                )
            }
            entry<NavTeam>(metadata = SplitPaneScene.detailPane()) {
                ConstructorStatsScreen(
                    data = it,
                    paddingValues = insetPadding,
                    actionUpClicked = { backStack.removeDetail() },
                    showBack = !isExpanded,
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass
                )
            }
            entry<NavDriverComparison>(metadata = SplitPaneScene.detailPane()) {
                DriverComparisonScreen(
                    season = it.season,
                    paddingValues = insetPadding,
                    actionUpClicked = { backStack.removeDetail() },
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass,
                )
            }

            entry<NavRss>(metadata = SplitPaneScene.listPane()) {
                RSSScreen(
                    paddingValues = insetPadding,
                    actionUpClicked = { openPanel() },
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass,
                    navigateTo = {
                        backStack.replaceDetail(it)
                    }
                )
            }
            entry<NavWebpage>(metadata = SplitPaneScene.detailPane()) {
                WebScreen(
                    url = it.url,
                    paddingValues = insetPadding,
                    actionUpClicked = { backStack.removeDetail() }
                )
            }

            entry<NavReactionGame>(metadata = SplitPaneScene.listPane()) {
                ReactionGameScreen(
                    paddingValues = insetPadding,
                    actionUpClicked = openPanel,
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass
                )
            }

            entry<NavSettings>(metadata = SplitPaneScene.listPane()) {
                AllSettingsScreen(
                    actionUpClicked = { openPanel() },
                    showMenu = isCompact,
                    navigateToSubScreen = { backStack.add(it) },
                    navigateTo = {
                        backStack.replaceList(it)
                    },
                    insetPadding = insetPadding
                )
            }
            entry<NavSettingsDarkMode>(metadata = SplitPaneScene.detailPane()) {
                SettingsDarkModeScreen(
                    actionUpClicked = { backStack.removeDetail() },
                    insetPadding = insetPadding,
                    showBack = !isExpanded
                )
            }
            entry<NavSettingsTheme>(metadata = SplitPaneScene.detailPane()) {
                SettingsThemeScreen(
                    actionUpClicked = { backStack.removeDetail() },
                    insetPadding = insetPadding,
                    showBack = !isExpanded
                )
            }
            entry<NavSettingsLayout>(metadata = SplitPaneScene.detailPane()) {
                SettingsLayoutScreen(
                    actionUpClicked = { backStack.removeDetail() },
                    insetPadding = insetPadding,
                    showBack = !isExpanded
                )
            }
            entry<NavSettingsWeather>(metadata = SplitPaneScene.detailPane()) {
                SettingsWeatherScreen(
                    actionUpClicked = { backStack.removeDetail() },
                    insetPadding = insetPadding,
                    showBack = !isExpanded
                )
            }
            entry<NavSettingsRssConfigure>(metadata = SplitPaneScene.detailPane()) {
                RssConfigureScreen(
                    actionUpClicked = { backStack.removeDetail() },
                    insetPadding = insetPadding,
                    showBack = !isExpanded
                )
            }
            entry<NavSettingsInAppBrowser>(metadata = SplitPaneScene.detailPane()) {
                SettingsBrowserScreen(
                    actionUpClicked = { backStack.removeDetail() },
                    insetPadding = insetPadding,
                    showBack = !isExpanded
                )
            }
            entry<NavSettingsNotificationResults>(metadata = SplitPaneScene.detailPane()) {
                SettingsNotificationResultsScreen(
                    actionUpClicked = { backStack.removeDetail() },
                    insetPadding = insetPadding,
                    showBack = !isExpanded
                )
            }
            entry<NavSettingsNotificationUpcoming>(metadata = SplitPaneScene.detailPane()) {
                SettingsNotificationUpcomingScreen(
                    actionUpClicked = { backStack.removeDetail() },
                    insetPadding = insetPadding,
                    showBack = !isExpanded
                )
            }
            entry<NavSettingsWidgets>(metadata = SplitPaneScene.detailPane()) {
                SettingsWidgetScreen(
                    actionUpClicked = { backStack.removeDetail() },
                    insetPadding = insetPadding,
                    showBack = !isExpanded
                )
            }
            entry<NavSettingsPrivacy>(metadata = SplitPaneScene.detailPane()) {
                SettingsPrivacyScreen(
                    actionUpClicked = { backStack.removeDetail() },
                    insetPadding = insetPadding,
                    showBack = !isExpanded
                )
            }
            entry<NavPrivacyPolicy>(metadata = SplitPaneScene.detailPane()) {
                PrivacyPolicyScreen(
                    paddingValues = insetPadding,
                    actionUpClicked = {
                        backStack.replaceList(NavSettings)
                    }
                )
            }

            entry<NavAbout>(metadata = SplitPaneScene.listPane()) {
                AboutScreen(
                    appIcon = Res.drawable.logo,
                    paddingValues = insetPadding,
                    actionUpClicked = openPanel,
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass
                )
            }
        }
    )
}