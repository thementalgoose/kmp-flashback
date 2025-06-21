package tmg.flashback.persistence.flashback.models.overview

fun Schedule.Companion.model(
    season: Int = 2020,
    round: Int = 1,
    label: String = "label",
    date: String = "2020-01-01",
    time: String = "12:34",
    rainPercent: Double? = 0.5,
    windMs: Double? = 1.0,
    windBearing: Int? = 180,
    tempMaxC: Double? = 20.0,
    tempMinC: Double? = 19.0,
    summary: String? = "clear_sky"
): Schedule = Schedule(
    season = season,
    round = round,
    label = label,
    date = date,
    time = time,
    rainPercent = rainPercent,
    windMs = windMs,
    windBearing = windBearing,
    tempMaxC = tempMaxC,
    tempMinC = tempMinC,
    summary = summary
)