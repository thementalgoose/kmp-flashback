package tmg.flashback.composeApp.presentation.settings.layout_race

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.settings_section_race_title
import kotlinx.datetime.TimeZone
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.analytics.presentation.ScreenView
import tmg.flashback.composeApp.presentation.settings.PrefSection
import tmg.flashback.composeApp.presentation.settings.SettingLink
import tmg.flashback.composeApp.presentation.settings.SettingSwitch
import tmg.flashback.composeApp.presentation.settings.Settings
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.header.HeaderAction

@Composable
fun SettingsLayoutRaceScreen(
    actionUpClicked: () -> Unit,
    insetPadding: PaddingValues,
    showBack: Boolean,
    viewModel: SettingsLayoutRaceViewModel = koinViewModel(),
) {
    ScreenView(screenName = "Settings - Layout Race")

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    SettingsLayoutRaceScreen(
        actionUpClicked = actionUpClicked,
        insetPadding = insetPadding,
        showBack = showBack,
        uiState = uiState.value,
        updateWeatherPref = viewModel::updateWeatherPref,
        temperatureMetricEnabled = viewModel::updateTemperatureMetric,
        windspeedMetricEnabled = viewModel::updateWindspeedMetric
    )
}

@Composable
private fun SettingsLayoutRaceScreen(
    uiState: SettingsLayoutRaceUiState,
    showBack: Boolean,
    insetPadding: PaddingValues,
    actionUpClicked: () -> Unit,
    updateWeatherPref: (Boolean) -> Unit,
    temperatureMetricEnabled: (Boolean) -> Unit,
    windspeedMetricEnabled: (Boolean) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = insetPadding
    ) {
        item("header") {
            Header(
                text = stringResource(string.settings_section_race_title),
                actionUpClicked = actionUpClicked,
                action = HeaderAction.BACK.takeIf { showBack }
            )
        }
        PrefSection(
            title = string.settings_section_race_title,
            content = {
                SettingLink(
                    item = Settings.LayoutRace.Timezone(TimeZone.currentSystemDefault().id),
                    itemClicked = { },
                )
                SettingSwitch(
                    item = Settings.LayoutRace.WeatherDetails,
                    isChecked = uiState.weatherDetails,
                    itemClicked = { updateWeatherPref(!uiState.weatherDetails) },
                )
                SettingSwitch(
                    item = Settings.LayoutRace.TemperatureUnit,
                    isChecked = uiState.temperatureMetrics,
                    itemClicked = { temperatureMetricEnabled(!uiState.temperatureMetrics) },
                )
                SettingSwitch(
                    item = Settings.LayoutRace.WindSpeed,
                    isChecked = uiState.windSpeedMetrics,
                    itemClicked = { windspeedMetricEnabled(!uiState.windSpeedMetrics) },
                )
            }
        )
    }
}