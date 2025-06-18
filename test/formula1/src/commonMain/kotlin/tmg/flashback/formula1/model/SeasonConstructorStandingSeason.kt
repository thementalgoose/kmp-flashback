package tmg.flashback.formula1.model

fun SeasonConstructorStandingSeason.Companion.model(
    season: Int = 2020,
    constructor: Constructor = Constructor.model(),
    points: Double = 1.0,
    inProgress: Boolean = true,
    inProgressName: String? = "name",
    inProgressRound: Int? = 1,
    races: Int = 1,
    championshipPosition: Int? = 1,
    drivers: List<SeasonConstructorStandingSeasonDriver> = listOf(
        SeasonConstructorStandingSeasonDriver.model()
    ),
): SeasonConstructorStandingSeason = SeasonConstructorStandingSeason(
    season = season,
    constructor = constructor,
    points = points,
    inProgress = inProgress,
    inProgressName = inProgressName,
    inProgressRound = inProgressRound,
    races = races,
    championshipPosition = championshipPosition,
    drivers = drivers
)