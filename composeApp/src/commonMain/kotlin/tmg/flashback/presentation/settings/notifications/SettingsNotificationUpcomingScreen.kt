package tmg.flashback.presentation.settings.notifications

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.notification_onboarding_title_howlong
import flashback.presentation.localisation.generated.resources.settings_header_device_info
import flashback.presentation.localisation.generated.resources.settings_header_legal
import flashback.presentation.localisation.generated.resources.settings_section_notifications_upcoming_description
import flashback.presentation.localisation.generated.resources.settings_section_notifications_upcoming_title
import flashback.presentation.localisation.generated.resources.settings_section_privacy_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.analytics.presentation.ScreenView
import tmg.flashback.feature.notifications.model.NotificationReminder
import tmg.flashback.feature.notifications.model.NotificationReminder.MINUTES_15
import tmg.flashback.feature.notifications.model.NotificationReminder.MINUTES_30
import tmg.flashback.feature.notifications.model.NotificationReminder.MINUTES_60
import tmg.flashback.presentation.settings.PrefHeader
import tmg.flashback.presentation.settings.PrefLink
import tmg.flashback.presentation.settings.PrefRadio
import tmg.flashback.presentation.settings.PrefSwitch
import tmg.flashback.presentation.settings.Settings
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.header.HeaderAction

@Composable
fun SettingsNotificationUpcomingScreen(
    actionUpClicked: () -> Unit,
    insetPadding: PaddingValues,
    showBack: Boolean,
    viewModel: SettingsNotificationUpcomingViewModel = koinViewModel()
) {
    ScreenView(screenName = "Settings - Notifications Upcoming")

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    SettingsNotificationUpcomingScreen(
        insetPadding = insetPadding,
        showBack = showBack,
        uiState = uiState.value,
        actionUpClicked = actionUpClicked,
        notificationReminderClicked = viewModel::notificationReminderClicked,
    )
}

@Composable
private fun SettingsNotificationUpcomingScreen(
    uiState: SettingsNotificationUpcomingUiState,
    showBack: Boolean,
    insetPadding: PaddingValues,
    actionUpClicked: () -> Unit,
    notificationReminderClicked: (NotificationReminder) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = insetPadding
    ) {
        item("header") {
            Header(
                text = stringResource(string.settings_section_notifications_upcoming_title),
                actionUpClicked = actionUpClicked,
                action = HeaderAction.BACK.takeIf { showBack }
            )
        }
        PrefHeader(string.notification_onboarding_title_howlong)
        PrefRadio(
            item = Settings.NotificationsNotice.Minutes15,
            itemClicked = { notificationReminderClicked(MINUTES_15) },
            isChecked = uiState.reminder == MINUTES_15
        )
        PrefRadio(
            item = Settings.NotificationsNotice.Minutes30,
            itemClicked = { notificationReminderClicked(MINUTES_30) },
            isChecked = uiState.reminder == MINUTES_30
        )
        PrefRadio(
            item = Settings.NotificationsNotice.Minutes60,
            itemClicked = { notificationReminderClicked(MINUTES_60) },
            isChecked = uiState.reminder == MINUTES_60
        )
    }
}