package tmg.flashback.infrastructure.datetime

import kotlinx.datetime.LocalTime
import kotlin.math.floor
import kotlin.math.roundToInt

@Throws(IllegalArgumentException::class)
fun requireFromTime(time: String, ): LocalTime {
    return timeFormats.firstNotNullOfOrNull { pattern ->
        try {
            LocalTime.parse(time, pattern)
        } catch (e: RuntimeException) {
            null
        }
    } ?: throw IllegalArgumentException("Failed to parse time string $time with no supported patterns.")
}

fun fromTime(time: String?): LocalTime? {
    if (time == null) {
        return null
    }
    return try {
        return requireFromTime(time)
    } catch (e: Exception) {
        null
    }
}

fun LocalTime.plusMinutes(minutes: Int): LocalTime {
    var totalMins = (this.hour * 60) + this.minute
    totalMins += minutes
    totalMins %= 1440

    val newHour = floor(totalMins / 60.0).roundToInt()
    val newMin = floor(totalMins % 60.0).roundToInt()
    return LocalTime(
        hour = newHour,
        minute = newMin,
        second = this.second,
        nanosecond = this.nanosecond
    )
}