package tmg.flashback.presentation.settings.browser

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.settings_header_in_app_browser
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.analytics.presentation.ScreenView
import tmg.flashback.presentation.settings.PrefSwitch
import tmg.flashback.presentation.settings.Settings
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.header.HeaderAction

@Composable
fun SettingsBrowserScreen(
    actionUpClicked: () -> Unit,
    insetPadding: PaddingValues,
    showBack: Boolean,
    viewModel: SettingsBrowserViewModel = koinViewModel()
) {
    ScreenView(screenName = "Settings - Web")

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    SettingsBrowserScreen(
        actionUpClicked = actionUpClicked,
        insetPadding = insetPadding,
        showBack = showBack,
        uiState = uiState.value,
        updateEnabled = viewModel::updateEnabled,
        updateEnableJavascript = viewModel::updateEnableJavascript
    )
}

@Composable
private fun SettingsBrowserScreen(
    uiState: SettingsBrowserUiState,
    showBack: Boolean,
    insetPadding: PaddingValues,
    actionUpClicked: () -> Unit,
    updateEnabled: (Boolean) -> Unit,
    updateEnableJavascript: (Boolean) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = insetPadding
    ) {
        item("header") {
            Header(
                text = stringResource(string.settings_header_in_app_browser),
                actionUpClicked = actionUpClicked,
                action = HeaderAction.BACK.takeIf { showBack }
            )
        }
        PrefSwitch(
            item = Settings.WebBrowser.Enabled,
            itemClicked = { updateEnabled(!uiState.enabled) },
            isChecked = uiState.enabled
        )
        PrefSwitch(
            item = Settings.WebBrowser.EnableJavascript,
            itemClicked = { updateEnableJavascript(!uiState.enableJavascript) },
            isChecked = uiState.enableJavascript,
            isEnabled = uiState.enabled
        )
    }
}