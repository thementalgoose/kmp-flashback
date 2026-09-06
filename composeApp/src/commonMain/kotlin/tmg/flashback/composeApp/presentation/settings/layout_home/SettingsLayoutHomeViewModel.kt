package tmg.flashback.composeApp.presentation.settings.layout_home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import tmg.flashback.feature.highlights.repositories.HighlightsRepository
import tmg.flashback.feature.season.repositories.CalendarRepository

class SettingsLayoutHomeViewModel(
    private val calendarRepository: CalendarRepository,
    private val highlightsRepository: HighlightsRepository
): ViewModel() {

    private val _uiState: MutableStateFlow<SettingsLayoutHomeUiState> = MutableStateFlow(
        SettingsLayoutHomeUiState(
            recentHighlights = highlightsRepository.show,
            collapseRaces = calendarRepository.collapseList,
            showEmptyWeeks = calendarRepository.emptyWeeksInCalendar,
            keepLastSeason = calendarRepository.keepUserSelectedSeason
        ))
    val uiState: StateFlow<SettingsLayoutHomeUiState> = _uiState

    fun refresh() {
        _uiState.update {
            SettingsLayoutHomeUiState(
                recentHighlights = highlightsRepository.show,
                collapseRaces = calendarRepository.collapseList,
                showEmptyWeeks = calendarRepository.emptyWeeksInCalendar,
                keepLastSeason = calendarRepository.keepUserSelectedSeason
            )
        }
    }

    fun updateHighlight(enabled: Boolean) {
        highlightsRepository.show = enabled
        refresh()
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