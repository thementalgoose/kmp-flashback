package tmg.flashback.formula1.model

fun LineupDriverOverview.Companion.model(
    driver: Driver = Driver.model(),
    seasons: Map<Int, Constructor> = mapOf(2020 to Constructor.model())
): LineupDriverOverview = LineupDriverOverview(
    driver = driver,
    seasons = seasons
)