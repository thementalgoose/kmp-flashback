package tmg.flashback.flashbackapi.api.models.circuits

import tmg.flashback.flashbackapi.api.models.constructors.Constructor
import tmg.flashback.flashbackapi.api.models.constructors.model
import tmg.flashback.flashbackapi.api.models.drivers.Driver
import tmg.flashback.flashbackapi.api.models.drivers.model

fun CircuitPreviewPosition.Companion.model(
    position: Int = 1,
    driver: Driver = Driver.model(),
    constructor: Constructor = Constructor.model()
): CircuitPreviewPosition = CircuitPreviewPosition(
    position = position,
    driver = driver,
    constructor = constructor
)