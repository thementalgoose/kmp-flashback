package tmg.flashback.feature.constructors.presentation.stats

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.ConstructorHistorySeasonDriver
import tmg.flashback.formula1.model.Driver

data class ConstructorStatsUiState(
    val constructor: Constructor? = null,
    val selection: ConstructorFilter? = null,
    val availableSeasons: List<Int> = emptyList(),
    val stats: List<ConstructorStat> = emptyList(),
    val data: ConstructorStatsData? = null,
)

sealed interface ConstructorStatsData {

    data class Overview(
        val items: Map<Int, List<Driver>>,
    ): ConstructorStatsData

    data class Season(
        val drivers: List<ConstructorHistorySeasonDriver>,
    ): ConstructorStatsData
}

data class ConstructorStat(
    val string: StringResource,
    val icon: DrawableResource,
    val value: String,
    val id: String = string.key
)