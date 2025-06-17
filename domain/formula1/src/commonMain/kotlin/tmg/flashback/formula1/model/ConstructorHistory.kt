package tmg.flashback.formula1.model

data class ConstructorHistory(
    val constructor: Constructor,
    val standings: List<ConstructorHistorySeason>
) {
    val championshipWins: Int by lazy {
        return@lazy standings
            .filter { !it.isInProgress }
            .count { it.championshipStanding == 1 }
    }

    val driversChampionships: Int by lazy {
        return@lazy standings
            .filter { !it.isInProgress }
            .sumOf { it.drivers.count { (_, driver) -> driver.championshipStanding == 1 } }
    }

    val bestChampionship: Int? by lazy {
        return@lazy standings
            .filter { it.championshipStanding != null }
            .filter { !it.isInProgress && it.championshipStanding!! > 0 }
            .map { it.championshipStanding }
            .minByOrNull { it!! }
    }

    val hasChampionshipCurrentlyInProgress: Boolean by lazy {
        return@lazy standings.any { it.isInProgress }
    }

    val races: Int by lazy {
        return@lazy standings
            .filter { it.races >= 0 }
            .sumOf { it.races }
    }

//    val raceEntries: Int by lazy {
//        return@lazy standings
//            .sumOf { standing ->
//                standing.drivers
//                    .values
//                    .filter { it.races >= 0 }
//                    .sumOf { it.races }
//            }
//    }

    val totalWins: Int by lazy {
        return@lazy standings
            .filter { it.wins >= 0 }
            .sumOf { it.wins }
    }
    val totalPodiums: Int by lazy {
        return@lazy standings
            .filter { it.podiums >= 0 }
            .sumOf { it.podiums }
    }
    val totalPoints: Double by lazy {
        return@lazy standings
            .filter { it.points >= 0 }
            .sumOf { it.points }
    }

    val totalQualifyingPoles: Int by lazy {
        return@lazy standings
            .map { it.qualifyingPole }
            .filterNotNull()
            .filter { it >= 0 }
            .sumOf { it }
    }

    val finishesInPoints: Int by lazy {
        return@lazy standings
            .filter { it.finishInPoints >= 0 }
            .map { it.finishInPoints }
            .sumOf { it }
    }

    fun isWorldChampionFor(season: Int): Boolean {
        return standings
            .filter { it.season == season }
            .filter { !it.isInProgress }
            .filter { it.championshipStanding == 1 }
            .count() > 0
    }

    companion object
}


