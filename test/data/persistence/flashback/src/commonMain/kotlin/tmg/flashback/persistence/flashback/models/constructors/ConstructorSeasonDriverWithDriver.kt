package tmg.flashback.persistence.flashback.models.constructors

import tmg.flashback.persistence.flashback.models.drivers.Driver
import tmg.flashback.persistence.flashback.models.drivers.model

fun ConstructorSeasonDriverWithDriver.Companion.model(
    results: ConstructorSeasonDriver = ConstructorSeasonDriver.model(),
    driver: Driver = Driver.model()
): ConstructorSeasonDriverWithDriver = ConstructorSeasonDriverWithDriver(
    results = results,
    driver = driver
)