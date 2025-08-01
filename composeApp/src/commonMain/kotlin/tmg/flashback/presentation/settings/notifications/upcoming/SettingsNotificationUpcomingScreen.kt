package tmg.flashback.presentation.settings.notifications.upcoming

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.notification_onboarding_title_howlong
import flashback.presentation.localisation.generated.resources.settings_header_permissions
import flashback.presentation.localisation.generated.resources.settings_pref_schedule_exact_alarm_title
import flashback.presentation.localisation.generated.resources.settings_section_notifications_upcoming_description
import flashback.presentation.localisation.generated.resources.settings_section_notifications_upcoming_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.analytics.presentation.ScreenView
import tmg.flashback.feature.notifications.model.NotificationReminder
import tmg.flashback.feature.notifications.model.NotificationReminder.MINUTES_15
import tmg.flashback.feature.notifications.model.NotificationReminder.MINUTES_30
import tmg.flashback.feature.notifications.model.NotificationReminder.MINUTES_60
import tmg.flashback.feature.notifications.model.NotificationUpcoming
import tmg.flashback.feature.notifications.model.NotificationUpcoming.FREE_PRACTICE
import tmg.flashback.feature.notifications.model.NotificationUpcoming.QUALIFYING
import tmg.flashback.feature.notifications.model.NotificationUpcoming.RACE
import tmg.flashback.feature.notifications.model.NotificationUpcoming.SPRINT
import tmg.flashback.feature.notifications.model.NotificationUpcoming.SPRINT_QUALIFYING
import tmg.flashback.infrastructure.device.Device
import tmg.flashback.infrastructure.device.Platform
import tmg.flashback.presentation.settings.PrefHeader
import tmg.flashback.presentation.settings.PrefLink
import tmg.flashback.presentation.settings.PrefRadio
import tmg.flashback.presentation.settings.PrefSwitch
import tmg.flashback.presentation.settings.Settings
import tmg.flashback.presentation.settings.notifications.ManageInSettings
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.header.HeaderAction
import tmg.flashback.ui.permissions.PermissionState

@Composable
fun SettingsNotificationUpcomingScreen(
    actionUpClicked: () -> Unit,
    insetPadding: PaddingValues,
    showBack: Boolean,
    viewModel: SettingsNotificationUpcomingViewModel = koinViewModel()
) {
    ScreenView(screenName = "Settings - Notifications Upcoming")

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val permissionState = viewModel.permissionState.collectAsState()

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        viewModel.refresh()
    }

    SettingsNotificationUpcomingScreen(
        insetPadding = insetPadding,
        showBack = showBack,
        uiState = uiState.value,
        actionUpClicked = actionUpClicked,
        notificationReminderClicked = viewModel::notificationReminderClicked,
        notificationUpcomingClicked = viewModel::setNotificationUpcoming,
        permissionState = permissionState.value,
        requestPermission = viewModel::requestPermissions,
        goToSettings = viewModel::goToSettings,
        goToAlarmSettings = viewModel::goToAlarmSettings
    )
}

@Composable
private fun SettingsNotificationUpcomingScreen(
    uiState: SettingsNotificationUpcomingUiState,
    showBack: Boolean,
    insetPadding: PaddingValues,
    actionUpClicked: () -> Unit,
    notificationReminderClicked: (NotificationReminder) -> Unit,
    notificationUpcomingClicked: (NotificationUpcoming, Boolean) -> Unit,
    permissionState: PermissionState,
    requestPermission: () -> Unit,
    goToSettings: () -> Unit,
    goToAlarmSettings: () -> Unit
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

        PrefHeader(string.settings_header_permissions)
        if (permissionState == PermissionState.NotGranted || permissionState == PermissionState.NotDetermined) {
            PrefLink(
                item = Settings.NotificationsUpcoming.PermissionEnable,
                itemClicked = { requestPermission() }
            )
        } else {
            PrefLink(
                item = Settings.NotificationsUpcoming.PermissionManage,
                itemClicked = { goToSettings() }
            )
        }

        if (Device.platform == Platform.IOS) {
            PrefHeader(string.settings_section_notifications_upcoming_description)
            PrefSwitch(
                item = Settings.NotificationsUpcoming.FreePractice,
                itemClicked = {
                    notificationUpcomingClicked(
                        FREE_PRACTICE,
                        !uiState.enabled.contains(FREE_PRACTICE)
                    )
                },
                isChecked = uiState.enabled.contains(FREE_PRACTICE),
                isEnabled = Device.platform == Platform.IOS
            )
            PrefSwitch(
                item = Settings.NotificationsUpcoming.SprintQualifying,
                itemClicked = {
                    notificationUpcomingClicked(
                        SPRINT_QUALIFYING,
                        !uiState.enabled.contains(SPRINT_QUALIFYING)
                    )
                },
                isChecked = uiState.enabled.contains(SPRINT_QUALIFYING),
                isEnabled = Device.platform == Platform.IOS
            )
            PrefSwitch(
                item = Settings.NotificationsUpcoming.SprintRace,
                itemClicked = {
                    notificationUpcomingClicked(SPRINT, !uiState.enabled.contains(SPRINT))
                },
                isChecked = uiState.enabled.contains(SPRINT),
                isEnabled = Device.platform == Platform.IOS
            )
            PrefSwitch(
                item = Settings.NotificationsUpcoming.Qualifying,
                itemClicked = {
                    notificationUpcomingClicked(QUALIFYING, !uiState.enabled.contains(QUALIFYING))
                },
                isChecked = uiState.enabled.contains(QUALIFYING),
                isEnabled = Device.platform == Platform.IOS
            )
            PrefSwitch(
                item = Settings.NotificationsUpcoming.Race,
                itemClicked = {
                    notificationUpcomingClicked(RACE, !uiState.enabled.contains(RACE))
                },
                isChecked = uiState.enabled.contains(RACE),
                isEnabled = Device.platform == Platform.IOS
            )
        }

        if (Device.platform == Platform.Android) {
            PrefHeader(string.settings_pref_schedule_exact_alarm_title)
            PrefLink(
                item = Settings.NotificationsNotice.ExactAlarm,
                itemClicked = { goToAlarmSettings() }
            )
        }
        PrefHeader(string.notification_onboarding_title_howlong)
        PrefRadio(
            item = Settings.NotificationsNotice.Minutes15,
            itemClicked = { notificationReminderClicked(MINUTES_15) },
            isChecked = uiState.reminder == MINUTES_15,
            isEnabled = uiState.reminderEnabled
        )
        PrefRadio(
            item = Settings.NotificationsNotice.Minutes30,
            itemClicked = { notificationReminderClicked(MINUTES_30) },
            isChecked = uiState.reminder == MINUTES_30,
            isEnabled = uiState.reminderEnabled
        )
        PrefRadio(
            item = Settings.NotificationsNotice.Minutes60,
            itemClicked = { notificationReminderClicked(MINUTES_60) },
            isChecked = uiState.reminder == MINUTES_60,
            isEnabled = uiState.reminderEnabled
        )
    }
}