package tmg.flashback.persistence.flashback.models.overview

fun Overview.Companion.model(
    season: Int = 2020,
    round: Int = 1,
    name: String = "name",
    circuitId: String = "circuitId",
    laps: String? = "12",
    date: String = "2020-10-12",
    time: String? = "12:34",
    hasRace: Boolean = false,
    hasSprint: Boolean = false,
    hasQualifying: Boolean = true,
    id: String = "${season}_${round}"
): Overview = Overview(
    season = season,
    round = round,
    name = name,
    circuitId = circuitId,
    laps = laps,
    date = date,
    time = time,
    hasRace = hasRace,
    hasSprint = hasSprint,
    hasQualifying = hasQualifying,
    id = id
)