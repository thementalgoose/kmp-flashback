package tmg.flashback.persistence.flashback.models.constructors

fun ConstructorSeason.Companion.model(
    constructorId: String = "constructorId",
    season: Int = 2020,
    championshipStanding: Int? = 1,
    points: Double = 1.0,
    inProgress: Boolean = true,
    inProgressMinPosition: Int? = 1,
    inProgressMaxPosition: Int? = 1,
    races: Int = 1,
): ConstructorSeason = ConstructorSeason(
    constructorId = constructorId,
    season = season,
    championshipStanding = championshipStanding,
    points = points,
    inProgress = inProgress,
    inProgressMinPosition = inProgressMinPosition,
    inProgressMaxPosition = inProgressMaxPosition,
    races = races,
)