package tmg.flashback.feature.season.presentation.shared.device_time

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.TimeZone
import tmg.flashback.feature.season.repositories.CalendarRepository

class DeviceTimeViewModel(
    private val calendarRepository: CalendarRepository
): ViewModel() {

    private val _uiState: MutableStateFlow<DeviceTimeUiState> = MutableStateFlow(
        DeviceTimeUiState(
            show = !calendarRepository.seenDatePrompt,
            timezone = TimeZone.currentSystemDefault().id
        )
    )
    val uiState: StateFlow<DeviceTimeUiState> = _uiState

    fun acknowledge() {
        calendarRepository.seenDatePrompt = true
        _uiState.update { it.copy(show = false) }
    }
}