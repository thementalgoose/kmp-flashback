package tmg.flashback.formula1.model

fun SeasonDriverStandings.Companion.model(
    standings: List<SeasonDriverStandingSeason> = listOf(
        SeasonDriverStandingSeason.model()
    )
): SeasonDriverStandings = SeasonDriverStandings(
    standings = standings
)