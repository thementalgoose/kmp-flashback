package tmg.flashback.presentation.settings

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.nav_settings
import flashback.presentation.localisation.generated.resources.settings_header_appearance
import flashback.presentation.localisation.generated.resources.settings_header_data
import flashback.presentation.localisation.generated.resources.settings_header_notifications
import flashback.presentation.localisation.generated.resources.settings_header_other
import flashback.presentation.localisation.generated.resources.settings_header_rss_feed
import flashback.presentation.localisation.generated.resources.settings_header_widgets
import flashback.presentation.localisation.generated.resources.settings_web_browser_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.header.HeaderAction

@Composable
internal fun AllSettingsScreen(
    actionUpClicked: () -> Unit,
    showMenu: Boolean,
    navigateTo: (SettingNavigation) -> Unit,
    viewModel: AllSettingsViewModel = koinViewModel(),
    insetPadding: PaddingValues,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    AllSettingsScreen(
        actionUpClicked = actionUpClicked,
        navigateTo = navigateTo,
        showMenu = showMenu,
        uiState = uiState.value,
        paddingValues = insetPadding
    )
}

@Composable
private fun AllSettingsScreen(
    actionUpClicked: () -> Unit,
    showMenu: Boolean,
    navigateTo: (SettingNavigation) -> Unit,
    uiState: AllSettingsUiState,
    paddingValues: PaddingValues,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = paddingValues
    ) {
        item("header") {
            Header(
                text = stringResource(string.nav_settings),
                actionUpClicked = actionUpClicked,
                action = HeaderAction.MENU.takeIf { showMenu }
            )
        }
        PrefHeader(string.settings_header_appearance)
        PrefCategory(
            item = Settings.DarkModeCategory,
            itemClicked = { navigateTo(SettingNavigation.DarkMode) }
        )
        if (uiState.isThemeSupported) {
            PrefCategory(
                item = Settings.ThemeCategory,
                itemClicked = { }
            )
        }
        PrefHeader(string.settings_header_data)
        PrefCategory(
            item = Settings.LayoutCategory,
            itemClicked = { }
        )
        PrefCategory(
            item = Settings.WeatherCategory,
            itemClicked = { }
        )
        if (uiState.isRssEnabled) {
            PrefHeader(string.settings_header_rss_feed)
            PrefCategory(
                item = Settings.RssCategory,
                itemClicked = { }
            )
        }
        PrefHeader(string.settings_web_browser_title)
        PrefCategory(
            item = Settings.WebBrowserCategory,
            itemClicked = { }
        )
        PrefHeader(string.settings_header_notifications)
        PrefCategory(
            item = Settings.NotificationsRaceCategory,
            itemClicked = { }
        )
        PrefCategory(
            item = Settings.NotificationsUpcomingCategory,
            itemClicked = { }
        )
        PrefCategory(
            item = Settings.NotificationsUpcomingNotice,
            itemClicked = { }
        )
        if (uiState.isWidgetsSupported) {
            PrefHeader(string.settings_header_widgets)
            PrefCategory(
                item = Settings.WidgetCategory,
                itemClicked = { }
            )
        }
        PrefHeader(string.settings_header_other)
        PrefCategory(
            item = Settings.PrivacyCategory,
            itemClicked = { }
        )
        PrefCategory(
            item = Settings.AboutCategory,
            itemClicked = { }
        )
    }
}