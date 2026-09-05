package tmg.flashback.composeApp.presentation.settings.layout_race

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import tmg.flashback.feature.weekend.repositories.WeekendRepository

class SettingsLayoutRaceViewModel(
    private val weekendRepository: WeekendRepository
): ViewModel() {

    private val _uiState: MutableStateFlow<SettingsLayoutRaceUiState> = MutableStateFlow(
        SettingsLayoutRaceUiState(
            weatherDetails = weekendRepository.weatherDetails,
        ))
    val uiState: StateFlow<SettingsLayoutRaceUiState> = _uiState

    fun refresh() {
        _uiState.update {
            SettingsLayoutRaceUiState(
                weatherDetails = weekendRepository.weatherDetails
            )
        }
    }

    fun updateWeatherPref(enabled: Boolean) {
        weekendRepository.weatherDetails = enabled
        refresh()
    }
}