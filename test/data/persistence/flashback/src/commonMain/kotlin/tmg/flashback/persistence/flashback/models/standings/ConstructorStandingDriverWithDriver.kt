package tmg.flashback.persistence.flashback.models.standings

import tmg.flashback.persistence.flashback.models.drivers.Driver
import tmg.flashback.persistence.flashback.models.drivers.model

fun ConstructorStandingDriverWithDriver.Companion.model(
    standing: ConstructorStandingDriver = ConstructorStandingDriver.model(),
    driver: Driver = Driver.model()
): ConstructorStandingDriverWithDriver = ConstructorStandingDriverWithDriver(
    standing = standing,
    driver = driver
)