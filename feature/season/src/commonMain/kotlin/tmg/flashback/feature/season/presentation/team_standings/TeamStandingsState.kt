package tmg.flashback.feature.season.presentation.team_standings

import tmg.flashback.formula1.model.SeasonConstructorStandingSeason

data class TeamStandingsState(
    val season: Int,
    val standings: List<SeasonConstructorStandingSeason> = emptyList(),
    val inProgress: Pair<String, Int>? = null,
    val isLoading: Boolean = false,
    val networkAvailable: Boolean = true,
    val maxPoints: Double = 0.0
)