package tmg.flashback.infrastructure.datetime

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.MonthNames.Companion.ENGLISH_ABBREVIATED
import kotlinx.datetime.format.MonthNames.Companion.ENGLISH_FULL
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char

val dateFormatMMDD = LocalDate.Format {
    monthNumber(Padding.ZERO)
    char('-')
    dayOfMonth(Padding.ZERO)
}
val dateFormatDMMM = LocalDate.Format {
    dayOfMonth(Padding.NONE)
    char(' ')
    monthName(ENGLISH_ABBREVIATED)
}
val dateFormatEEEEDMMM = LocalDate.Format {
    dayOfWeek(DayOfWeekNames.ENGLISH_FULL)
    char(' ')
    dayOfMonth(Padding.NONE)
    char(' ')
    monthName(ENGLISH_ABBREVIATED)
}
val dateFormatYYYYMD = LocalDate.Format {
    year()
    char('-')
    monthNumber(Padding.NONE)
    char('-')
    dayOfMonth(Padding.NONE)
}
val dateFormatYYYYMMD = LocalDate.Format {
    year()
    char('-')
    monthNumber(Padding.ZERO)
    char('-')
    dayOfMonth(Padding.NONE)
}
val dateFormatYYYYMMDD = LocalDate.Format {
    year()
    char('-')
    monthNumber(Padding.ZERO)
    char('-')
    dayOfMonth(Padding.ZERO)
}
val dateFormatYYYYMMMD = LocalDate.Format {
    year()
    char('-')
    monthName(ENGLISH_ABBREVIATED)
    char('-')
    dayOfMonth(Padding.NONE)
}
val dateFormatYYYYMMMDD = LocalDate.Format {
    year()
    char('-')
    monthName(ENGLISH_ABBREVIATED)
    char('-')
    dayOfMonth(Padding.ZERO)
}
val dateFormatYYYYMMMMD = LocalDate.Format {
    year()
    char('-')
    monthName(ENGLISH_FULL)
    char('-')
    dayOfMonth(Padding.ZERO)
}
val dateFormatYYYYMMMMDD = LocalDate.Format {
    year()
    char('-')
    monthName(ENGLISH_FULL)
    char('-')
    dayOfMonth(Padding.ZERO)
}

val dateFormats = listOf(
    dateFormatYYYYMD,
    dateFormatYYYYMMD,
    dateFormatYYYYMMDD,
    dateFormatYYYYMMMD,
    dateFormatYYYYMMMDD,
    dateFormatYYYYMMMMD,
    dateFormatYYYYMMMMDD,
)


val timeFormatHHmmss = LocalTime.Format {
    hour(Padding.ZERO)
    char(':')
    minute(Padding.ZERO)
    char(':')
    second(Padding.ZERO)
}
val timeFormatHHmmssZ = LocalTime.Format {
    hour(Padding.ZERO)
    char(':')
    minute(Padding.ZERO)
    char(':')
    second(Padding.ZERO)
    char('Z')
}
val timeFormatHHmm = LocalTime.Format {
    hour(Padding.ZERO)
    char(':')
    minute(Padding.ZERO)
}

val timeFormats = listOf(
    timeFormatHHmm,
    timeFormatHHmmss,
    timeFormatHHmmssZ
)