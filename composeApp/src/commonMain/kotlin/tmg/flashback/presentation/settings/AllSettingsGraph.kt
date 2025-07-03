package tmg.flashback.presentation.settings

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass.Companion.COMPACT
import tmg.flashback.feature.rss.presentation.configure.RssConfigureScreen
import tmg.flashback.presentation.settings.about.SettingsAboutScreen
import tmg.flashback.presentation.settings.browser.SettingsBrowserScreen
import tmg.flashback.presentation.settings.darkmode.SettingsDarkModeScreen
import tmg.flashback.presentation.settings.layout.SettingsLayoutScreen
import tmg.flashback.presentation.settings.notifications.results.SettingsNotificationResultsScreen
import tmg.flashback.presentation.settings.notifications.upcoming.SettingsNotificationUpcomingScreen
import tmg.flashback.presentation.settings.privacy.SettingsPrivacyScreen
import tmg.flashback.presentation.settings.theme.SettingsThemeScreen
import tmg.flashback.presentation.settings.weather.SettingsWeatherScreen
import tmg.flashback.presentation.settings.widgets.SettingsWidgetScreen
import tmg.flashback.ui.navigation.MasterDetailPaneState
import tmg.flashback.ui.navigation.MasterDetailsPane

interface SettingNavigation {
    data object DarkMode: SettingNavigation
    data object Theme: SettingNavigation
    data object Layout: SettingNavigation
    data object Weather: SettingNavigation
    data object RssConfigure: SettingNavigation
    data object InAppBrowser: SettingNavigation
    data object NotificationResults: SettingNavigation
    data object NotificationUpcoming: SettingNavigation
    data object Widgets: SettingNavigation
    data object Privacy: SettingNavigation
    data object About: SettingNavigation
}

@Composable
fun AllSettingsGraph(
    insetPadding: PaddingValues,
    actionUpClicked: () -> Unit,
    navigator: MasterDetailPaneState<SettingNavigation>,
    windowSizeClass: WindowSizeClass,
) {
    MasterDetailsPane(
        navigator = navigator,
        windowSizeClass = windowSizeClass,
        master = {
            AllSettingsScreen(
                showMenu = windowSizeClass.windowWidthSizeClass == COMPACT,
                navigateTo = { navigator.navigateTo(it) },
                actionUpClicked = actionUpClicked,
                insetPadding = insetPadding
            )
        },
        detailsActionUpClicked = {
            navigator.clear()
        },
        details = { model, actionUpClicked ->
            when (model) {
                is SettingNavigation.DarkMode -> SettingsDarkModeScreen(
                    actionUpClicked = { navigator.clear() },
                    insetPadding = insetPadding,
                    showBack = windowSizeClass.windowWidthSizeClass == COMPACT
                )
                is SettingNavigation.Theme -> SettingsThemeScreen(
                    actionUpClicked = { navigator.clear() },
                    insetPadding = insetPadding,
                    showBack = windowSizeClass.windowWidthSizeClass == COMPACT
                )
                is SettingNavigation.Layout -> SettingsLayoutScreen(
                    actionUpClicked = { navigator.clear() },
                    insetPadding = insetPadding,
                    showBack = windowSizeClass.windowWidthSizeClass == COMPACT
                )
                is SettingNavigation.Weather -> SettingsWeatherScreen(
                    actionUpClicked = { navigator.clear() },
                    insetPadding = insetPadding,
                    showBack = windowSizeClass.windowWidthSizeClass == COMPACT
                )
                is SettingNavigation.InAppBrowser -> SettingsBrowserScreen(
                    actionUpClicked = { navigator.clear() },
                    insetPadding = insetPadding,
                    showBack = windowSizeClass.windowWidthSizeClass == COMPACT
                )
                is SettingNavigation.RssConfigure -> RssConfigureScreen(
                    actionUpClicked = { navigator.clear() },
                    insetPadding = insetPadding,
                    showBack = windowSizeClass.windowWidthSizeClass == COMPACT
                )
                is SettingNavigation.NotificationResults -> SettingsNotificationResultsScreen(
                    actionUpClicked = { navigator.clear() },
                    insetPadding = insetPadding,
                    showBack = windowSizeClass.windowWidthSizeClass == COMPACT
                )
                is SettingNavigation.NotificationUpcoming -> SettingsNotificationUpcomingScreen(
                    actionUpClicked = { navigator.clear() },
                    insetPadding = insetPadding,
                    showBack = windowSizeClass.windowWidthSizeClass == COMPACT
                )
                is SettingNavigation.Widgets -> SettingsWidgetScreen(
                    actionUpClicked = { navigator.clear() },
                    insetPadding = insetPadding,
                    showBack = windowSizeClass.windowWidthSizeClass == COMPACT
                )
                is SettingNavigation.Privacy -> SettingsPrivacyScreen(
                    actionUpClicked = { navigator.clear() },
                    insetPadding = insetPadding,
                    showBack = windowSizeClass.windowWidthSizeClass == COMPACT
                )
                is SettingNavigation.About -> SettingsAboutScreen(
                    actionUpClicked = { navigator.clear() },
                    insetPadding = insetPadding,
                    showBack = windowSizeClass.windowWidthSizeClass == COMPACT
                )
            }
        }
    )
}