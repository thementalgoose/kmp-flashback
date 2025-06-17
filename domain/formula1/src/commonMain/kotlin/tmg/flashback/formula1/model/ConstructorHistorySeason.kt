package tmg.flashback.formula1.model

data class ConstructorHistorySeason(
    val drivers: Map<String, ConstructorHistorySeasonDriver>,
    val isInProgress: Boolean,
    val championshipStanding: Int?,
    val points: Double,
    val season: Int,
    val races: Int
) {
    val qualifyingPole: Int? by lazy {
        return@lazy drivers.values
            .sumOf { it.polePosition }
    }

    val driverPoints: Double? by lazy {
        return@lazy drivers.values
            .filter { it.points >= 0 }
            .sumOf { it.points }
    }

    val finishInPoints: Int by lazy {
        return@lazy drivers.values
            .filter { it.finishesInPoints >= 0 }
            .sumOf { it.finishesInPoints }
    }

    val wins: Int by lazy {
        return@lazy drivers.values
            .sumOf { it.wins }
    }

    val podiums: Int by lazy {
        return@lazy drivers.values
            .sumOf { it.podiums }
    }

    companion object
}