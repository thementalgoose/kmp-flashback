package tmg.flashback.persistence.flashback.models.standings

fun ConstructorStanding.Companion.model(
    constructorId: String = "constructorId",
    season: Int = 2020,
    points: Double = 1.0,
    position: Int? = 1,
    inProgress: Boolean = true,
    inProgressName: String? = "name",
    inProgressRound: Int? = 1,
    races: Int = 1,
): ConstructorStanding = ConstructorStanding(
    constructorId = constructorId,
    season = season,
    points = points,
    position = position,
    inProgress = inProgress,
    inProgressName = inProgressName,
    inProgressRound = inProgressRound,
    races = races
)