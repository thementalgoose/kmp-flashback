package tmg.flashback.formula1.model

fun SeasonConstructorStandingSeasonDriver.Companion.model(
    driver: Driver = Driver.model(),
    points: Double = 1.0
): SeasonConstructorStandingSeasonDriver = SeasonConstructorStandingSeasonDriver(
    driver = driver,
    points = points
)