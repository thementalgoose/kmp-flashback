package tmg.flashback.formula1.model

fun DriverHistory.Companion.model(
    driver: Driver = Driver.model(),
    standings: List<DriverHistorySeason> = listOf(
        DriverHistorySeason.model()
    )
): DriverHistory = DriverHistory(
    driver = driver,
    standings = standings
)