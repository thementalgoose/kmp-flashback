package tmg.flashback.infrastructure.datetime

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun LocalDateTime.Companion.now(): LocalDateTime {
    return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
}

fun LocalDate.Companion.now(): LocalDate {
    return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
}

fun LocalTime.Companion.now(): LocalTime {
    return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time
}


@Throws(IllegalArgumentException::class)
fun requireFromDate(date: String): LocalDate {
    return dateFormats.firstNotNullOfOrNull { pattern ->
        try {
            LocalDate.parse(date, pattern)
        } catch (e: RuntimeException) {
            null
        }
    } ?: throw IllegalArgumentException("Failed to parse time string $date with no supported patterns.")
}
fun fromDate(date: String?): LocalDate? {
    if (date == null) {
        return null
    }
    return try {
        return requireFromDate(date)
    } catch (e: Exception) {
        null
    }
}

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