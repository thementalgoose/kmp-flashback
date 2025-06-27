package tmg.flashback.feature.weekend.presentation.data.sprint_race

import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.Driver
import tmg.flashback.formula1.model.SprintRaceResult

sealed class SprintRaceModel(
    val id: String
) {
    data class DriverResult(
        val result: SprintRaceResult
    ): SprintRaceModel(
        id = "driver-${result.entry.driver.id}"
    ) {
        companion object
    }

    data class ConstructorResult(
        val constructor: Constructor,
        val position: Int?,
        val points: Double,
        val drivers: List<Pair<Driver, Double>>,
        val maxTeamPoints: Double,
        val highestDriverPosition: Int
    ): SprintRaceModel(
        id = "constructor-${constructor.id}"
    ) {
        companion object
    }
}