package tmg.flashback.formula1.model

fun LineupSeason.Companion.model(
    season: Int = 2020,
    driversToConstructors: Map<Driver, Constructor> = mapOf(
        Driver.model() to Constructor.model()
    )
): LineupSeason = LineupSeason(
    season = season,
    driversToConstructors = driversToConstructors
)