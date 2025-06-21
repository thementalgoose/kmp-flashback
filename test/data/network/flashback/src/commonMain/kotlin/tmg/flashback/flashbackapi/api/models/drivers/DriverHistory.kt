package tmg.flashback.flashbackapi.api.models.drivers

fun DriverHistory.Companion.model(
    driver: Driver = Driver.model(),
    standings: Map<String, DriverHistoryStanding> = mapOf(
        "s2020r1" to DriverHistoryStanding.model()
    )
): DriverHistory = DriverHistory(
    driver = driver,
    standings = standings
)