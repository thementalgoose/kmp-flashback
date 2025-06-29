package tmg.flashback.feature.constructors.presentation.season

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.ConstructorHistorySeasonDriver

sealed interface ConstructorSeasonUiState {
    data class Data(
        val season: Int,
        val constructor: Constructor,
        val isInProgress: Boolean,
        val stats: List<ConstructorSeasonStat> = emptyList(),
        val drivers: List<ConstructorSeasonDriver> = emptyList()
    ): ConstructorSeasonUiState

    data class NoRaces(
        val season: Int,
        val constructor: Constructor
    ): ConstructorSeasonUiState

    data object NotFound: ConstructorSeasonUiState
}

data class ConstructorSeasonStat(
    val string: StringResource,
    val icon: DrawableResource,
    val value: String
)

data class ConstructorSeasonDriver(
    val result: ConstructorHistorySeasonDriver
)