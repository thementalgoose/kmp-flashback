package tmg.flashback.infrastructure.datetime

import kotlinx.datetime.LocalTime

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