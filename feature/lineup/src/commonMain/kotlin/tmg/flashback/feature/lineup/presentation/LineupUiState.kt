package tmg.flashback.feature.lineup.presentation

import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.Driver

data class LineupUiState(
    val sinceSeason: Int,
    val rows: List<DriverLineup>
)

data class DriverLineup(
    val driver: Driver,
    val contracts: List<DriverContract>
)

data class DriverContract(
    val startSeason: Int,
    val endSeason: Int,
    val constructor: Constructor
)