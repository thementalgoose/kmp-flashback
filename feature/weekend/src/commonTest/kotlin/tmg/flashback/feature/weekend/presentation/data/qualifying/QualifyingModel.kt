package tmg.flashback.feature.weekend.presentation.data.qualifying

import tmg.flashback.formula1.model.DriverEntry
import tmg.flashback.formula1.model.LapTime
import tmg.flashback.formula1.model.QualifyingResult
import tmg.flashback.formula1.model.RaceResult
import tmg.flashback.formula1.model.model

fun QualifyingModel.Q1Q2Q3.Companion.model(
    driver: DriverEntry = DriverEntry.model(),
    finalQualifyingPosition: Int? = 1,
    q1: QualifyingResult? = QualifyingResult.model(lapTime = LapTime.model(0, 1, 2, 1)),
    q2: QualifyingResult? = QualifyingResult.model(lapTime = LapTime.model(0, 1, 2, 2)),
    q3: QualifyingResult? = QualifyingResult.model(lapTime = LapTime.model(0, 1, 2, 3)),
    grid: Int? = RaceResult.model().grid,
    sprintRaceGrid: Int? = null
): QualifyingModel.Q1Q2Q3 = QualifyingModel.Q1Q2Q3(
    driver = driver,
    finalQualifyingPosition = finalQualifyingPosition,
    q1 = q1,
    q2 = q2,
    q3 = q3,
    grid = grid,
    sprintRaceGrid = sprintRaceGrid,
)

fun QualifyingModel.Q1Q2.Companion.model(
    driver: DriverEntry = DriverEntry.model(),
    finalQualifyingPosition: Int? = 1,
    q1: QualifyingResult? = QualifyingResult.model(lapTime = LapTime.model(0, 1, 2, 1)),
    q2: QualifyingResult? = QualifyingResult.model(lapTime = LapTime.model(0, 1, 2, 2))
): QualifyingModel.Q1Q2 = QualifyingModel.Q1Q2(
    driver = driver,
    finalQualifyingPosition = finalQualifyingPosition,
    q1 = q1,
    q2 = q2
)

fun QualifyingModel.Q1.Companion.model(
    driver: DriverEntry = DriverEntry.model(),
    finalQualifyingPosition: Int? = 1,
    q1: QualifyingResult? = QualifyingResult.model(lapTime = LapTime.model(0, 1, 2, 1))
): QualifyingModel.Q1 = QualifyingModel.Q1(
    driver = driver,
    finalQualifyingPosition = finalQualifyingPosition,
    q1 = q1
)