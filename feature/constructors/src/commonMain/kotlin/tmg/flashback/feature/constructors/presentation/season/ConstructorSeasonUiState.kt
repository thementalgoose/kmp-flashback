package tmg.flashback.feature.constructors.presentation.season

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.ConstructorHistorySeasonDriver

data class ConstructorSeasonUiState(
    val season: Int,
    val constructor: Constructor,
    val stats: List<Stat>,
    val drivers: List<ConstructorHistorySeasonDriver>
)

data class Stat(
    val title: StringResource,
    val icon: DrawableResource,
    val value: String
)