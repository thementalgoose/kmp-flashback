package tmg.flashback.formula1.model

import kotlinx.datetime.LocalTime
import tmg.flashback.formula1.utils.extend
import kotlin.math.abs

data class LapTime(
    val hours: Int = 0,
    val mins: Int = 0,
    val seconds: Int = 0,
    val millis: Int = 0
) {

    constructor(): this(
        -1,
        -1,
        -1,
        -1
    )

    constructor(millis: Int): this(
        LocalTime.fromNanosecondOfDay(millis * 1_000_000L).hour,
        LocalTime.fromNanosecondOfDay(millis * 1_000_000L).minute,
        LocalTime.fromNanosecondOfDay(millis * 1_000_000L).second,
        LocalTime.fromNanosecondOfDay(millis * 1_000_000L).nanosecond / 1_000_000
    )

    val noTime: Boolean
        get() = hours == -1 || mins == -1 || seconds == -1 || millis == -1

    val totalMillis: Int
        get() = if (noTime) 0 else (hours * 1000 * 60 * 60) +
                (mins * 1000 * 60) +
                (seconds * 1000) +
                millis

    val time: String
        get() = when {
            noTime -> {
                "No time"
            }
            hours != 0 -> {
                "${hours}:${mins.extend(2)}:${seconds.extend(2)}.${millis.extend(3)}"
            }
            mins != 0 -> {
                "${mins}:${seconds.extend(2)}.${millis.extend(3)}"
            }
            else -> {
                "${seconds}.${millis.extend(3)}"
            }
        }

    fun deltaTo(lapTime: LapTime?): String? {
        if (lapTime == null) {
            return null
        }
        val diff = lapTime.totalMillis - this.totalMillis
        if (diff == 0) {
            return "0.000"
        }
        val newTime = LapTime(abs(diff))
        return "${if (diff < 0) "-" else "+"}$newTime"
    }

    override fun equals(other: Any?): Boolean {
        return this.time == (other as? LapTime)?.time
    }

    override fun hashCode(): Int {
        return this.time.hashCode()
    }

    override fun toString(): String {
        return time
    }

    val contentDescription: String
        get() = when {
            noTime -> {
                "No time"
            }
            hours != 0 -> {
                "$hours hours, $mins minutes, ${seconds.extend(2)} seconds and ${millis.extend(3)} milliseconds"
            }
            mins != 0 -> {
                "$mins minutes, ${seconds.extend(2)} seconds and ${millis.extend(3)} milliseconds"
            }
            else -> {
                "$seconds seconds and ${millis.extend(3)} milliseconds"
            }
        }

    companion object {
        val noTime: LapTime = LapTime()
    }
}

