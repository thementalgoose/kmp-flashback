package tmg.flashback.presentation.settings

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass.Companion.COMPACT
import tmg.flashback.presentation.settings.darkmode.SettingsDarkModeScreen
import tmg.flashback.presentation.settings.theme.SettingsThemeScreen
import tmg.flashback.ui.navigation.MasterDetailsPane
import tmg.flashback.ui.navigation.rememberMasterDetailPaneState


internal interface SettingNavigation {
    data object DarkMode: SettingNavigation
    data object Theme: SettingNavigation
    data object Layout: SettingNavigation
    data object Weather: SettingNavigation
    data object RssConfigure: SettingNavigation
    data object InAppBrowser: SettingNavigation
    data object NotificationResults: SettingNavigation
    data object NotificationUpcoming: SettingNavigation
    data object Privacy: SettingNavigation
    data object About: SettingNavigation
}

@Composable
fun AllSettingsGraph(
    insetPadding: PaddingValues,
    actionUpClicked: () -> Unit,
    windowSizeClass: WindowSizeClass,
) {
    val navigator = rememberMasterDetailPaneState<SettingNavigation>()

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
            }
        }
    )
}