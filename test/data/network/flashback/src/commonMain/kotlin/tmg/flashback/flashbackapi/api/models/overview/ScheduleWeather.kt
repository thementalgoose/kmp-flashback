package tmg.flashback.flashbackapi.api.models.overview

fun ScheduleWeather.Companion.model(
    rainPercent: Double = 0.5,
    windMs: Double = 1.0,
    windBearing: Int = 180,
    tempMaxC: Double = 20.0,
    tempMinC: Double = 19.0,
    summary: List<String> = listOf("clear_sky")
): ScheduleWeather = ScheduleWeather(
    rainPercent = rainPercent,
    windMs = windMs,
    windBearing = windBearing,
    tempMaxC = tempMaxC,
    tempMinC = tempMinC,
    summary = summary
)