package tmg.flashback.presentation.settings.privacy

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.settings_header_device_info
import flashback.presentation.localisation.generated.resources.settings_header_legal
import flashback.presentation.localisation.generated.resources.settings_restart_app_required
import flashback.presentation.localisation.generated.resources.settings_section_privacy_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.analytics.presentation.ScreenView
import tmg.flashback.presentation.settings.PrefHeader
import tmg.flashback.presentation.settings.PrefLink
import tmg.flashback.presentation.settings.PrefSwitch
import tmg.flashback.presentation.settings.Setting.Pref
import tmg.flashback.presentation.settings.Settings
import tmg.flashback.style.AppTheme
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.header.HeaderAction
import tmg.flashback.ui.toasts.ToastManager.showMessage

@Composable
fun SettingsPrivacyScreen(
    actionUpClicked: () -> Unit,
    insetPadding: PaddingValues,
    showBack: Boolean,
    viewModel: SettingsPrivacyViewModel = koinViewModel()
) {
    ScreenView(screenName = "Settings - Privacy")

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    SettingsPrivacyScreen(
        insetPadding = insetPadding,
        showBack = showBack,
        uiState = uiState.value,
        actionUpClicked = actionUpClicked,
        analyticsEnabled = viewModel::updateAnalyticsEnabled,
        crashReportingEnabled = viewModel::updateCrashlyticsEnabled
    )
}

@Composable
private fun SettingsPrivacyScreen(
    uiState: SettingsPrivacyUiState,
    showBack: Boolean,
    insetPadding: PaddingValues,
    actionUpClicked: () -> Unit,
    crashReportingEnabled: (Boolean) -> Unit,
    analyticsEnabled: (Boolean) -> Unit,
) {
    val restartMessage = stringResource(string.settings_restart_app_required)
    val snackBarBackground = AppTheme.colors.primaryContainer
    val snackBarForeground = AppTheme.colors.onPrimaryContainer

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = insetPadding
    ) {
        item("header") {
            Header(
                text = stringResource(string.settings_section_privacy_title),
                actionUpClicked = actionUpClicked,
                action = HeaderAction.BACK.takeIf { showBack }
            )
        }
        PrefHeader(string.settings_header_device_info)
        PrefLink(
            item = Settings.Privacy.Disclaimer,
            itemClicked = { }
        )
        PrefSwitch(
            item = Settings.Privacy.CrashReporting,
            isChecked = uiState.crashReportingEnabled,
            itemClicked = {
                crashReportingEnabled(!uiState.crashReportingEnabled)
                showMessage(
                    message = restartMessage,
                    backgroundColor = snackBarBackground,
                    textColor = snackBarForeground
                )
            }
        )
        PrefSwitch(
            item = Settings.Privacy.Analytics,
            isChecked = uiState.analyticsEnabled,
            itemClicked = {
                analyticsEnabled(!uiState.analyticsEnabled)
                showMessage(
                    message = restartMessage,
                    backgroundColor = snackBarBackground,
                    textColor = snackBarForeground
                )
            }
        )
    }
}