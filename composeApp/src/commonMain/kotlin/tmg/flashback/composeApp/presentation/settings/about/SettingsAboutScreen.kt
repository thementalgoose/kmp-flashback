package tmg.flashback.composeApp.presentation.settings.about

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
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.analytics.presentation.ScreenView
import tmg.flashback.composeApp.presentation.settings.PrefSection
import tmg.flashback.composeApp.presentation.settings.SettingLink
import tmg.flashback.composeApp.presentation.settings.Settings
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.header.HeaderAction

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
        openFeedback = viewModel::openFeedback,
        openChangelog = viewModel::openChangelog,
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
    openFeedback: () -> Unit,
    openChangelog: () -> Unit,
    actionUpClicked: () -> Unit,
    firstTimeSync: () -> Unit,
) {
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
        PrefSection(
            title = string.settings_header_about,
            content = {
                SettingLink(
                    item = Settings.About.AboutThisApp,
                    itemClicked = {
                        actionUpClicked()
                        navigateToAboutThisApp()
                    }
                )
                SettingLink(
                    item = Settings.About.Review,
                    itemClicked = { openReview() }
                )
                SettingLink(
                    item = Settings.About.Feedback,
                    itemClicked = { openFeedback() }
                )
                SettingLink(
                    item = Settings.About.Changelog,
                    itemClicked = { openChangelog() }
                )
            }
        )
        PrefSection(
            title = string.settings_pref_reset_title,
            content = {
                SettingLink(
                    item = Settings.About.FirstTimeSync,
                    itemClicked = { firstTimeSync() }
                )
            }
        )
        PrefSection(
            title = string.settings_header_device_info,
            content = {
                SettingLink(
                    item = Settings.About.BuildVersion,
                    itemClicked = { }
                )
                SettingLink(
                    item = Settings.About.DeviceInfo,
                    itemClicked = { }
                )
            }
        )
    }
}