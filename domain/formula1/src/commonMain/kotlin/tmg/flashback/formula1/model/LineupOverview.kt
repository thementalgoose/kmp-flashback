package tmg.flashback.formula1.model

data class LineupOverview(
    val constructor: Constructor,
    val drivers: Map<Driver, List<Int>>
) {
    companion object {

        fun from(lineupList: List<LineupSeason>): List<LineupOverview> {
            val list = mutableListOf<LineupOverview>()
            val constructors = lineupList.flatMap { it.constructors }.distinct()
            for (constructor in constructors) {
                val drivers = lineupList
                    .flatMap { season ->
                        season.driversToConstructors
                            .filter { it.value == constructor }
                            .map { it.key }
                    }
                val driversToSeasons = drivers
                    .associateWith { driver ->
                        lineupList
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
    }
}
