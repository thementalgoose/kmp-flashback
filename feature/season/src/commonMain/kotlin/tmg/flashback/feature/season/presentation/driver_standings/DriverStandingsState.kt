package tmg.flashback.feature.season.presentation.driver_standings

import tmg.flashback.formula1.model.SeasonDriverStandingSeason

data class DriverStandingsState(
    val season: Int,
    val standings: List<SeasonDriverStandingSeason> = emptyList(),
    val inProgress: DriverStandingsStateInProgress? = null,
    val isLoading: Boolean = true,
    val maxPoints: Double = 0.0,
)

data class DriverStandingsStateInProgress(
    val raceName: String,
    val round: Int,
    val championSecured: Boolean
)