package tmg.flashback.formula1.model

data class ScheduleWeather(
    val rainPercent: Double,
    val windMs: Double,
    val windBearing: Int,
    val tempMaxC: Double,
    val tempMinC: Double,
    val summary: List<WeatherType>
) {

    val windMph: Double by lazy { windMs * 2.23694 }

    val windKph: Double by lazy { windMs * 3.6 }

    val tempAverageC: Double by lazy {
        (tempMinC + ((tempMaxC - tempMinC) / 2))
    }

    val tempMaxF: Double by lazy { tempMaxC.toF() }

    val tempMinF: Double by lazy { tempMinC.toF() }

    val tempAverageF: Double by lazy { tempAverageC.toF() }

    private fun Double.toF(): Double {
        return (this * 1.8) + 32.0
    }

    companion object
}