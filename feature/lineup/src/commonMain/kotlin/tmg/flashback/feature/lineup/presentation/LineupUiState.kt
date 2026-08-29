package tmg.flashback.feature.lineup.presentation

import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.Driver
import tmg.flashback.formula1.preview.preview

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

internal val fakeUiState = LineupUiState(
    sinceSeason = 2026,
    rows = listOf(
        DriverLineup(
            driver = Driver.preview(id = "1"),
            contracts = listOf(
                DriverContract(
                    startSeason = 2026,
                    endSeason = 2028,
                    constructor = Constructor.preview(id = "1")
                )
            )
        ),
        DriverLineup(
            driver = Driver.preview(id = "2"),
            contracts = listOf(
                DriverContract(
                    startSeason = 2026,
                    endSeason = 2027,
                    constructor = Constructor.preview(id = "1")
                ),
                DriverContract(
                    startSeason = 2028,
                    endSeason = 2028,
                    constructor = Constructor.preview(id = "2")
                )
            )
        ),
        DriverLineup(
            driver = Driver.preview(id = "3"),
            contracts = listOf(
                DriverContract(
                    startSeason = 2027,
                    endSeason = 2029,
                    constructor = Constructor.preview(id = "2")
                )
            )
        )
    )
)