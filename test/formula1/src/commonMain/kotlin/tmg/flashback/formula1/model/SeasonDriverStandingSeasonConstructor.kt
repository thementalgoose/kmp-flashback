package tmg.flashback.formula1.model

fun SeasonDriverStandingSeasonConstructor.Companion.model(
    constructor: Constructor = Constructor.model(),
    points: Double = 1.0
): SeasonDriverStandingSeasonConstructor = SeasonDriverStandingSeasonConstructor(
    constructor = constructor,
    points = points
)