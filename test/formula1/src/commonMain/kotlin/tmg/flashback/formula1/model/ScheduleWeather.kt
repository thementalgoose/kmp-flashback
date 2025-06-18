package tmg.flashback.formula1.model

fun ScheduleWeather.Companion.model(
    rainPercent: Double = 0.5,
    windMs: Double = 1.0,
    windBearing: Int = 180,
    tempMaxC: Double = 20.0,
    tempMinC: Double = 19.0,
    summary: List<WeatherType> = listOf(WeatherType.CLEAR_SKY)
): ScheduleWeather = ScheduleWeather(
    rainPercent = rainPercent,
    windMs = windMs,
    windBearing = windBearing,
    tempMaxC = tempMaxC,
    tempMinC = tempMinC,
    summary = summary,
)