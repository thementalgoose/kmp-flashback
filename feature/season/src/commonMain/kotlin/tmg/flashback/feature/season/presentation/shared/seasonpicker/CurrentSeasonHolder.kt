package tmg.flashback.feature.season.presentation.shared.seasonpicker

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import tmg.flashback.data.repo.repository.InfoRepository
import tmg.flashback.feature.season.repositories.CalendarRepository
import tmg.flashback.feature.season.usecases.DefaultSeasonUseCase

class CurrentSeasonHolder(
    private val defaultSeasonUseCase: DefaultSeasonUseCase,
    private val calendarRepository: CalendarRepository,
    private val infoRepository: InfoRepository
) {

    private val _currentSeason: MutableStateFlow<Int> = MutableStateFlow(defaultSeasonUseCase.defaultSeason)
    val currentSeasonFlow: StateFlow<Int> = _currentSeason
    val currentSeason: Int
        get() = currentSeasonFlow.value

    private val _supportedSeasons: MutableStateFlow<List<Int>> = MutableStateFlow(infoRepository.supportedSeasons.sortedByDescending { it })
    val supportedSeasonFlow: StateFlow<List<Int>> = _supportedSeasons
    val supportedSeasons: List<Int>
        get() = _supportedSeasons.value

    private val _newSeasonAvailable: MutableStateFlow<Boolean> = MutableStateFlow(newSeasonAvailable())
    val newSeasonAvailableFlow: StateFlow<Boolean> = _newSeasonAvailable

    val defaultSeason: Int
        get() = defaultSeasonUseCase.defaultSeason

    init {
        calendarRepository.viewedSeasons = calendarRepository.viewedSeasons + currentSeason
    }

    fun updateTo(season: Int) {
        calendarRepository.viewedSeasons = (calendarRepository.viewedSeasons + season)
        calendarRepository.userSelectedSeason = season
        if (supportedSeasons.contains(season)) {
            _currentSeason.value = season
        }
        refresh()
    }

    fun refresh() {
        val seasons = infoRepository.supportedSeasons.sortedByDescending { it }
        _supportedSeasons.value = seasons
        _newSeasonAvailable.value = newSeasonAvailable()
        if (!seasons.contains(currentSeason)) {
            _currentSeason.value = defaultSeasonUseCase.defaultSeason
        }
    }

    private fun newSeasonAvailable(): Boolean {
        val viewedSeasons = calendarRepository.viewedSeasons
        val newSeasons = supportedSeasons
        return newSeasons.any { season -> season !in viewedSeasons }
    }
}