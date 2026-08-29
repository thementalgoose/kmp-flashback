package tmg.flashback.feature.lineup.presentation

import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.Driver
import tmg.flashback.formula1.preview.preview

data class LineupUiState(
    val isLoading: Boolean,
    val seasons: List<Int>,
    val rows: List<ConstructorLineup>
) {
    constructor(): this(false, emptyList(), emptyList())
}


data class ConstructorLineup(
    val constructor: Constructor,
    val contractBars: Map<Driver, List<ConstructorBar>>,
)

data class ConstructorBar(
    val signedup: Boolean,
    val seasons: Int
)


internal val fakeUiState = LineupUiState(
    isLoading = false,
    seasons = listOf(2026, 2027, 2028, 2029),
    rows = listOf(
        ConstructorLineup(
            constructor = Constructor.preview(id = "1"),
            contractBars = mapOf(
                Driver.preview(id = "1") to listOf(ConstructorBar(true, 3)),
                Driver.preview(id = "2") to listOf(ConstructorBar(true, 2)),
                Driver.preview(id = "3") to listOf(ConstructorBar(false, 1), ConstructorBar(true, 2)),
            )
        )
    )
)