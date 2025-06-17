package tmg.flashback.formula1.utils

import kotlinx.datetime.LocalTime
import tmg.flashback.formula1.model.LapTime

fun LapTime.addDelta(hours: Int = 0, mins: Int = 0, seconds: Int = 0, millis: Int = 0): LapTime {
    val lapMillis: Int = LapTime(
        hours,
        mins,
        seconds,
        millis
    ).totalMillis
    return this.add(lapMillis)
}

fun LapTime.add(millis: Int): LapTime {
    return LapTime(this.totalMillis + millis)
}

fun LapTime.addDelta(data: String?): LapTime {
    var time: String = data ?: ""
    if (data?.contains("+") == true) {
        time = time.replace("+", "")
    }
    if (data?.contains("-") == true) {
        time = time.replace("-", "")
    }
    return when (time.split(":").size) {
        3 -> addDelta(
            hours = time.split(":")[0].toIntOrNull() ?: 0,
            mins = time.split(":")[1].toIntOrNull() ?: 0,
            seconds = time.split(":")[2].split(".")[0].toIntOrNull() ?: 0,
            millis = time.split(".")[1].toIntOrNull() ?: 0
        )
        2 -> addDelta(
            mins = time.split(":")[0].toIntOrNull() ?: 0,
            seconds = time.split(":")[1].split(".")[0].toIntOrNull() ?: 0,
            millis = time.split(".")[1].toIntOrNull() ?: 0
        )
        else -> addDelta(
            seconds = time.split(".")[0].toIntOrNull() ?: 0,
            millis = time.split(".")[1].toIntOrNull() ?: 0
        )
    }
}

fun String.toLocalTime(): LocalTime? {
    var time: String = this
    if (this.contains("+")) {
        time = this.replace("+", "")
    }
    if (this.contains("-")) {
        time = this.replace("-", "")
    }
    try {
        if (time.matches(tmg.flashback.formula1.enums.LapTimeFormats.SECOND_MILLIS.regex)) {
            val seconds = time.split(".")[0].toIntOrNull() ?: 0
            val millis = time.split(".")[1].toIntOrNull() ?: 0
            return LocalTime(0, 0, seconds, millis * 1_000_000)
        }
        if (time.matches(tmg.flashback.formula1.enums.LapTimeFormats.MIN_SECOND_MILLIS.regex)) {
            val mins = time.split(":")[0].toIntOrNull() ?: 0
            val seconds = time.split(":")[1].split(".")[0].toIntOrNull() ?: 0
            val millis = time.split(".")[1].toIntOrNull() ?: 0
            return LocalTime(0, mins, seconds, millis * 1_000_000)
        }
        if (time.matches(tmg.flashback.formula1.enums.LapTimeFormats.HOUR_MIN_SECOND_MILLIS.regex)) {
            val hours = time.split(":")[0].toIntOrNull() ?: 0
            val mins = time.split(":")[1].toIntOrNull() ?: 0
            val seconds = time.split(":")[2].split(".")[0].toIntOrNull() ?: 0
            val millis = time.split(".")[1].toIntOrNull() ?: 0
            return LocalTime(hours, mins, seconds, millis * 1_000_000)
        }
    }
    catch (e: IndexOutOfBoundsException) {
        return null
    }
    return null
}

fun String.toLapTime(): LapTime {
    val localTime = this.toLocalTime()
    return if (localTime != null) {
        LapTime(
            hours = localTime.hour,
            mins = localTime.minute,
            seconds = localTime.second,
            millis = localTime.nanosecond / 1_000_000
        )
    }
    else {
        LapTime.noTime
    }
}