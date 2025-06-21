package tmg.flashback.persistence.flashback.models.overview

fun Event.Companion.model(
    season: Int = 2020,
    label: String = "label",
    type: String = "testing",
    date: String = "2020-10-12"
): Event = Event(
    season = season,
    label = label,
    type = type,
    date = date
)