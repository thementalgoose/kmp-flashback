package tmg.flashback.feature.season.presentation.shared.seasonpicker

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import tmg.flashback.data.repo.repository.InfoRepository
import tmg.flashback.feature.season.repositories.CalendarRepository
import tmg.flashback.feature.season.usecases.DefaultSeasonUseCase

interface CurrentSeasonHolder {
    val currentSeasonFlow: StateFlow<Int>
    val currentSeason: Int
    val supportedSeasonsFlow: StateFlow<List<Int>>
    val supportedSeasons: List<Int>
    val newSeasonAvailableFlow: StateFlow<Boolean>
    val defaultSeason: Int

    fun updateTo(season: Int)
    fun refresh()
}

class CurrentSeasonHolderImpl(
    private val defaultSeasonUseCase: DefaultSeasonUseCase,
    private val calendarRepository: CalendarRepository,
    private val infoRepository: InfoRepository
): CurrentSeasonHolder {

    override val currentSeasonFlow: MutableStateFlow<Int> = MutableStateFlow(defaultSeasonUseCase.defaultSeason)
    override val currentSeason: Int
        get() = currentSeasonFlow.value

    override val supportedSeasonsFlow: MutableStateFlow<List<Int>> = MutableStateFlow(infoRepository.supportedSeasons.sortedByDescending { it })
    override val supportedSeasons: List<Int>
        get() = supportedSeasonsFlow.value

    override val newSeasonAvailableFlow: MutableStateFlow<Boolean> = MutableStateFlow(newSeasonAvailable())

    override val defaultSeason: Int
        get() = defaultSeasonUseCase.defaultSeason

    init {
        calendarRepository.viewedSeasons = calendarRepository.viewedSeasons + currentSeason
    }

    override fun updateTo(season: Int) {
        calendarRepository.viewedSeasons = (calendarRepository.viewedSeasons + season)
        calendarRepository.userSelectedSeason = season
        if (supportedSeasons.contains(season)) {
            currentSeasonFlow.value = season
        }
        refresh()
    }

    override fun refresh() {
        val seasons = infoRepository.supportedSeasons.sortedByDescending { it }
        supportedSeasonsFlow.value = seasons
        newSeasonAvailableFlow.value = newSeasonAvailable()
        if (!seasons.contains(currentSeason)) {
            currentSeasonFlow.value = defaultSeasonUseCase.defaultSeason
        }
    }

    private fun newSeasonAvailable(): Boolean {
        val viewedSeasons = calendarRepository.viewedSeasons
        val newSeasons = supportedSeasons
        return newSeasons.any { season -> season !in viewedSeasons }
    }
}