package tmg.flashback.feature.drivers.presentation.season

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.Driver
import tmg.flashback.formula1.model.DriverHistorySeasonRace

sealed interface DriverSeasonUiState {
    data class Data(
        val season: Int,
        val driver: Driver,
        val isInProgress: Boolean,
        val stats: List<DriverSeasonStat> = emptyList(),
        val races: List<DriverSeasonRace> = emptyList()
    ): DriverSeasonUiState {
        val latestConstructor: Constructor? by lazy {
            races.mapNotNull { it.result.constructor }
                .lastOrNull()
        }
        val constructors: List<Constructor> by lazy {
            races.mapNotNull { it.result.constructor }
                .distinct()
        }
    }

    data class NoRaces(
        val season: Int,
        val driver: Driver
    ): DriverSeasonUiState

    data object NotFound: DriverSeasonUiState
}

data class DriverSeasonStat(
    val string: StringResource,
    val icon: DrawableResource,
    val value: String
)

data class DriverSeasonRace(
    val result: DriverHistorySeasonRace
)