package tmg.flashback.formula1.model

fun RaceConstructorStandings.Companion.model(
    points: Double = 1.0,
    constructor: Constructor = Constructor.model()
): RaceConstructorStandings = RaceConstructorStandings(
    points = points,
    constructor = constructor
)