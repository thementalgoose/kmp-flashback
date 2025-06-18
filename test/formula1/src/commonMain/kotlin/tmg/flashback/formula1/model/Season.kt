package tmg.flashback.formula1.model

fun Season.Companion.model(
    season: Int = 2020,
    races: List<Race> = listOf(
        Race.model()
    ),
    event: List<Event> = listOf(
        Event.model()
    )
): Season = Season(
    season = season,
    races = races,
    event = event
)