package tmg.flashback.widgets.upnext.utils

import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import tmg.flashback.formula1.model.Schedule
import tmg.flashback.infrastructure.datetime.dateFormatEEEE
import tmg.flashback.infrastructure.datetime.dateFormatMMM
import tmg.flashback.infrastructure.datetime.now
import tmg.flashback.infrastructure.datetime.startOfWeek
import tmg.flashback.infrastructure.datetime.timeFormatHHmm

internal fun Schedule.labels(): Pair<String, String> {
    val deviceTime = this.timestamp.deviceLocalDateTime

    val sameWeek = LocalDate.now().startOfWeek() == deviceTime.date.startOfWeek()
    return if (sameWeek) {
        deviceTime.date.format(dateFormatEEEE) to deviceTime.time.format(timeFormatHHmm)
    } else {
        "${deviceTime.dayOfMonth} ${deviceTime.date.format(dateFormatMMM)}" to deviceTime.time.format(timeFormatHHmm)
    }
}