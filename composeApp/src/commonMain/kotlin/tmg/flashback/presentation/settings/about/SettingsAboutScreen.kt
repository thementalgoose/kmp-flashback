package tmg.flashback.presentation.settings.about

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.settings_header_about
import flashback.presentation.localisation.generated.resources.settings_header_device_info
import flashback.presentation.localisation.generated.resources.settings_pref_reset_title
import flashback.presentation.localisation.generated.resources.settings_restart_app_required
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.analytics.presentation.ScreenView
import tmg.flashback.presentation.settings.PrefHeader
import tmg.flashback.presentation.settings.PrefLink
import tmg.flashback.presentation.settings.Settings
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.header.HeaderAction
import tmg.flashback.ui.toast.Duration
import tmg.flashback.ui.toast.rememberToastManager

@Composable
fun SettingsAboutScreen(
    actionUpClicked: () -> Unit,
    navigateToAboutThisApp: () -> Unit,
    insetPadding: PaddingValues,
    showBack: Boolean,
    viewModel: SettingsAboutViewModel = koinViewModel()
) {
    ScreenView(screenName = "Settings - About")

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    SettingsAboutScreen(
        navigateToAboutThisApp = navigateToAboutThisApp,
        actionUpClicked = actionUpClicked,
        insetPadding = insetPadding,
        showBack = showBack,
        openReview = viewModel::openReview,
        firstTimeSync = viewModel::firstTimeSync,
        uiState = uiState.value,
    )
}

@Composable
private fun SettingsAboutScreen(
    uiState: SettingsAboutUiState,
    navigateToAboutThisApp: () -> Unit,
    showBack: Boolean,
    insetPadding: PaddingValues,
    openReview: () -> Unit,
    actionUpClicked: () -> Unit,
    firstTimeSync: () -> Unit,
) {
    val toastManager = rememberToastManager()
    val message = stringResource(string.settings_restart_app_required)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = insetPadding
    ) {
        item("header") {
            Header(
                text = stringResource(string.settings_header_about),
                actionUpClicked = actionUpClicked,
                action = HeaderAction.BACK.takeIf { showBack }
            )
        }
        PrefHeader(string.settings_header_about)
        PrefLink(
            item = Settings.About.AboutThisApp,
            itemClicked = {
                actionUpClicked()
                navigateToAboutThisApp()
            }
        )
        PrefLink(
            item = Settings.About.Review,
            itemClicked = { openReview() }
        )
        PrefLink(
            item = Settings.About.BuildVersion,
            itemClicked = { }
        )
        PrefHeader(string.settings_pref_reset_title)
        PrefLink(
            item = Settings.About.FirstTimeSync,
            itemClicked = {
                toastManager.showMessage(message, Duration.Long)
                firstTimeSync()
            }
        )
        PrefHeader(string.settings_header_device_info)
        PrefLink(
            item = Settings.About.DeviceInfo,
            itemClicked = { }
        )
    }
}