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

    val constructorOverview: Map<Constructor, Set<Driver>> by lazy {
        driversToConstructors.values
            .distinct()
            .associateWith { constructor ->
                driversToConstructors
                    .filter { it.value == constructor }
                    .keys
            }
    }

    companion object
}

fun List<LineupSeason>.toOverview(): List<LineupOverview> {
    val list = mutableListOf<LineupOverview>()
    val constructors = this.flatMap { it.constructors }.distinct()
    for (constructor in constructors) {
        val driverMap = this
            .filter { lineup -> lineup.constructors.contains(constructor) }
            .map { lineup -> lineup
                .driversToConstructors
                .filter { it.value == constructors }
                .keys
                .associateWith { lineup.season }
            }

        val drivers = driverMap.flatMap { it.keys }.distinct()
        val driversToSeasons = drivers
            .associateWith {
                driverMap
                    .map { list -> list[it] }
                    .distinct()
                    .filterNotNull()
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