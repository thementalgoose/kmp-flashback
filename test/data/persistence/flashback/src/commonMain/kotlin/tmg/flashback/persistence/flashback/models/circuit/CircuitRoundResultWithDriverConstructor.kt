package tmg.flashback.persistence.flashback.models.circuit

import tmg.flashback.persistence.flashback.models.constructors.Constructor
import tmg.flashback.persistence.flashback.models.constructors.model
import tmg.flashback.persistence.flashback.models.drivers.Driver
import tmg.flashback.persistence.flashback.models.drivers.model

fun CircuitRoundResultWithDriverConstructor.Companion.model(
    result: CircuitRoundResult = CircuitRoundResult.model(),
    constructor: Constructor = Constructor.model(),
    driver: Driver = Driver.model()
): CircuitRoundResultWithDriverConstructor = CircuitRoundResultWithDriverConstructor(
    result = result,
    constructor = constructor,
    driver = driver
)