package tmg.flashback.presentation.settings.layout

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import tmg.flashback.feature.season.repositories.CalendarRepository

class SettingsLayoutViewModel(
    private val calendarRepository: CalendarRepository
): ViewModel() {

    private val _uiState: MutableStateFlow<SettingsLayoutUiState> = MutableStateFlow(
        SettingsLayoutUiState(
            recentHighlights = false,
            collapseRaces = calendarRepository.collapseList,
            showEmptyWeeks = calendarRepository.emptyWeeksInCalendar,
            keepLastSeason = calendarRepository.keepUserSelectedSeason
        ))
    val uiState: StateFlow<SettingsLayoutUiState> = _uiState

    fun refresh() {
        SettingsLayoutUiState(
            recentHighlights = false,
            collapseRaces = calendarRepository.collapseList,
            showEmptyWeeks = calendarRepository.emptyWeeksInCalendar,
            keepLastSeason = calendarRepository.keepUserSelectedSeason
        )
    }

    fun updateCollapseRacesEnabled(enabled: Boolean) {
        calendarRepository.collapseList = enabled
        refresh()
    }

    fun updateShowEmptyWeeks(enabled: Boolean) {
        calendarRepository.emptyWeeksInCalendar = enabled
        refresh()
    }

    fun updateKeepLastSeason(enabled: Boolean) {
        calendarRepository.keepUserSelectedSeason = enabled
        refresh()
    }
}