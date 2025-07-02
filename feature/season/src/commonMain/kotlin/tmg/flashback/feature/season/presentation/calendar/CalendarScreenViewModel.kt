package tmg.flashback.feature.season.presentation.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import tmg.flashback.data.repo.repository.EventRepository
import tmg.flashback.data.repo.repository.OverviewRepository
import tmg.flashback.data.repo.repository.RaceRepository
import tmg.flashback.feature.season.models.NotificationSchedule
import tmg.flashback.feature.season.presentation.calendar.ScheduleBuilder.generateScheduleModel
import tmg.flashback.feature.season.presentation.shared.seasonpicker.CurrentSeasonHolder
import tmg.flashback.feature.season.repositories.CalendarRepository
import tmg.flashback.infrastructure.log.logInfo

class CalendarScreenViewModel(
    private val overviewRepository: OverviewRepository,
    private val raceRepository: RaceRepository,
    private val currentSeasonHolder: CurrentSeasonHolder,
    calendarRepository: CalendarRepository,
    private val eventsRepository: EventRepository,
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
): ViewModel() {

    private val _uiState: MutableStateFlow<CalendarScreenState> = MutableStateFlow(CalendarScreenState(
        season = currentSeasonHolder.currentSeason,
    ))
    val uiState: StateFlow<CalendarScreenState> = _uiState

    private var collapseRaces: Boolean = calendarRepository.collapseList
    private var showEmptyWeeks: Boolean = calendarRepository.emptyWeeksInCalendar

    init {
        viewModelScope.launch(mainDispatcher) {
            currentSeasonHolder.currentSeasonFlow.collectLatest {
                _uiState.value = _uiState.value.copy(season = it)
                if (!populate(it) || it == currentSeasonHolder.defaultSeason) {
                    refresh()
                }
            }
        }
    }

    fun refresh() {
        viewModelScope.launch(mainDispatcher) {
            val season = uiState.value.season
            if (_uiState.value.items.isNullOrEmpty() || _uiState.value.items?.none { it is CalendarItem.RaceWeek } == true) {
                populate(season)
            }
            _uiState.value = _uiState.value.copy(isLoading = true, showEvents = false)
            overviewRepository.populateOverview(season)
            raceRepository.populateRaces(season)
            populate(season)
        }
    }

    private suspend fun populate(season: Int): Boolean {
        val overview = overviewRepository.getOverview(season).firstOrNull()
        val events = eventsRepository.getEvents(season).firstOrNull()
        if (overview == null || overview.overviewRaces.isEmpty()) {
            _uiState.value = _uiState.value.copy(
                items = null,
                isLoading = false,
                showEvents = events?.isNotEmpty() ?: false
            )
            return false
        }

        val raceList = generateScheduleModel(
            overview = overview,
            events = events ?: emptyList(),
            notificationSchedule = NotificationSchedule(false, false, false, false, false, false),
            showCollapsePreviousRaces = collapseRaces,
            showEmptyWeeks = showEmptyWeeks,
        )

        _uiState.value = _uiState.value.copy(
            items = raceList,
            isLoading = false,
            showEvents = events?.isNotEmpty() == true
        )
        return true
    }

    fun clickGroupedRaces() {
        collapseRaces = false
        viewModelScope.launch { populate(uiState.value.season) }
    }
}