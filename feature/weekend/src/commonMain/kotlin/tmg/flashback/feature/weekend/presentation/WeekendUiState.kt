package tmg.flashback.feature.weekend.presentation

import tmg.flashback.feature.weekend.presentation.data.info.InfoModel
import tmg.flashback.feature.weekend.presentation.data.qualifying.QualifyingModel
import tmg.flashback.feature.weekend.presentation.data.race.RaceModel
import tmg.flashback.feature.weekend.presentation.data.sprint_qualifying.SprintQualifyingModel
import tmg.flashback.feature.weekend.presentation.data.sprint_race.SprintRaceModel

sealed class WeekendUiState {
    data object Initial: WeekendUiState()
    data object NotFound: WeekendUiState()
    data class Data(
        val season: Int,
        val info: InfoModel,
        val qualifyingResults: List<QualifyingModel>,
        val raceResults: List<RaceModel>,
        val sprintQualifyingResults: List<SprintQualifyingModel>,
        val sprintRaceResults: List<SprintRaceModel>,
    ): WeekendUiState()
}