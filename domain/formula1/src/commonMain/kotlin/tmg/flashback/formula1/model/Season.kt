package tmg.flashback.formula1.model

data class Season(
    val season: Int,
    val races: List<Race>,
    val drivers: Set<Driver> = races.map { it.entries.map { it.driver } }.flatten().toSet(),
    val constructors: Set<Constructor> = races.map { it.constructors }.flatten().toSet(),
    val event: List<Event>
) {
    val circuits: List<Circuit>
        get() = races
            .map { it.raceInfo.circuit }
            .toSet()
            .toList()

    val firstRace: Race?
        get() = races.minByOrNull { it.raceInfo.round }

    val lastRace: Race?
        get() = races.maxByOrNull { it.raceInfo.round }

    companion object
}