package tmg.flashback.composeApp.presentation.settings

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND
import tmg.flashback.navigation.Screen
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
import tmg.flashback.navigation.NavAbout
import tmg.flashback.navigation.NavPrivacyPolicy
import tmg.flashback.navigation.NavSettingsDarkMode
import tmg.flashback.navigation.NavSettingsInAppBrowser
import tmg.flashback.navigation.NavSettingsLayoutHome
import tmg.flashback.navigation.NavSettingsNotificationResults
import tmg.flashback.navigation.NavSettingsNotificationUpcoming
import tmg.flashback.navigation.NavSettingsPrivacy
import tmg.flashback.navigation.NavSettingsRssConfigure
import tmg.flashback.navigation.NavSettingsTheme
import tmg.flashback.navigation.NavSettingsLayoutRace
import tmg.flashback.navigation.NavSettingsWidgets
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.header.HeaderAction
import tmg.flashback.ui.navigation.appBarMaximumHeight

@Composable
internal fun AllSettingsScreen(
    navigateToSubScreen: (Screen) -> Unit,
    navigateTo: (Screen) -> Unit,
    paddingValues: PaddingValues,
    windowSizeClass: WindowSizeClass,
    viewModel: AllSettingsViewModel = koinViewModel(),
) {
    ScreenView(screenName = "Settings")
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val insets = when (windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND)) {
        true -> paddingValues
        false -> {
            val direction = LocalLayoutDirection.current
            PaddingValues(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding() + appBarMaximumHeight,
                start = paddingValues.calculateStartPadding(direction),
                end = paddingValues.calculateEndPadding(direction)
            )
        }
    }

    AllSettingsScreen(
        navigateTo = navigateTo,
        navigateToSubScreen = navigateToSubScreen,
        uiState = uiState.value,
        paddingValues = insets
    )
}

@Composable
private fun AllSettingsScreen(
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
                action = null,
            )
        }
        PrefSection(
            title = string.settings_header_appearance,
            content = {
                SettingCategory(
                    item = Settings.DarkModeCategory,
                    itemClicked = { navigateToSubScreen(NavSettingsDarkMode) }
                )
                if (uiState.isThemeChangeSupported) {
                    SettingCategory(
                        item = Settings.ThemeCategory,
                        itemClicked = { navigateToSubScreen(NavSettingsTheme) }
                    )
                }
            }
        )

        PrefSection(
            title = string.settings_header_data,
            content = {
                SettingCategory(
                    item = Settings.LayoutHomeCategory,
                    itemClicked = { navigateToSubScreen(NavSettingsLayoutHome)  }
                )
                SettingCategory(
                    item = Settings.LayoutRaceCategory,
                    itemClicked = { navigateToSubScreen(NavSettingsLayoutRace)  }
                )
            }
        )

        if (uiState.isRssEnabled) {
            PrefSection(
                title = string.settings_header_rss_feed,
                content = {
                    SettingCategory(
                        item = Settings.RssCategory,
                        itemClicked = { navigateToSubScreen(NavSettingsRssConfigure) }
                    )
                    if (uiState.isInAppBrowserSupported) {
                        SettingCategory(
                            item = Settings.WebBrowserCategory,
                            itemClicked = { navigateToSubScreen(NavSettingsInAppBrowser) }
                        )
                    }
                }
            )
        }
        if (Device.platform == Platform.Android || Device.platform == Platform.IOS) {
            PrefSection(
                title = string.settings_header_notifications,
                content = {
                    SettingCategory(
                        item = Settings.NotificationsResultCategory,
                        itemClicked = { navigateToSubScreen(NavSettingsNotificationResults) },
                    )
                    SettingCategory(
                        item = Settings.NotificationsUpcomingCategory,
                        itemClicked = { navigateToSubScreen(NavSettingsNotificationUpcoming) },
                    )
                }
            )
        }
        if (uiState.isWidgetsSupported) {
            PrefSection(
                title = string.settings_header_widgets,
                content = {
                    SettingCategory(
                        item = Settings.WidgetCategory,
                        itemClicked = { navigateToSubScreen(NavSettingsWidgets) }
                    )
                }
            )
        }
        PrefSection(
            title = string.settings_header_other,
            content = {
                SettingCategory(
                    item = Settings.PrivacyCategory,
                    itemClicked = { navigateToSubScreen(NavSettingsPrivacy) }
                )
                SettingCategory(
                    item = Settings.PrivacyPolicy,
                    itemClicked = { navigateToSubScreen(NavPrivacyPolicy) }
                )
                SettingCategory(
                    item = Settings.AboutCategory,
                    itemClicked = { navigateTo(NavAbout) }
                )
            }
        )
    }
}