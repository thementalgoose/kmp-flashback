package tmg.flashback.flashbackapi.api.models.races

import tmg.flashback.flashbackapi.api.models.overview.Schedule
import tmg.flashback.flashbackapi.api.models.overview.model

fun Race.Companion.model(
    data: RaceData = RaceData.model(),
    race: Map<String, RaceResult> = mapOf(
        "driverId" to RaceResult.model()
    ),
    qualifying: Map<String, QualifyingResult> = mapOf(
        "driverId" to QualifyingResult.model()
    ),
    schedule: List<Schedule>? = listOf(
        Schedule.model()
    )
): Race = Race(
    data = data,
    race = race,
    qualifying = qualifying,
    schedule = schedule
)