package tmg.flashback.presentation.settings.weather

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import tmg.flashback.feature.weekend.repositories.WeatherRepository

class SettingsWeatherViewModel(
    private val weatherRepository: WeatherRepository
): ViewModel() {

    private val _uiState: MutableStateFlow<SettingsWeatherUiState> = MutableStateFlow(
        SettingsWeatherUiState(
            temperatureMetrics = weatherRepository.weatherTemperatureMetric,
            windSpeedMetrics = weatherRepository.weatherWindspeedMetric
        ))
    val uiState: StateFlow<SettingsWeatherUiState> = _uiState

    fun refresh() {
        _uiState.update {
            SettingsWeatherUiState(
                temperatureMetrics = weatherRepository.weatherTemperatureMetric,
                windSpeedMetrics = weatherRepository.weatherWindspeedMetric
            )
        }
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