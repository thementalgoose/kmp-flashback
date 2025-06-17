package tmg.flashback.formula1.extensions

import tmg.flashback.formula1.model.Race

/**
 * Get the best qualifying position for a given driver
 */
fun List<Race>.bestQualified(driverId: String): Int? {
    val round = this.minByOrNull { it.race.firstOrNull { it.entry.driver.id == driverId }?.qualified ?: Int.MAX_VALUE }
    return round?.race?.firstOrNull { it.entry.driver.id == driverId }?.qualified
}

fun List<Race>.bestQualifyingResultFor(driverId: String): Pair<Int, List<Race>>? {
    val bestQualifyingPosition: Int = this.bestQualified(driverId) ?: return null
    val listOfCircuits = this
        .filter { it.race.firstOrNull { it.entry.driver.id == driverId }?.qualified == bestQualifyingPosition }
    return Pair(bestQualifyingPosition, listOfCircuits)
}

/**
 * Get the best finishing position for a given driver
 */
fun List<Race>.bestFinish(driverId: String): Int? {
    val round = this.minByOrNull { it.race.firstOrNull { it.entry.driver.id == driverId }?.finish ?: Int.MAX_VALUE }
    return round?.race?.firstOrNull { it.entry.driver.id == driverId }?.finish
}

fun List<Race>.bestRaceResultFor(driverId: String): Pair<Int, List<Race>>? {
    val bestQualifyingPosition: Int = this.bestFinish(driverId) ?: return null
    val listOfCircuits = this
        .filter { it.race.firstOrNull { it.entry.driver.id == driverId }?.finish == bestQualifyingPosition }
    return Pair(bestQualifyingPosition, listOfCircuits)
}