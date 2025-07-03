package tmg.flashback.presentation.settings.notifications.results

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.settings_header_permissions
import flashback.presentation.localisation.generated.resources.settings_section_notifications_results_description
import flashback.presentation.localisation.generated.resources.settings_section_notifications_results_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.analytics.presentation.ScreenView
import tmg.flashback.feature.notifications.model.NotificationResultsAvailable
import tmg.flashback.feature.notifications.model.NotificationResultsAvailable.QUALIFYING
import tmg.flashback.feature.notifications.model.NotificationResultsAvailable.RACE
import tmg.flashback.feature.notifications.model.NotificationResultsAvailable.SPRINT
import tmg.flashback.feature.notifications.model.NotificationResultsAvailable.SPRINT_QUALIFYING
import tmg.flashback.presentation.settings.PrefHeader
import tmg.flashback.presentation.settings.PrefLink
import tmg.flashback.presentation.settings.PrefSwitch
import tmg.flashback.presentation.settings.Settings
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.header.HeaderAction

@Composable
fun SettingsNotificationResultsScreen(
    actionUpClicked: () -> Unit,
    insetPadding: PaddingValues,
    showBack: Boolean,
    viewModel: SettingsNotificationResultsViewModel = koinViewModel()
) {
    ScreenView(screenName = "Settings - Notifications Results")

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    SettingsNotificationResultsScreen(
        insetPadding = insetPadding,
        showBack = showBack,
        uiState = uiState.value,
        actionUpClicked = actionUpClicked,
        notificationResultsClicked = viewModel::setNotificationResult
    )
}

@Composable
private fun SettingsNotificationResultsScreen(
    uiState: SettingsNotificationResultsUiState,
    showBack: Boolean,
    insetPadding: PaddingValues,
    actionUpClicked: () -> Unit,
    notificationResultsClicked: (NotificationResultsAvailable, Boolean) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = insetPadding
    ) {
        item("header") {
            Header(
                text = stringResource(string.settings_section_notifications_results_title),
                actionUpClicked = actionUpClicked,
                action = HeaderAction.BACK.takeIf { showBack }
            )
        }
        PrefHeader(string.settings_header_permissions)
        PrefLink(
            item = Settings.NotificationsResults.Enable,
            itemClicked = { }
        )
        PrefHeader(string.settings_section_notifications_results_description)
        PrefSwitch(
            item = Settings.NotificationsResults.SprintQualifying,
            itemClicked = {
                notificationResultsClicked(SPRINT_QUALIFYING, !uiState.enabled.contains(SPRINT_QUALIFYING))
            },
            isChecked = uiState.enabled.contains(SPRINT_QUALIFYING)
        )
        PrefSwitch(
            item = Settings.NotificationsResults.SprintRace,
            itemClicked = {
                notificationResultsClicked(SPRINT, !uiState.enabled.contains(SPRINT))
            },
            isChecked = uiState.enabled.contains(SPRINT)
        )
        PrefSwitch(
            item = Settings.NotificationsResults.Qualifying,
            itemClicked = {
                notificationResultsClicked(QUALIFYING, !uiState.enabled.contains(QUALIFYING))
            },
            isChecked = uiState.enabled.contains(QUALIFYING)
        )
        PrefSwitch(
            item = Settings.NotificationsUpcoming.Race,
            itemClicked = {
                notificationResultsClicked(RACE, !uiState.enabled.contains(RACE))
            },
            isChecked = uiState.enabled.contains(RACE)
        )
    }
}