package tmg.flashback.composeApp.presentation.settings.layout_race

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import tmg.flashback.feature.weekend.repositories.WeatherRepository
import tmg.flashback.feature.weekend.repositories.WeekendRepository

class SettingsLayoutRaceViewModel(
    private val weekendRepository: WeekendRepository,
    private val weatherRepository: WeatherRepository,
): ViewModel() {

    private val _uiState: MutableStateFlow<SettingsLayoutRaceUiState> = MutableStateFlow(
        SettingsLayoutRaceUiState(
            weatherDetails = weekendRepository.weatherDetails,
            temperatureMetrics = weatherRepository.weatherTemperatureMetric,
            windSpeedMetrics = weatherRepository.weatherWindspeedMetric
        ))
    val uiState: StateFlow<SettingsLayoutRaceUiState> = _uiState

    fun refresh() {
        _uiState.update {
            SettingsLayoutRaceUiState(
                weatherDetails = weekendRepository.weatherDetails,
                temperatureMetrics = weatherRepository.weatherTemperatureMetric,
                windSpeedMetrics = weatherRepository.weatherWindspeedMetric
            )
        }
    }

    fun updateWeatherPref(enabled: Boolean) {
        weekendRepository.weatherDetails = enabled
        refresh()
    }

    fun updateTemperatureMetric(enabled: Boolean) {
        weatherRepository.weatherTemperatureMetric = enabled
        refresh()
    }

    fun updateWindspeedMetric(enabled: Boolean) {
        weatherRepository.weatherWindspeedMetric = enabled
        refresh()
    }
}