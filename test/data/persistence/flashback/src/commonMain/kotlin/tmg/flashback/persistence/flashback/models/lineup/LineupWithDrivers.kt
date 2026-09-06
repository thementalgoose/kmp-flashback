package tmg.flashback.persistence.flashback.models.lineup

import tmg.flashback.persistence.flashback.models.constructors.Constructor
import tmg.flashback.persistence.flashback.models.constructors.model
import tmg.flashback.persistence.flashback.models.drivers.Driver
import tmg.flashback.persistence.flashback.models.drivers.model

fun LineupWithDrivers.Companion.model(
    lineup: Lineup = Lineup.model(),
    driver: Driver = Driver.model(),
    constructor: Constructor = Constructor.model()
) = LineupWithDrivers(
    lineup = lineup,
    driver = driver,
    constructor = constructor
)