package tmg.flashback.feature.season.presentation.driver_standings

import tmg.flashback.formula1.model.SeasonDriverStandingSeason

data class DriverStandingsState(
    val season: Int,
    val standings: List<SeasonDriverStandingSeason> = emptyList(),
    val inProgress: Pair<String, Int>? = null,
    val isLoading: Boolean = true,
    val maxPoints: Double = 0.0,
)