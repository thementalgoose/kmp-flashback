package tmg.flashback.widgets.upnext.utils

import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import tmg.flashback.formula1.model.Schedule
import tmg.flashback.infrastructure.datetime.dateFormatEEE
import tmg.flashback.infrastructure.datetime.dateFormatMMM
import tmg.flashback.infrastructure.datetime.now
import tmg.flashback.infrastructure.datetime.startOfWeek
import tmg.flashback.infrastructure.datetime.timeFormatHHmm
import tmg.flashback.infrastructure.extensions.ordinalAbbreviation

internal fun Schedule.labels(): Pair<String, String> {
    val deviceTime = this.timestamp.deviceLocalDateTime

    val sameWeek = LocalDate.now().startOfWeek() == deviceTime.date.startOfWeek()
    return if (sameWeek) {
        deviceTime.date.format(dateFormatEEE) to deviceTime.time.format(timeFormatHHmm)
    } else {
        "${deviceTime.dayOfMonth} ${deviceTime.date.format(dateFormatMMM)}" to deviceTime.time.format(timeFormatHHmm)
    }
}

internal fun LocalDate.weekRelativeLabel(): String {
    val sameWeek = LocalDate.now().startOfWeek() == this.startOfWeek()
    return if (sameWeek) {
        this.format(dateFormatEEE)
    } else {
        "${this.day} ${this.format(dateFormatMMM)}"
    }
}