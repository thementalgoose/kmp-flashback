package tmg.flashback.feature.weekend.presentation.data.race

import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.Driver
import tmg.flashback.formula1.model.RaceResult
import tmg.flashback.formula1.model.model

fun RaceModel.DriverResult.Companion.model(
    result: RaceResult = RaceResult.model()
): RaceModel.DriverResult = RaceModel.DriverResult(
    result = result
)

fun RaceModel.ConstructorResult.Companion.model(
    constructor: Constructor = Constructor.model(),
    position: Int = 1,
    points: Double = 1.0,
    drivers: List<Pair<Driver, Double>> = listOf(
        Driver.model() to 1.0
    ),
    maxTeamPoints: Double = 45.0,
    highestDriverPosition: Int = 1,
): RaceModel.ConstructorResult = RaceModel.ConstructorResult(
    constructor = constructor,
    position = position,
    points = points,
    drivers = drivers,
    maxTeamPoints = maxTeamPoints,
    highestDriverPosition = highestDriverPosition,
)