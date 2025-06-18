package tmg.flashback.formula1.model

fun CircuitHistoryRaceResult.Companion.model(
    position: Int = 1,
    driver: Driver = Driver.model(),
    constructor: Constructor = Constructor.model()
): CircuitHistoryRaceResult = CircuitHistoryRaceResult(
    position = position,
    driver = driver,
    constructor = constructor
)