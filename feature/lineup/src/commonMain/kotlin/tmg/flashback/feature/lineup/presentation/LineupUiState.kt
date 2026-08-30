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
    val contractBars: Map<Driver, List<Int>>,
)

data class ConstructorBar(
    val signedup: Boolean,
    val width: Int
)


internal val fakeUiState = LineupUiState(
    isLoading = false,
    seasons = listOf(2026, 2027, 2028, 2029),
    rows = listOf(
        ConstructorLineup(
            constructor = Constructor.preview(id = "1"),
            contractBars = mapOf(
                Driver.preview(id = "1") to listOf(2026, 2027, 2028),
                Driver.preview(id = "2") to listOf(2026, 2027),
                Driver.preview(id = "3") to listOf(2027, 2028),
            )
        )
    )
)

internal val fakeUiUnconfirmedState = LineupUiState(
    isLoading = false,
    seasons = listOf(2026, 2027),
    rows = listOf(
        ConstructorLineup(
            constructor = Constructor.preview(id = "1"),
            contractBars = mapOf(
                Driver.preview(id = "1") to listOf(2026, 2027, 2028),
                Driver.preview(id = "2") to listOf(2026, 2027),
                Driver.preview(id = "3") to listOf(2027, 2028),
            )
        )
    )
)