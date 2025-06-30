package tmg.flashback.presentation.settings.widgets

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.settings_header_weather
import flashback.presentation.localisation.generated.resources.settings_header_widgets
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.presentation.settings.PrefSwitch
import tmg.flashback.presentation.settings.Settings
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.header.HeaderAction

@Composable
fun SettingsWidgetScreen(
    actionUpClicked: () -> Unit,
    insetPadding: PaddingValues,
    showBack: Boolean,
    viewModel: SettingsWidgetsViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    SettingsWidgetScreen(
        insetPadding = insetPadding,
        showBack = showBack,
        uiState = uiState.value,
        actionUpClicked = actionUpClicked,
        updateShowBackground = viewModel::updateShowBackground,
        updateDeeplinkToEvent = viewModel::updateDeeplinkToEvent,
        updateShowWeather = viewModel::updateShowWeather
    )
}

@Composable
private fun SettingsWidgetScreen(
    uiState: SettingsWidgetsUiState,
    showBack: Boolean,
    insetPadding: PaddingValues,
    actionUpClicked: () -> Unit,
    updateShowBackground: (Boolean) -> Unit,
    updateDeeplinkToEvent: (Boolean) -> Unit,
    updateShowWeather: (Boolean) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = insetPadding
    ) {
        item("header") {
            Header(
                text = stringResource(string.settings_header_widgets),
                actionUpClicked = actionUpClicked,
                action = HeaderAction.BACK.takeIf { showBack }
            )
        }
        PrefSwitch(
            item = Settings.Widgets.ShowBackground,
            itemClicked = { updateShowBackground(!uiState.showBackground) },
            isChecked = uiState.showBackground
        )
        PrefSwitch(
            item = Settings.Widgets.DeeplinkToEvent,
            itemClicked = { updateDeeplinkToEvent(!uiState.linkToEvent) },
            isChecked = uiState.linkToEvent
        )
        PrefSwitch(
            item = Settings.Widgets.ShowWeather,
            itemClicked = { updateShowWeather(!uiState.showWeather) },
            isChecked = uiState.showWeather
        )
    }
}