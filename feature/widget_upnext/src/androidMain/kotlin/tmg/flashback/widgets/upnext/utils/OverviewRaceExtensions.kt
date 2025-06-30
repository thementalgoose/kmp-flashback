package tmg.flashback.widgets.upnext.utils

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.format
import tmg.flashback.formula1.model.OverviewRace
import tmg.flashback.formula1.model.Schedule
import tmg.flashback.formula1.model.Timestamp
import tmg.flashback.infrastructure.datetime.dateFormatEEEE
import tmg.flashback.infrastructure.datetime.dateFormatMMM
import tmg.flashback.infrastructure.datetime.now
import tmg.flashback.infrastructure.datetime.startOfWeek
import tmg.flashback.infrastructure.datetime.timeFormatHHmm

internal fun OverviewRace.raceSchedule(): Schedule? {
    return this.schedule.firstOrNull { it.label.lowercase() == "race" }
}

internal fun OverviewRace.labels(): Pair<String, String> {
    val deviceTime = Timestamp(this.date, this.time ?: LocalTime(12, 0)).deviceLocalDateTime

    val sameWeek = LocalDate.now().startOfWeek() == deviceTime.date.startOfWeek()
    val timeString = this.time?.let { deviceTime.time.format(timeFormatHHmm) } ?: ""
    return if (sameWeek) {
        deviceTime.date.format(dateFormatEEEE) to timeString
    } else {
        "${deviceTime.dayOfMonth} ${deviceTime.date.format(dateFormatMMM)}" to timeString
    }
}
