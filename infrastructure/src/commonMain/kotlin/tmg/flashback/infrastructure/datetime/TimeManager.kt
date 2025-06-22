package tmg.flashback.infrastructure.datetime

import kotlinx.datetime.LocalDateTime

interface TimeManager {
    val now: LocalDateTime
}

internal class TimeManagerImpl(): TimeManager {
    override val now: LocalDateTime
        get() = LocalDateTime.now()
}