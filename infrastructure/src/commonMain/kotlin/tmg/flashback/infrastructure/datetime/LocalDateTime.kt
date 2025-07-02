package tmg.flashback.infrastructure.datetime

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

/**
 * Returns a LocalDateTime that results from adding the value number of the specified unit to this date and time.
 */
fun LocalDateTime.plus(value: Int, unit: DateTimeUnit, timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDateTime =
    when (unit) {
        is DateTimeUnit.DateBased -> {
            this.date.plus(value, unit).atTime(time = this.time)
        }

        is DateTimeUnit.TimeBased -> {
            toInstant(timeZone = timeZone).plus(value, unit).toLocalDateTime(timeZone = timeZone)
        }
    }
