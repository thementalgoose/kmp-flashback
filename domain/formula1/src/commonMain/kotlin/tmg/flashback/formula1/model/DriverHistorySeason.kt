package tmg.flashback.formula1.model

import tmg.flashback.formula1.enums.isStatusFinished

data class DriverHistorySeason(
    val championshipStanding: Int?,
    val isInProgress: Boolean,
    val points: Double,
    val season: Int,
    val constructors: List<Constructor>,
    val raceOverview: List<DriverHistorySeasonRace>
) {
    val bestFinish: Int? by lazy {
        return@lazy raceOverview
            .filter { !it.isSprint }
            .filter { it.finished > 0 }
            .minByOrNull { it.finished }
            ?.finished
    }

    val bestQualifying: Int? by lazy {
        return@lazy raceOverview
            .filter { !it.isSprint }
            .filter { (it.qualified ?: 0) > 0 }
            .minByOrNull { it.qualified ?: Int.MAX_VALUE }
            ?.qualified
    }

    val podiums: Int by lazy {
        return@lazy raceOverview
            .filter { !it.isSprint }
            .count { it.finished in 1..3 }
    }
    val races: Int by lazy {
        return@lazy raceOverview
            .filter { !it.isSprint }
            .size
    }
    val wins: Int by lazy {
        return@lazy raceOverview
            .filter { !it.isSprint }
            .count { it.finished == 1}
    }

    val qualifyingPoles: Int by lazy {
        return@lazy totalQualifyingIn(1)
    }
    val qualifyingTop3: Int by lazy {
        return@lazy totalQualifyingAbove(3)
    }

    val finishesInPoints: Int by lazy {
        return@lazy raceOverview
            .filter { !it.isSprint }
            .count { it.points > 0 }
    }

    val raceStarts: Int by lazy {
        return@lazy races
    }
    val raceFinishes: Int by lazy {
        return@lazy raceOverview
            .filter { !it.isSprint }
            .filter { it.status.isStatusFinished() }
            .size
    }
    val raceRetirements: Int by lazy {
        return@lazy raceOverview
            .filter { !it.isSprint }
            .filter { !it.status.isStatusFinished() }
            .size
    }

    fun totalQualifyingAbove(position: Int): Int {
        return raceOverview
            .filter { !it.isSprint }
            .count {
                it.qualified != null && it.qualified >= 1 && it.qualified <= position
            }
    }

    private fun totalQualifyingIn(position: Int): Int {
        return raceOverview
            .filter { !it.isSprint }
            .count { it.qualified == position }
    }

    companion object
}