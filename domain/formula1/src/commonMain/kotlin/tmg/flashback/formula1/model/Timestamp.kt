package tmg.flashback.formula1.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.format
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import tmg.flashback.formula1.utils.now
import kotlin.time.Duration.Companion.seconds

data class Timestamp(
    private val originalDate: LocalDate,
    private val originalTime: LocalTime,
    private val currentTimeZone: TimeZone = TimeZone.currentSystemDefault(),
    private val utcInstant: Instant = originalDate.atTime(originalTime).toInstant(TimeZone.UTC)
) {
    val utcLocalDateTime: LocalDateTime
        get() = originalDate.atTime(originalTime)

    /**
     * Local date time object
     */
    val deviceLocalDateTime: LocalDateTime
        get() = utcInstant.toLocalDateTime(currentTimeZone)
    /**
     * Is the timestamp considered in the past based on UTC?
     */
    val isInPast: Boolean
        get() {
            return deviceLocalDateTime < LocalDateTime.now()
        }

    /**
     * Is the timestamp considered in the past based on UTC?
     */
    fun isInPastRelativeToo(deltaSeconds: Long): Boolean {
        val timestampInFuture = Clock.System.now()
        timestampInFuture.plus(deltaSeconds.seconds)
        return deviceLocalDateTime < timestampInFuture.toLocalDateTime(currentTimeZone)
    }

    /**
     * Is the timestamp considered to be today
     */
    val isToday: Boolean
        get() {
            return deviceLocalDateTime.date == LocalDate.now()
        }

    /**
     * Get a string representation of the date
     */
    fun string(): String {
        return this.originalDate.format(dateFormat) + this.originalTime.format(timeFormat)
    }

    private val dateFormat = LocalDate.Format {
        year()
        monthNumber(Padding.ZERO)
        dayOfMonth(Padding.ZERO)
    }
    private val timeFormat = LocalTime.Format {
        hour(Padding.ZERO)
        char(':')
        minute(Padding.ZERO)
        char(':')
        second(Padding.ZERO)
    }
}