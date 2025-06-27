package tmg.flashback.feature.weekend.presentation.data.sprint_race

import tmg.flashback.feature.weekend.presentation.data.race.RaceModel
import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.Driver
import tmg.flashback.formula1.model.RaceResult
import tmg.flashback.formula1.model.SprintRaceResult
import tmg.flashback.formula1.preview.preview

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

fun SprintRaceModel.ConstructorResult.Companion.preview() = SprintRaceModel.ConstructorResult(
    constructor = Constructor.preview(),
    position = 1,
    points = 25.0,
    drivers = listOf(
        Driver.preview() to 10.0,
        Driver.preview() to 15.0
    ),
    maxTeamPoints = 25.0,
    highestDriverPosition = 1
)

fun SprintRaceModel.DriverResult.Companion.preview() = SprintRaceModel.DriverResult(
    result = SprintRaceResult.preview()
)