package tmg.flashback.persistence.flashback.models.race

import tmg.flashback.persistence.flashback.models.constructors.Constructor
import tmg.flashback.persistence.flashback.models.constructors.model
import tmg.flashback.persistence.flashback.models.drivers.Driver
import tmg.flashback.persistence.flashback.models.drivers.model

fun SprintRaceDriverResult.Companion.model(
    raceResult: SprintRaceResult = SprintRaceResult.model(),
    driver: Driver = Driver.model(),
    constructor: Constructor = Constructor.model()
): SprintRaceDriverResult = SprintRaceDriverResult(
    sprintResult = raceResult,
    driver = driver,
    constructor = constructor
)