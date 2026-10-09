package tmg.flashback.feature.weekend.presentation.data.sprint_qualifying

import tmg.flashback.formula1.model.DriverEntry
import tmg.flashback.formula1.model.LapTime
import tmg.flashback.formula1.model.RaceResult
import tmg.flashback.formula1.model.SprintQualifyingResult
import tmg.flashback.formula1.model.model

fun SprintQualifyingModel.Result.Companion.model(
    driver: DriverEntry = DriverEntry.model(),
    finalQualifyingPosition: Int? = 1,
    sq1: SprintQualifyingResult? = SprintQualifyingResult.model(lapTime = LapTime.model(0, 1, 2, 1)),
    sq2: SprintQualifyingResult? = SprintQualifyingResult.model(lapTime = LapTime.model(0, 1, 2, 2)),
    sq3: SprintQualifyingResult? = SprintQualifyingResult.model(lapTime = LapTime.model(0, 1, 2, 3)),
    grid: Int? = RaceResult.model().grid
): SprintQualifyingModel.Result = SprintQualifyingModel.Result(
    driver = driver,
    finalQualifyingPosition = finalQualifyingPosition,
    sq1 = sq1,
    sq2 = sq2,
    sq3 = sq3,
    grid = grid
)