package tmg.flashback.formula1.model

data class DriverHistory(
        val driver: Driver,
        val standings: List<DriverHistorySeason>
) {
    val championshipWins: Int by lazy {
        return@lazy standings
            .filter { !it.isInProgress }
            .count { it.championshipStanding == 1 }
    }

    val careerBestChampionship: Int? by lazy {
        return@lazy standings
                .filter { it.championshipStanding != null }
                .filter { !it.isInProgress && it.championshipStanding!! > 0 }
                .map { it.championshipStanding }
                .minByOrNull { it!! }
    }

    val hasChampionshipCurrentlyInProgress: Boolean by lazy {
        return@lazy standings.any { it.isInProgress }
    }

    val careerWins: Int by lazy {
        return@lazy standings
                .sumOf { it.wins }
    }
    val careerPodiums: Int by lazy {
        return@lazy standings
                .sumOf { it.podiums }
    }
    val careerPoints: Double by lazy {
        return@lazy standings
                .sumOf { it.points }
    }
    val careerRaces: Int by lazy {
        return@lazy standings
                .sumOf { it.races }
    }

    val constructors: List<Pair<Int, Constructor>> by lazy {
        return@lazy standings
                .sortedBy { it.season }
                .map { standings ->
                    standings.constructors.map {
                        Pair(standings.season, it)
                    }
                }
                .flatten()
    }

    val raceStarts: Int by lazy {
        return@lazy standings
                .map { it.raceStarts }
                .sum()
    }
    val raceFinishes: Int by lazy {
        return@lazy standings
                .map { it.raceFinishes }
                .sum()
    }
    val raceRetirements: Int by lazy {
        return@lazy standings
                .map { it.raceRetirements }
                .sum()
    }

    val careerBestFinish: Int by lazy {
        return@lazy standings.minByOrNull { it.bestFinish ?: Int.MAX_VALUE }?.bestFinish ?: -1
    }

    val careerQualifyingPoles: Int by lazy {
        return@lazy totalQualifyingIn(1)
    }
    val careerQualifyingTop3: Int by lazy {
        return@lazy totalFinishesAbove(3)
    }
    val careerQualifyingTop10: Int by lazy {
        return@lazy totalFinishesAbove(10)
    }

    val careerFinishesInPoints: Int by lazy {
        return@lazy standings
                .map { it.raceOverview }
                .flatten()
                .count { it.points > 0 }
    }
    val careerFinishesTop5: Int by lazy {
        return@lazy totalFinishesAbove(5)
    }

    fun totalFinishesIn(position: Int): Int {
        return standings
                .map { it.raceOverview }
                .flatten()
                .count { it.finished == position }
    }

    fun totalFinishesAbove(position: Int): Int {
        return standings
                .map { it.raceOverview }
                .flatten()
                .count {
                    @Suppress("ConvertTwoComparisonsToRangeCheck")
                    it.finished >= 1 && it.finished <= position
                }
    }

    fun totalQualifyingAbove(position: Int): Int {
        return standings
                .map { it.raceOverview }
                .flatten()
                .count {
                    it.qualified != null && it.qualified >= 1 && it.qualified <= position
                }
    }

    fun totalQualifyingIn(position: Int): Int {
        return standings
                .map { it.raceOverview }
                .flatten()
                .count { it.qualified == position }
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