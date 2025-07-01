package tmg.flashback.presentation.settings.weather

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.settings_header_weather
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.analytics.presentation.ScreenView
import tmg.flashback.presentation.settings.PrefSwitch
import tmg.flashback.presentation.settings.Settings
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.header.HeaderAction

@Composable
fun SettingsWeatherScreen(
    actionUpClicked: () -> Unit,
    insetPadding: PaddingValues,
    showBack: Boolean,
    viewModel: SettingsWeatherViewModel = koinViewModel()
) {
    ScreenView(screenName = "Settings - Weather")

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    SettingsWeatherScreen(
        insetPadding = insetPadding,
        showBack = showBack,
        uiState = uiState.value,
        actionUpClicked = actionUpClicked,
        temperatureMetricEnabled = viewModel::updateTemperatureMetric,
        windspeedMetricEnabled = viewModel::updateWindspeedMetric
    )
}

@Composable
private fun SettingsWeatherScreen(
    uiState: SettingsWeatherUiState,
    showBack: Boolean,
    insetPadding: PaddingValues,
    actionUpClicked: () -> Unit,
    temperatureMetricEnabled: (Boolean) -> Unit,
    windspeedMetricEnabled: (Boolean) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = insetPadding
    ) {
        item("header") {
            Header(
                text = stringResource(string.settings_header_weather),
                actionUpClicked = actionUpClicked,
                action = HeaderAction.BACK.takeIf { showBack }
            )
        }
        PrefSwitch(
            item = Settings.Weather.TemperatureUnit,
            itemClicked = { temperatureMetricEnabled(!uiState.temperatureMetrics) },
            isChecked = uiState.temperatureMetrics
        )
        PrefSwitch(
            item = Settings.Weather.WindSpeed,
            itemClicked = { windspeedMetricEnabled(!uiState.windSpeedMetrics) },
            isChecked = uiState.windSpeedMetrics
        )
    }
}