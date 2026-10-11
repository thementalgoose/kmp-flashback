package tmg.flashback.feature.drivers.presentation.stats

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.Driver
import tmg.flashback.formula1.model.DriverHistorySeasonRace
import tmg.flashback.formula1.model.RaceInfo

data class DriverStatsUiState(
    val driver: Driver? = null,
    val constructors: List<Constructor> = emptyList(),
    val selection: DriverFilter? = null,
    val availableSeasons: List<Int> = emptyList(),
    val stats: List<DriverStat> = emptyList(),
    val data: DriverStatsData? = null,
)

sealed interface DriverStatsData {

    data class Overview(
        val teams: List<DriverStatTeamOverview>,
    ): DriverStatsData

    data class Season(
        val races: List<DriverSeasonFeed>,
    ): DriverStatsData
}

sealed interface DriverSeasonFeed {

    val id: String
    data class TeamHeader(
        val team: Constructor,
        val firstRound: Int,
    ): DriverSeasonFeed {
        override val id: String
            get() = "header-${team.id}-${firstRound}"
    }

    data class Weekend(
        val race: DriverHistorySeasonRace?,
        val sprint: DriverHistorySeasonRace?
    ): DriverSeasonFeed {
        val raceInfo: RaceInfo
            get() = race?.raceInfo ?: sprint!!.raceInfo

        val team: Constructor
            get() = race?.constructor ?: sprint!!.constructor

        override val id: String
            get() = "header-${raceInfo.season}-${raceInfo.round}"
    }
}

data class DriverStatTeamOverview(
    val season: Int,
    val teams: List<Constructor>,
    val standing: Int?,
)

data class DriverStat(
    val string: StringResource,
    val icon: DrawableResource,
    val value: String,
    val id: String = string.key
)