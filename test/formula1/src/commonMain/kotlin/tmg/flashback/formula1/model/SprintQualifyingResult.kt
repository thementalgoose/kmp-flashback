package tmg.flashback.formula1.model

fun SprintQualifyingResult.Companion.model(
    driver: DriverEntry = DriverEntry.model(),
    lapTime: LapTime? = LapTime.model(),
    position: Int = 1
): SprintQualifyingResult = SprintQualifyingResult(
    entry = driver,
    lapTime = lapTime,
    position = position
)