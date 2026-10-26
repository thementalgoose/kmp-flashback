package tmg.flashback.composeApp.presentation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_EXPANDED_LOWER_BOUND
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND
import flashback.composeapp.generated.resources.Res
import flashback.composeapp.generated.resources.logo
import tmg.flashback.composeApp.presentation.info.InfoScreen
import tmg.flashback.composeApp.presentation.navigation.AppNavigationViewModel
import tmg.flashback.composeApp.presentation.navigation.scene.SplitPaneScene
import tmg.flashback.composeApp.presentation.navigation.scene.rememberSplitPaneSceneStrategy
import tmg.flashback.composeApp.presentation.paddock.PaddockScreen
import tmg.flashback.composeApp.presentation.settings.AllSettingsScreen
import tmg.flashback.composeApp.presentation.settings.browser.SettingsBrowserScreen
import tmg.flashback.composeApp.presentation.settings.darkmode.SettingsDarkModeScreen
import tmg.flashback.composeApp.presentation.settings.layout_home.SettingsLayoutHomeScreen
import tmg.flashback.composeApp.presentation.settings.layout_race.SettingsLayoutRaceScreen
import tmg.flashback.composeApp.presentation.settings.notifications.results.SettingsNotificationResultsScreen
import tmg.flashback.composeApp.presentation.settings.notifications.upcoming.SettingsNotificationUpcomingScreen
import tmg.flashback.composeApp.presentation.settings.privacy.SettingsPrivacyScreen
import tmg.flashback.composeApp.presentation.settings.theme.SettingsThemeScreen
import tmg.flashback.composeApp.presentation.settings.widgets.SettingsWidgetScreen
import tmg.flashback.feature.about.presentation.AboutScreen
import tmg.flashback.feature.circuits.presentation.circuit.CircuitScreen
import tmg.flashback.feature.constructors.presentation.stats.ConstructorStatsScreen
import tmg.flashback.feature.drivers.presentation.comparison.DriverComparisonScreen
import tmg.flashback.feature.drivers.presentation.stats.DriverStatsScreen
import tmg.flashback.feature.glossary.presentation.all.GlossaryScreen
import tmg.flashback.feature.glossary.presentation.details.GlossaryDetailScreen
import tmg.flashback.feature.lineup.presentation.LineupScreen
import tmg.flashback.feature.privacypolicy.presentation.PrivacyPolicyScreen
import tmg.flashback.feature.reactiongame.presentation.ReactionGameScreen
import tmg.flashback.feature.rss.presentation.configure.RssConfigureScreen
import tmg.flashback.feature.rss.presentation.feed.RSSScreen
import tmg.flashback.feature.search.presentation.SearchScreen
import tmg.flashback.feature.season.presentation.calendar.CalendarScreen
import tmg.flashback.feature.season.presentation.driver_standings.DriverStandingsScreen
import tmg.flashback.feature.season.presentation.team_standings.TeamStandingsScreen
import tmg.flashback.feature.weekend.presentation.WeekendScreen
import tmg.flashback.navigation.NavAbout
import tmg.flashback.navigation.NavCalendar
import tmg.flashback.navigation.NavCircuit
import tmg.flashback.navigation.NavDriver
import tmg.flashback.navigation.NavDriverComparison
import tmg.flashback.navigation.NavDriverStandings
import tmg.flashback.navigation.NavGlossary
import tmg.flashback.navigation.NavGlossaryDetail
import tmg.flashback.navigation.NavInfo
import tmg.flashback.navigation.NavLineup
import tmg.flashback.navigation.NavPaddock
import tmg.flashback.navigation.NavPrivacyPolicy
import tmg.flashback.navigation.NavReactionGame
import tmg.flashback.navigation.NavRss
import tmg.flashback.navigation.NavSearch
import tmg.flashback.navigation.NavSettings
import tmg.flashback.navigation.NavSettingsDarkMode
import tmg.flashback.navigation.NavSettingsInAppBrowser
import tmg.flashback.navigation.NavSettingsLayoutHome
import tmg.flashback.navigation.NavSettingsLayoutRace
import tmg.flashback.navigation.NavSettingsNotificationResults
import tmg.flashback.navigation.NavSettingsNotificationUpcoming
import tmg.flashback.navigation.NavSettingsPrivacy
import tmg.flashback.navigation.NavSettingsRssConfigure
import tmg.flashback.navigation.NavSettingsTheme
import tmg.flashback.navigation.NavSettingsWidgets
import tmg.flashback.navigation.NavStyleGuide
import tmg.flashback.navigation.NavTeam
import tmg.flashback.navigation.NavTeamStandings
import tmg.flashback.navigation.NavWebpage
import tmg.flashback.navigation.NavWeekend
import tmg.flashback.navigation.appendMaster
import tmg.flashback.navigation.pop
import tmg.flashback.navigation.popMaster
import tmg.flashback.navigation.removeDetail
import tmg.flashback.navigation.replaceDetail
import tmg.flashback.style.AppTheme
import tmg.flashback.style.styleguide.StyleGuideScreen
import tmg.flashback.ui.extensions.only
import tmg.flashback.webbrowser.presentation.WebScreen

