package tmg.flashback.feature.weekend.presentation

import flashback.feature.weekend.generated.resources.Res
import flashback.feature.weekend.generated.resources.nav_qualifying
import flashback.feature.weekend.generated.resources.nav_race
import flashback.feature.weekend.generated.resources.nav_sprint
import flashback.feature.weekend.generated.resources.nav_sprint_qualifying
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.nav_qualifying
import flashback.presentation.localisation.generated.resources.nav_race
import flashback.presentation.localisation.generated.resources.nav_sprint
import flashback.presentation.localisation.generated.resources.nav_sprint_qualifying
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import tmg.flashback.feature.weekend.presentation.data.ResultType
import tmg.flashback.feature.weekend.presentation.data.info.InfoModel
import tmg.flashback.feature.weekend.presentation.data.qualifying.QualifyingModel
import tmg.flashback.feature.weekend.presentation.data.race.RaceModel
import tmg.flashback.feature.weekend.presentation.data.sprint_qualifying.SprintQualifyingModel
import tmg.flashback.feature.weekend.presentation.data.sprint_race.SprintRaceModel
import tmg.flashback.formula1.model.QualifyingType

sealed class WeekendUiState {
    data object Initial: WeekendUiState()
    data object NotFound: WeekendUiState()
    data class Data(
        val season: Int,
        val tab: WeekendTabs,
        val tabs: List<WeekendTabs>,
        val info: InfoModel,
        val resultType: ResultType,
        val qualifyingColumns: QualifyingType?,
        val qualifyingResults: List<QualifyingModel>,
        val raceResults: List<RaceModel>,
        val sprintQualifyingResults: List<SprintQualifyingModel>,
        val sprintRaceResults: List<SprintRaceModel>,
    ): WeekendUiState()
}

enum class WeekendTabs(
    val id: String
) {
    Qualifying("Qualifying"),
    Race("Race"),
    SprintQualifying("SprintQualifying"),
    SprintRace("SprintRace"),
}

val WeekendTabs.label: StringResource
    get() = when (this) {
        WeekendTabs.Qualifying -> string.nav_qualifying
        WeekendTabs.Race -> string.nav_race
        WeekendTabs.SprintQualifying -> string.nav_sprint_qualifying
        WeekendTabs.SprintRace -> string.nav_sprint
    }

val WeekendTabs.icon: DrawableResource
    get() = when (this) {
        WeekendTabs.Qualifying -> Res.drawable.nav_qualifying
        WeekendTabs.Race -> Res.drawable.nav_race
        WeekendTabs.SprintQualifying -> Res.drawable.nav_sprint_qualifying
        WeekendTabs.SprintRace -> Res.drawable.nav_sprint
    }