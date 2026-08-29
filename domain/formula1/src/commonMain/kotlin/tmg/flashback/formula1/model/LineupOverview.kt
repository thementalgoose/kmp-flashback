package tmg.flashback.formula1.model

data class LineupOverview(
    val constructor: Constructor,
    val drivers: Map<Driver, List<Int>>
) {
    companion object
}
