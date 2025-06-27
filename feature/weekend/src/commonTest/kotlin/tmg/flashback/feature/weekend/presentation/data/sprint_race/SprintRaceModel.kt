package tmg.flashback.feature.weekend.presentation.data.sprint_race

import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.Driver
import tmg.flashback.formula1.model.LapTime
import tmg.flashback.formula1.model.SprintRaceResult
import tmg.flashback.formula1.model.model

fun SprintRaceModel.DriverResult.Companion.model(
    result: SprintRaceResult = SprintRaceResult.model(
        time = LapTime(1, 2, 3, 4)
    )
): SprintRaceModel.DriverResult = SprintRaceModel.DriverResult(
    result = result
)

fun SprintRaceModel.ConstructorResult.Companion.model(
    constructor: Constructor = Constructor.model(),
    position: Int? = 1,
    points: Double = 1.0,
    drivers: List<Pair<Driver, Double>> = listOf(
        Driver.model() to 1.0
    ),
    maxTeamPoints: Double = 15.0,
    highestDriverPosition: Int = 1,
): SprintRaceModel.ConstructorResult = SprintRaceModel.ConstructorResult(
    constructor = constructor,
    position = position,
    points = points,
    drivers = drivers,
    maxTeamPoints = maxTeamPoints,
    highestDriverPosition = highestDriverPosition
)