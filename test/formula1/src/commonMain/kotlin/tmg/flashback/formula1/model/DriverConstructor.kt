package tmg.flashback.formula1.model

fun DriverEntry.Companion.model(
    driver: Driver = Driver.model(),
    constructor: Constructor = Constructor.model()
): DriverEntry = DriverEntry(
    driver = driver,
    constructor = constructor
)