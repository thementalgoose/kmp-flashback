package tmg.flashback.persistence.flashback.models.standings

import tmg.flashback.persistence.flashback.models.drivers.Driver
import tmg.flashback.persistence.flashback.models.drivers.model

fun DriverStandingWithConstructors.Companion.model(
    standing: DriverStanding = DriverStanding.model(),
    driver: Driver = Driver.model(),
    constructors: List<DriverStandingConstructorWithConstructor> = listOf(
        DriverStandingConstructorWithConstructor.model()
    )
): DriverStandingWithConstructors = DriverStandingWithConstructors(
    standing = standing,
    driver = driver,
    constructors = constructors
)