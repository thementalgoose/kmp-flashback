package tmg.flashback.infrastructure.datetime

import kotlinx.datetime.LocalDateTime
import kotlin.time.Clock.System
import kotlin.time.ExperimentalTime

interface TimeManager {
    val now: LocalDateTime
    val nowMillis: Long
}

@OptIn(ExperimentalTime::class)
internal class TimeManagerImpl(): TimeManager {
    override val now: LocalDateTime
        get() = LocalDateTime.now()

    override val nowMillis: Long
        get() = System.now().toEpochMilliseconds()
}