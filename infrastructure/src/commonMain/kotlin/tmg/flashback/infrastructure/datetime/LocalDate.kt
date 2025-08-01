package tmg.flashback.infrastructure.datetime

import kotlin.time.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.minus
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

fun daysBetween(start: LocalDate, end: LocalDate): Int {
    return start.daysUntil(end)
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

fun LocalDate.startOfWeek(): LocalDate {
    val current = this.dayOfWeek
    return this.minus(current.ordinal, DateTimeUnit.DAY)
}