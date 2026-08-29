package tmg.flashback.formula1.model

data class LineupDriverOverview(
    val driver: Driver,
    val seasons: Map<Int, Constructor>
)