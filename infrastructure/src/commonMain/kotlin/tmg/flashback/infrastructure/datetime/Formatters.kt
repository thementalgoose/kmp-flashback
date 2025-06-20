package tmg.flashback.infrastructure.datetime

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char

val dateFormatYYYYMMDD = LocalDate.Format {
    year()
    char('-')
    monthNumber(Padding.ZERO)
    char('-')
    dayOfMonth(Padding.ZERO)
}
val timeFormatHHmmss = LocalTime.Format {
    hour(Padding.ZERO)
    char(':')
    minute(Padding.ZERO)
    char(':')
    second(Padding.ZERO)
}
val timeFormatHHmm = LocalTime.Format {
    hour(Padding.ZERO)
    char(':')
    minute(Padding.ZERO)
}