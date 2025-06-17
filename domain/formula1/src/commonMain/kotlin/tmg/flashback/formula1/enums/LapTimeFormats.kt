package tmg.flashback.formula1.enums

enum class LapTimeFormats(
    val regex: Regex
) {
    SECOND_MILLIS(Regex("([0-5][0-9]|[0-9]).[0-9][0-9][0-9]")),
    MIN_SECOND_MILLIS(Regex("([0-5][0-9]|[0-9]):[0-5][0-9].[0-9][0-9][0-9]")),
    HOUR_MIN_SECOND_MILLIS(Regex("([2][0-3]|[0-9]|[0-1][0-9]):([0-5][0-9]|[0-9]):[0-5][0-9].[0-9][0-9][0-9]"));
}