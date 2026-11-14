package tmg.flashback.infrastructure.datetime

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlin.time.Duration

fun Duration.Companion.between(
    ref1: LocalDateTime,
    ref2: LocalDateTime,
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): Duration {
    return ref2.toInstant(timeZone) - ref1.toInstant(timeZone)
}

fun Duration.toMillis() = this.inWholeMilliseconds