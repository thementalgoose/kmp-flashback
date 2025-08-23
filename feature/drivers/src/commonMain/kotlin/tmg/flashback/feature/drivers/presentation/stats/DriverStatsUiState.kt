package tmg.flashback.feature.drivers.presentation.stats

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.Driver
import tmg.flashback.formula1.model.DriverHistorySeasonRace

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
        val teams: Map<Int, List<Constructor>>,
    ): DriverStatsData

    data class Season(
        val races: List<DriverHistorySeasonRace>,
    ): DriverStatsData
}

data class DriverStat(
    val string: StringResource,
    val icon: DrawableResource,
    val value: String,
    val id: String = string.key
)