package tmg.flashback.formula1.model

fun QualifyingResult.Companion.model(
    driver: DriverEntry = DriverEntry.model(),
    lapTime: LapTime? = LapTime.model(),
    position: Int = 1,
): QualifyingResult = QualifyingResult(
    entry = driver,
    lapTime = lapTime,
    position = position
)