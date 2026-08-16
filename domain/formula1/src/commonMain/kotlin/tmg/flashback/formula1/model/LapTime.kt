package tmg.flashback.formula1.model

import kotlinx.datetime.LocalTime
import tmg.flashback.infrastructure.extensions.extend
import kotlin.math.abs

data class LapTime(
    val hours: Int = 0,
    val mins: Int = 0,
    val seconds: Int = 0,
    val millis: Int = 0,
    val millisPrecision: Int = 3,
) {

    constructor(): this(
        hours = -1,
        mins = -1,
        seconds = -1,
        millis = -1,
        millisPrecision = 3
    )

    constructor(
        millis: Int,
        millisPrecision: Int = 3,
    ): this(
        hours = LocalTime.fromNanosecondOfDay(millis * 1_000_000L).hour,
        mins = LocalTime.fromNanosecondOfDay(millis * 1_000_000L).minute,
        seconds = LocalTime.fromNanosecondOfDay(millis * 1_000_000L).second,
        millis = LocalTime.fromNanosecondOfDay(millis * 1_000_000L).nanosecond / 1_000_000,
        millisPrecision = millisPrecision
    )

    val noTime: Boolean
        get() = hours == -1 || mins == -1 || seconds == -1 || millis == -1

    val totalMillis: Int
        get() = if (noTime) 0 else (hours * 1000 * 60 * 60) +
                (mins * 1000 * 60) +
                (seconds * 1000) +
                millis

    val time: String
        get() {
            val millisString = millis.extend(3).take(millisPrecision)
            return when {
                noTime -> {
                    "No time"
                }
                hours != 0 -> {
                    "${hours}:${mins.extend(2)}:${seconds.extend(2)}.${millisString}"
                }
                mins != 0 -> {
                    "${mins}:${seconds.extend(2)}.${millisString}"
                }
                else -> {
                    "${seconds}.${millisString}"
                }
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

