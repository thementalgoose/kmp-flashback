package tmg.flashback.feature.season.presentation.shared.seasonpicker

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

class SeasonPickerViewModel (
    private val currentSeasonHolder: CurrentSeasonHolder,
): ViewModel() {

    val currentSeason: StateFlow<Int> = currentSeasonHolder.currentSeasonFlow

    val supportedSeasons: StateFlow<List<Int>> = currentSeasonHolder.supportedSeasonsFlow

    val newSeasonAvailable: StateFlow<Boolean> = currentSeasonHolder.newSeasonAvailableFlow

    fun currentSeasonUpdate(season: Int) {
        currentSeasonHolder.updateTo(season)
    }
}