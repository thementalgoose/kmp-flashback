package tmg.flashback.formula1.model

fun SeasonConstructorStandings.Companion.model(
    standings: List<SeasonConstructorStandingSeason> = listOf(
        SeasonConstructorStandingSeason.model()
    )
): SeasonConstructorStandings = SeasonConstructorStandings(
    standings = standings
)