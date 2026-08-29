package tmg.flashback.formula1.model

data class LineupSeason(
    val season: Int,
    val driversToConstructors: Map<Driver, Constructor>
) {
    companion object
}