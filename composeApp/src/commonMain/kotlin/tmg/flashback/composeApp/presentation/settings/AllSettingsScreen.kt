package tmg.flashback.composeApp.presentation.settings

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
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.analytics.presentation.ScreenView
import tmg.flashback.infrastructure.device.Device
import tmg.flashback.infrastructure.device.Platform
import tmg.flashback.navigation.Screen
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.header.HeaderAction

@Composable
internal fun AllSettingsScreen(
    actionUpClicked: () -> Unit,
    showMenu: Boolean,
    navigateToSubScreen: (Screen) -> Unit,
    navigateTo: (Screen) -> Unit,
    insetPadding: PaddingValues,
    viewModel: AllSettingsViewModel = koinViewModel(),
) {
    ScreenView(screenName = "Settings")
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    AllSettingsScreen(
        actionUpClicked = actionUpClicked,
        navigateTo = navigateTo,
        navigateToSubScreen = navigateToSubScreen,
        showMenu = showMenu,
        uiState = uiState.value,
        paddingValues = insetPadding
    )
}

@Composable
private fun AllSettingsScreen(
    actionUpClicked: () -> Unit,
    showMenu: Boolean,
    navigateToSubScreen: (Screen) -> Unit,
    navigateTo: (Screen) -> Unit,
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
            itemClicked = { navigateToSubScreen(Screen.SettingsDarkMode) }
        )
        if (uiState.isThemeChangeSupported) {
            PrefCategory(
                item = Settings.ThemeCategory,
                itemClicked = { navigateToSubScreen(Screen.SettingsTheme) }
            )
        }
        PrefHeader(string.settings_header_data)
        PrefCategory(
            item = Settings.LayoutCategory,
            itemClicked = { navigateToSubScreen(Screen.SettingsLayout)  }
        )
        PrefCategory(
            item = Settings.WeatherCategory,
            itemClicked = { navigateToSubScreen(Screen.SettingsWeather)  }
        )
        if (uiState.isRssEnabled) {
            PrefHeader(string.settings_header_rss_feed)
            PrefCategory(
                item = Settings.RssCategory,
                itemClicked = { navigateToSubScreen(Screen.SettingsRssConfigure) }
            )
            if (uiState.isInAppBrowserSupported) {
                PrefCategory(
                    item = Settings.WebBrowserCategory,
                    itemClicked = { navigateToSubScreen(Screen.SettingsInAppBrowser)  }
                )
            }
        }
        if (Device.platform == Platform.Android || Device.platform == Platform.IOS) {
            PrefHeader(string.settings_header_notifications)
            PrefCategory(
                item = Settings.NotificationsResultCategory,
                itemClicked = { navigateToSubScreen(Screen.SettingsNotificationResults) },
            )
            PrefCategory(
                item = Settings.NotificationsUpcomingCategory,
                itemClicked = { navigateToSubScreen(Screen.SettingsNotificationUpcoming) },
            )
        }
        if (uiState.isWidgetsSupported) {
            PrefHeader(string.settings_header_widgets)
            PrefCategory(
                item = Settings.WidgetCategory,
                itemClicked = { navigateToSubScreen(Screen.SettingsWidgets) }
            )
        }
        PrefHeader(string.settings_header_other)
        PrefCategory(
            item = Settings.PrivacyCategory,
            itemClicked = { navigateToSubScreen(Screen.PrivacyPolicy) }
        )
        PrefCategory(
            item = Settings.PrivacyPolicy,
            itemClicked = { navigateToSubScreen(Screen.PrivacyPolicy) }
        )
        PrefCategory(
            item = Settings.AboutCategory,
            itemClicked = { navigateTo(Screen.About) }
        )
    }
}