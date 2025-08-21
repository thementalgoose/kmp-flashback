package tmg.flashback.feature.drivers.presentation.stats

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import tmg.flashback.formula1.model.Driver
import tmg.flashback.formula1.model.DriverHistorySeasonRace
import tmg.flashback.navigation.Screen

data class DriverStatsUiState(
    val driver: Driver? = null,
    val availableSeasons: List<Int> = emptyList(),
    val isLoading: Boolean = false,
    val stats: List<DriverStat> = emptyList(),
    val data: DriverStatsData? = null,
)

sealed interface DriverStatsData {

    data class Overview(
        val teams: Map<Int, List<Screen.Constructor>>,
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