package tmg.flashback.formula1.model

import tmg.flashback.formula1.model.QualifyingType.Q1
import tmg.flashback.formula1.model.QualifyingType.Q2
import tmg.flashback.formula1.model.QualifyingType.Q3

fun Race.Companion.model(
    raceInfo: RaceInfo = RaceInfo.model(),
    qualifying: List<QualifyingRound> = listOf(
        QualifyingRound.model(label = Q1, order = 1, results = listOf(
            QualifyingResult.model(lapTime = LapTime.model(0, 1, 2, 1))
        )),
        QualifyingRound.model(label = Q2, order = 2, results = listOf(
            QualifyingResult.model(lapTime = LapTime.model(0, 1, 2, 2))
        )),
        QualifyingRound.model(label = Q3, order = 3, results = listOf(
            QualifyingResult.model(lapTime = LapTime.model(0, 1, 2, 3))
        ))
    ),
    sprint: SprintResult = SprintResult.model(),
    race: List<RaceResult> = listOf(
        RaceResult.model()
    ),
    schedule: List<Schedule> = listOf(
        Schedule.model()
    )
): Race = Race(
    raceInfo = raceInfo,
    qualifying = qualifying,
    sprint = sprint,
    race = race,
    schedule = schedule
)