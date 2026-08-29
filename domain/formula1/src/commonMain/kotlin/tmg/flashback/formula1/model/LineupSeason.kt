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

fun List<LineupSeason>.toOverview(): List<LineupOverview> {
    val list = mutableListOf<LineupOverview>()
    val constructors = this.flatMap { it.constructors }.distinct()
    for (constructor in constructors) {
        val drivers = this
            .flatMap { season ->
                season.driversToConstructors
                    .filter { it.value == constructor }
                    .map { it.key }
            }
        val driversToSeasons = drivers
            .associateWith { driver ->
                this
                    .filter { it.driversToConstructors[driver] == constructor }
                    .map { it.season }
                    .distinct()
                    .sorted()
            }

        list.add(
            LineupOverview(
                constructor = constructor,
                drivers = driversToSeasons
            )
        )
    }
    return list.toList()
}