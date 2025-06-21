package tmg.flashback.persistence.flashback.models.standings

import tmg.flashback.persistence.flashback.models.constructors.Constructor
import tmg.flashback.persistence.flashback.models.constructors.model

fun ConstructorStandingWithDrivers.Companion.model(
    standing: ConstructorStanding = ConstructorStanding.model(),
    constructor: Constructor = Constructor.model(),
    drivers: List<ConstructorStandingDriverWithDriver> = listOf(
        ConstructorStandingDriverWithDriver.model()
    )
): ConstructorStandingWithDrivers = ConstructorStandingWithDrivers(
    standing = standing,
    constructor = constructor,
    drivers = drivers
)