package tmg.flashback.feature.season.presentation.shared.device_time

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import tmg.flashback.feature.season.repositories.CalendarRepository

class DeviceTimeViewModel(
    private val calendarRepository: CalendarRepository
): ViewModel() {

    private val _uiState: MutableStateFlow<Boolean> = MutableStateFlow(calendarRepository.seenDatePrompt)
    val uiState: StateFlow<Boolean> = _uiState

    fun acknowledge() {
        calendarRepository.seenDatePrompt = true
        _uiState.value = true
    }
}