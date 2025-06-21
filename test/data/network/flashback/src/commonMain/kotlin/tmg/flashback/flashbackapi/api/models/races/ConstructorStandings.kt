package tmg.flashback.flashbackapi.api.models.races

fun ConstructorStandings.Companion.model(
    constructorId: String = "constructorId",
    points: Double = 1.0,
    inProgress: Boolean? = true,
    inProgressInfo: InProgressInfo = InProgressInfo.model(),
    races: Int = 1,
    position: Int? = 1,
    drivers: Map<String, Double> = mapOf(
        "driverId" to 1.0
    )
): ConstructorStandings = ConstructorStandings(
    constructorId = constructorId,
    points = points,
    inProgress = inProgress,
    inProgressInfo = inProgressInfo,
    races = races,
    position = position,
    drivers = drivers
)