@Composable
fun AppGraph(
    appNavigationViewModel: AppNavigationViewModel,
    windowAdaptiveInfo: WindowAdaptiveInfo,
    backStack: NavBackStack<NavKey>,
) {
    val listDetailStrategy = rememberSplitPaneSceneStrategy<NavKey>()

    val isCompact = !windowAdaptiveInfo.windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND)
    val isExpanded = windowAdaptiveInfo.windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_EXPANDED_LOWER_BOUND)

    val navDisplayModifier = Modifier
        .fillMaxSize()
        .background(AppTheme.colors.surface)

    val sides = when (isCompact) {
        true -> WindowInsetsSides.Horizontal + WindowInsetsSides.Vertical
        false -> WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom
    }
    val insetPadding = WindowInsets.safeDrawing.only(sides)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.pop() },
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
                    paddingValues = insetPadding.asPaddingValues(),
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass,
                    navigateTo = {
                        backStack.replaceDetail(it)
                    }
                )
            }
            entry<NavDriverStandings>(metadata = SplitPaneScene.listPane()) {
                DriverStandingsScreen(
                    paddingValues = insetPadding.asPaddingValues(),
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass,
                    navigateTo = {
                        backStack.replaceDetail(it)
                    }
                )
            }
            entry<NavTeamStandings>(metadata = SplitPaneScene.listPane()) {
                TeamStandingsScreen(
                    paddingValues = insetPadding.asPaddingValues(),
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass,
                    navigateTo = {
                        backStack.replaceDetail(it)
                    }
                )
            }
            entry<NavPaddock>(metadata = SplitPaneScene.listPane()) {
                PaddockScreen(
                    paddingValues = insetPadding.asPaddingValues(),
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass,
                    navigateTo = {
                        backStack.appendMaster(it)
                    }
                )
            }

            entry<NavWeekend>(metadata = SplitPaneScene.detailPane()) {
                WeekendScreen(
                    data = it,
                    paddingValues = insetPadding.asPaddingValues(),
                    showBack = !isExpanded,
                    actionUpClicked = { backStack.removeDetail() },
                    navigateTo = { backStack.replaceDetail(it) },
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass
                )
            }
            entry<NavLineup>(metadata = SplitPaneScene.listPane()) {
                LineupScreen(
                    paddingValues = insetPadding.asPaddingValues(),
                    actionUpClicked = { backStack.popMaster() },
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass,
                    navigateTo = {
                        backStack.replaceDetail(it)
                    })
            }
            entry<NavCircuit>(metadata = SplitPaneScene.detailPane()) {
                CircuitScreen(
                    data = it,
                    paddingValues = insetPadding.asPaddingValues(),
                    actionUpClicked = { backStack.removeDetail() },
                    showBack = !isExpanded,
                    navigateTo = { backStack.replaceDetail(it) },
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass
                )
            }
            entry<NavDriver>(metadata = SplitPaneScene.detailPane()) {
                DriverStatsScreen(
                    data = it,
                    paddingValues = insetPadding.asPaddingValues(),
                    actionUpClicked = { backStack.removeDetail() },
                    showBack = !isExpanded,
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass,
                )
            }
            entry<NavTeam>(metadata = SplitPaneScene.detailPane()) {
                ConstructorStatsScreen(
                    data = it,
                    paddingValues = insetPadding.asPaddingValues(),
                    actionUpClicked = { backStack.removeDetail() },
                    showBack = !isExpanded,
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass
                )
            }
            entry<NavDriverComparison>(metadata = SplitPaneScene.detailPane()) {
                DriverComparisonScreen(
                    season = it.season,
                    paddingValues = insetPadding.asPaddingValues(),
                    actionUpClicked = { backStack.removeDetail() },
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass,
                )
            }

            entry<NavRss>(metadata = SplitPaneScene.listPane()) {
                RSSScreen(
                    paddingValues = insetPadding.asPaddingValues(),
                    actionUpClicked = { backStack.popMaster() },
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass,
                    navigateTo = {
                        backStack.replaceDetail(it)
                    }
                )
            }
            entry<NavWebpage>(metadata = SplitPaneScene.detailPane()) {
                WebScreen(
                    url = it.url,
                    paddingValues = insetPadding.asPaddingValues(),
                    actionUpClicked = { backStack.removeDetail() }
                )
            }
            entry<NavSearch>(metadata = SplitPaneScene.listPane()) {
                SearchScreen(
                    paddingValues = insetPadding.asPaddingValues(),
                    actionUpClicked = { backStack.popMaster() },
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass,
                    navigateTo = {
                        backStack.replaceDetail(it)
                    },
                    filtering = it.filter
                )
            }

            entry<NavReactionGame>(metadata = SplitPaneScene.listPane()) {
                ReactionGameScreen(
                    paddingValues = insetPadding.asPaddingValues(),
                    actionUpClicked = { backStack.popMaster() },
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass
                )
            }

            entry<NavGlossary>(metadata = SplitPaneScene.listPane()) {
                GlossaryScreen(
                    paddingValues = insetPadding.asPaddingValues(),
                    actionUpClicked = { backStack.popMaster() },
                    navigateTo = {
                        backStack.replaceDetail(it)
                    },
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass
                )
            }

            entry<NavGlossaryDetail>(metadata = SplitPaneScene.detailPane()) {
                GlossaryDetailScreen(
                    data = it,
                    paddingValues = insetPadding.asPaddingValues(),
                    showBack = !isExpanded,
                    actionUpClicked = { backStack.removeDetail() },
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass
                )
            }

            entry<NavSettings>(metadata = SplitPaneScene.listPane()) {
                AllSettingsScreen(
                    navigateToSubScreen = { backStack.replaceDetail(it) },
                    navigateTo = {
                        backStack.appendMaster(it)
                    },
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass,
                    paddingValues = insetPadding.asPaddingValues()
                )
            }
            entry<NavSettingsDarkMode>(metadata = SplitPaneScene.detailPane()) {
                SettingsDarkModeScreen(
                    actionUpClicked = { backStack.removeDetail() },
                    insetPadding = insetPadding.asPaddingValues(),
                    showBack = !isExpanded
                )
            }
            entry<NavSettingsTheme>(metadata = SplitPaneScene.detailPane()) {
                SettingsThemeScreen(
                    actionUpClicked = { backStack.removeDetail() },
                    insetPadding = insetPadding.asPaddingValues(),
                    showBack = !isExpanded
                )
            }
            entry<NavSettingsLayoutHome>(metadata = SplitPaneScene.detailPane()) {
                SettingsLayoutHomeScreen(
                    actionUpClicked = { backStack.removeDetail() },
                    insetPadding = insetPadding.asPaddingValues(),
                    showBack = !isExpanded
                )
            }
            entry<NavSettingsLayoutRace>(metadata = SplitPaneScene.detailPane()) {
                SettingsLayoutRaceScreen(
                    actionUpClicked = { backStack.removeDetail() },
                    insetPadding = insetPadding.asPaddingValues(),
                    showBack = !isExpanded
                )
            }
            entry<NavSettingsRssConfigure>(metadata = SplitPaneScene.detailPane()) {
                RssConfigureScreen(
                    actionUpClicked = { backStack.removeDetail() },
                    insetPadding = insetPadding.asPaddingValues(),
                    showBack = !isExpanded
                )
            }
            entry<NavSettingsInAppBrowser>(metadata = SplitPaneScene.detailPane()) {
                SettingsBrowserScreen(
                    actionUpClicked = { backStack.removeDetail() },
                    insetPadding = insetPadding.asPaddingValues(),
                    showBack = !isExpanded
                )
            }
            entry<NavSettingsNotificationResults>(metadata = SplitPaneScene.detailPane()) {
                SettingsNotificationResultsScreen(
                    actionUpClicked = { backStack.removeDetail() },
                    insetPadding = insetPadding.asPaddingValues(),
                    showBack = !isExpanded
                )
            }
            entry<NavSettingsNotificationUpcoming>(metadata = SplitPaneScene.detailPane()) {
                SettingsNotificationUpcomingScreen(
                    actionUpClicked = { backStack.removeDetail() },
                    insetPadding = insetPadding.asPaddingValues(),
                    showBack = !isExpanded
                )
            }
            entry<NavSettingsWidgets>(metadata = SplitPaneScene.detailPane()) {
                SettingsWidgetScreen(
                    actionUpClicked = { backStack.removeDetail() },
                    insetPadding = insetPadding.asPaddingValues(),
                    showBack = !isExpanded
                )
            }
            entry<NavSettingsPrivacy>(metadata = SplitPaneScene.detailPane()) {
                SettingsPrivacyScreen(
                    actionUpClicked = { backStack.removeDetail() },
                    insetPadding = insetPadding.asPaddingValues(),
                    showBack = !isExpanded
                )
            }
            entry<NavPrivacyPolicy>(metadata = SplitPaneScene.detailPane()) {
                PrivacyPolicyScreen(
                    paddingValues = insetPadding.asPaddingValues(),
                    actionUpClicked = {
                        backStack.appendMaster(NavSettings)
                    }
                )
            }

            entry<NavAbout>(metadata = SplitPaneScene.detailPane()) {
                AboutScreen(
                    appIcon = Res.drawable.logo,
                    paddingValues = insetPadding.asPaddingValues(),
                    actionUpClicked = {
                        backStack.removeDetail()
                    },
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass
                )
            }

            entry<NavStyleGuide>(metadata = SplitPaneScene.listPane()) {
                StyleGuideScreen(
                    actionUpClicked = { backStack.popMaster() },
                    paddingValues = insetPadding.asPaddingValues()
                )
            }

            entry<NavInfo>(metadata = SplitPaneScene.listPane()) {
                InfoScreen(
                    actionUpClicked = { backStack.popMaster() },
                    paddingValues = insetPadding.asPaddingValues(),
                    windowSizeClass = windowAdaptiveInfo.windowSizeClass
                )
            }
        }
    )
}