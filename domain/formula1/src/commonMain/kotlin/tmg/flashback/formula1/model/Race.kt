package tmg.flashback.formula1.model

import tmg.flashback.formula1.model.QualifyingType.Q1
import tmg.flashback.formula1.model.QualifyingType.Q2
import tmg.flashback.formula1.model.QualifyingType.Q3
import tmg.flashback.formula1.model.SprintQualifyingType.SQ1
import tmg.flashback.formula1.model.SprintQualifyingType.SQ2
import tmg.flashback.formula1.model.SprintQualifyingType.SQ3

data class Race(
    val raceInfo: RaceInfo,
    val qualifying: List<QualifyingRound>,
    val sprint: SprintResult,
    val race: List<RaceResult>,
    val schedule: List<Schedule>
) {

    val entries: List<DriverEntry>
    val constructors: List<Constructor>

    init {
        val driverSet: MutableSet<DriverEntry> = mutableSetOf()
        val constructorSet: MutableSet<Constructor> = mutableSetOf()
        qualifying.forEach {
            it.results.forEach {
                constructorSet.add(it.entry.constructor)
                driverSet.add(it.entry)
            }
        }
        race.forEach {
            constructorSet.add(it.entry.constructor)
            driverSet.add(it.entry)
        }
        sprint.qualifying.forEach {
            it.results.forEach {
                constructorSet.add(it.entry.constructor)
                driverSet.add(it.entry)
            }
        }
        sprint.race.forEach {
            constructorSet.add(it.entry.constructor)
            driverSet.add(it.entry)
        }
        this.entries = driverSet.toList()
        this.constructors = constructorSet.toList()
    }

    fun driverOverview(driverId: String): RaceDriverOverview? {
        val driver = entries.firstOrNull { it.driver.id == driverId } ?: return null
        return RaceDriverOverview(
            entry = driver,
            q1 = qualifying.firstOrNull { it.label == Q1 }?.results?.firstOrNull { it.entry.driver.id == driverId },
            q2 = qualifying.firstOrNull { it.label == Q2 }?.results?.firstOrNull { it.entry.driver.id == driverId },
            q3 = qualifying.firstOrNull { it.label == Q3 }?.results?.firstOrNull { it.entry.driver.id == driverId },
            race = race.firstOrNull { it.entry.driver.id == driverId },
            sprintQ1 = sprint.qualifying.firstOrNull { it.label == SQ1 }?.results?.firstOrNull { it.entry.driver.id == driverId },
            sprintQ2 = sprint.qualifying.firstOrNull { it.label == SQ2 }?.results?.firstOrNull { it.entry.driver.id == driverId },
            sprintQ3 = sprint.qualifying.firstOrNull { it.label == SQ3 }?.results?.firstOrNull { it.entry.driver.id == driverId },
            sprintRace = sprint.race.firstOrNull { it.entry.driver.id == driverId },
        )
    }

    val hasSprint: Boolean = sprint.qualifying.isNotEmpty() || sprint.race.isNotEmpty()

    val hasSprintQualifying: Boolean = sprint.qualifying.isNotEmpty()
    val hasSprintRace: Boolean = sprint.race.isNotEmpty()

    fun has(raceQualifyingType: QualifyingType): Boolean {
        return qualifying.any { it.label == raceQualifyingType}
    }

    val q1FastestLap: LapTime? by lazy {
        qualifying.firstOrNull { it.label == Q1 }
            ?.results
            ?.minByOrNull { it.lapTime?.totalMillis ?: Int.MAX_VALUE }
            ?.lapTime
    }
    val q2FastestLap: LapTime? by lazy {
        qualifying.firstOrNull { it.label == Q2 }
            ?.results
            ?.minByOrNull { it.lapTime?.totalMillis ?: Int.MAX_VALUE }
            ?.lapTime
    }
    val q3FastestLap: LapTime? by lazy {
        qualifying.firstOrNull { it.label == Q3 }
            ?.results
            ?.minByOrNull { it.lapTime?.totalMillis ?: Int.MAX_VALUE }
            ?.lapTime
    }

    val hasData: Boolean by lazy {
        qualifying.isNotEmpty() || race.isNotEmpty()
    }

    val constructorStandings: List<RaceConstructorStandings>
        get() {
            val standings: MutableMap<String, Double> = mutableMapOf()
            for (raceResult in race) {
                var previousPoints = standings.getOrPut(raceResult.entry.constructor.id) { 0.0 }
                previousPoints += raceResult.points
                standings[raceResult.entry.constructor.id] = previousPoints
            }
            if (hasSprint) {
                sprint.race.forEach {
                    var previousPoints = standings.getOrPut(it.entry.constructor.id) { 0.0 }
                    previousPoints += it.points
                    standings[it.entry.constructor.id] = previousPoints
                }
            }
            return constructors
                .map {
                    RaceConstructorStandings(
                        standings.getOrElse(
                            it.id
                        ) { 0.0 }, it
                    )
                }
                .sortedByDescending { it.points }
        }

    companion object
}




