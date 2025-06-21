package tmg.flashback.persistence.flashback.models.race

import tmg.flashback.persistence.flashback.models.constructors.Constructor
import tmg.flashback.persistence.flashback.models.constructors.model
import tmg.flashback.persistence.flashback.models.drivers.Driver
import tmg.flashback.persistence.flashback.models.drivers.model

fun RaceDriverResult.Companion.model(
    raceResult: RaceResult = RaceResult.model(),
    driver: Driver = Driver.model(),
    constructor: Constructor = Constructor.model()
): RaceDriverResult = RaceDriverResult(
    raceResult = raceResult,
    driver = driver,
    constructor = constructor
)