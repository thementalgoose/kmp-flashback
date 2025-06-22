package tmg.flashback.presentation.settings

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.settings_header_appearance
import flashback.presentation.localisation.generated.resources.settings_header_data
import flashback.presentation.localisation.generated.resources.settings_header_notifications
import flashback.presentation.localisation.generated.resources.settings_header_other
import flashback.presentation.localisation.generated.resources.settings_header_rss_feed
import flashback.presentation.localisation.generated.resources.settings_header_widgets
import flashback.presentation.localisation.generated.resources.settings_web_browser_title
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsScreen(
    viewModel: AllSettingsViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    SettingsScreen(
        uiState = uiState.value
    )
}

@Composable
private fun SettingsScreen(
    uiState: AllSettingsUiState
) {
    LazyColumn(
        contentPadding = WindowInsets.safeDrawing.asPaddingValues()
    ) {
        PrefHeader(string.settings_header_appearance)
        PrefCategory(
            item = Settings.DarkModeCategory,
            itemClicked = { }
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