package tmg.flashback.formula1.model

data class LineupSeason(
    val season: Int,
    val driversToConstructors: Map<Driver, Constructor>
) {
    val constructors: List<Constructor> by lazy {
        driversToConstructors.values.distinct()
    }
    val drivers: List<Driver> by lazy {
        driversToConstructors.keys.distinct()
    }

    companion object
}