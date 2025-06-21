package tmg.flashback.flashbackapi.api.models.constructors

import tmg.flashback.flashbackapi.api.models.drivers.Driver
import tmg.flashback.flashbackapi.api.models.drivers.model

fun ConstructorHistoryStandingDriver.Companion.model(
    driver: Driver = Driver.model(),
    points: Double = 1.0,
    wins: Int? = 1,
    races: Int? = 1,
    podiums: Int? = 1,
    pointsFinishes: Int? = 1,
    pole: Int? = 1,
    championshipPosition: Int? = 1,
): ConstructorHistoryStandingDriver = ConstructorHistoryStandingDriver(
    driver = driver,
    points = points,
    wins = wins,
    races = races,
    podiums = podiums,
    pointsFinishes = pointsFinishes,
    pole = pole,
    championshipPosition = championshipPosition,
)