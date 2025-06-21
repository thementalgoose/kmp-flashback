package tmg.flashback.persistence.flashback.models.standings

import tmg.flashback.persistence.flashback.models.constructors.Constructor
import tmg.flashback.persistence.flashback.models.constructors.model

fun DriverStandingConstructorWithConstructor.Companion.model(
    standing: DriverStandingConstructor = DriverStandingConstructor.model(),
    constructor: Constructor = Constructor.model()
): DriverStandingConstructorWithConstructor = DriverStandingConstructorWithConstructor(
    standing = standing,
    constructor = constructor
)