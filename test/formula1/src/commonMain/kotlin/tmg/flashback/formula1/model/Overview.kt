package tmg.flashback.formula1.model

fun Overview.Companion.model(
    season: Int = 2020,
    overviewRaces: List<OverviewRace> = listOf(OverviewRace.model())
): Overview = Overview(
    season = season,
    overviewRaces = overviewRaces
)