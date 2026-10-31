package tmg.flashback.composeApp.presentation.settings.notifications.news

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.settings_header_permissions
import flashback.presentation.localisation.generated.resources.settings_section_notifications_breaking_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.analytics.presentation.ScreenView
import tmg.flashback.composeApp.presentation.settings.PrefSection
import tmg.flashback.composeApp.presentation.settings.SettingLink
import tmg.flashback.composeApp.presentation.settings.SettingSwitch
import tmg.flashback.composeApp.presentation.settings.Settings
import tmg.flashback.feature.notifications.model.NotificationNews
import tmg.flashback.feature.notifications.model.NotificationNews.BREAKING_NEWS
import tmg.flashback.infrastructure.device.Device
import tmg.flashback.infrastructure.device.Platform
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.header.HeaderAction
import tmg.flashback.ui.permissions.PermissionState

@Composable
fun SettingsNotificationNewsScreen(
    actionUpClicked: () -> Unit,
    insetPadding: PaddingValues,
    showBack: Boolean,
    viewModel: SettingsNotificationNewsViewModel = koinViewModel(),
) {
    ScreenView(screenName = "Settings - Notifications News")

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val permissionState = viewModel.permissionState.collectAsState()

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        viewModel.refresh()
    }

    SettingsNotificationNewsScreen(
        insetPadding = insetPadding,
        showBack = showBack,
        uiState = uiState.value,
        actionUpClicked = actionUpClicked,
        notificationNewsClicked = viewModel::setNotificationNews,
        permissionState = permissionState.value,
        requestPermission = viewModel::requestPermissions,
        goToSettings = viewModel::goToSettings
    )
}

@Composable
private fun SettingsNotificationNewsScreen(
    uiState: SettingsNotificationNewsUiState,
    showBack: Boolean,
    insetPadding: PaddingValues,
    actionUpClicked: () -> Unit,
    notificationNewsClicked: (NotificationNews, Boolean) -> Unit,
    permissionState: PermissionState,
    requestPermission: () -> Unit,
    goToSettings: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = insetPadding
    ) {
        item("header") {
            Header(
                text = stringResource(string.settings_section_notifications_breaking_title),
                actionUpClicked = actionUpClicked,
                action = HeaderAction.BACK.takeIf { showBack }
            )
        }

        PrefSection(
            title = string.settings_header_permissions,
            content = {
                if (permissionState == PermissionState.NotGranted || permissionState == PermissionState.NotDetermined) {
                    SettingLink(
                        item = Settings.NotificationsBreaking.PermissionEnable,
                        itemClicked = { requestPermission() }
                    )
                } else {
                    SettingLink(
                        item = Settings.NotificationsBreaking.PermissionManage,
                        itemClicked = { goToSettings() }
                    )
                }
            }
        )

        if (Device.platform == Platform.IOS) {
            PrefSection(
                title = string.settings_section_notifications_breaking_title,
                content = {
                    SettingSwitch(
                        item = Settings.NotificationsBreaking.NewsStories,
                        itemClicked = {
                            notificationNewsClicked(BREAKING_NEWS, !uiState.enabled.contains(BREAKING_NEWS))
                        },
                        isChecked = uiState.enabled.contains(BREAKING_NEWS),
                        isEnabled = permissionState == PermissionState.Granted
                    )
                }
            )
        }
    }
}