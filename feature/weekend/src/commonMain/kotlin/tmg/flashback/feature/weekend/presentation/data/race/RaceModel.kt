package tmg.flashback.feature.weekend.presentation.data.race

import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.Driver
import tmg.flashback.formula1.model.RaceResult
import tmg.flashback.formula1.preview.preview

sealed class RaceModel(
    val id: String
) {
    data class ConstructorResult(
        val constructor: Constructor,
        val position: Int?,
        val points: Double,
        val drivers: List<Pair<Driver, Double>>,
        val maxTeamPoints: Double,
        val highestDriverPosition: Int
    ): RaceModel(id = "constructor-${constructor.id}") {
        companion object
    }

    data class DriverPodium(
        val p1: RaceResult,
        val p2: RaceResult,
        val p3: RaceResult
    ): RaceModel(id = "podium") {
        companion object
    }

    data class DriverResult(
        val result: RaceResult
    ): RaceModel(id = "driver-${result.entry.driver.id}") {
        companion object
    }
}

fun RaceModel.ConstructorResult.Companion.preview() = RaceModel.ConstructorResult(
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

fun RaceModel.DriverPodium.Companion.preview() = RaceModel.DriverPodium(
    p1 = RaceResult.preview("1"),
    p2 = RaceResult.preview("2"),
    p3 = RaceResult.preview("3"),
)

fun RaceModel.DriverResult.Companion.preview() = RaceModel.DriverResult(
    result = RaceResult.preview("4")
)