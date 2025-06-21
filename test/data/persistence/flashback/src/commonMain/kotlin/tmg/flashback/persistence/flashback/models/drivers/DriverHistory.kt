package tmg.flashback.persistence.flashback.models.drivers

fun DriverHistory.Companion.model(
    driver: Driver = Driver.model(),
    seasons: List<DriverSeasonWithRaces> = listOf(
        DriverSeasonWithRaces.model()
    )
): DriverHistory = DriverHistory(
    driver = driver,
    seasons = seasons
)