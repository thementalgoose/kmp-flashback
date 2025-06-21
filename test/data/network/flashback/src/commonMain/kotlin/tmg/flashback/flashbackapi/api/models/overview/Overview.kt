package tmg.flashback.flashbackapi.api.models.overview

import tmg.flashback.flashbackapi.api.models.circuits.Circuit
import tmg.flashback.flashbackapi.api.models.circuits.model

fun OverviewRace.Companion.model(
    season: Int = 2020,
    round: Int = 1,
    name: String = "name",
    circuit: Circuit = Circuit.model(),
    date: String = "2020-10-12",
    time: String? = "12:34",
    laps: String? = "12",
    hasQualifying: Boolean = true,
    hasSprint: Boolean = false,
    hasRace: Boolean = false,
    schedule: List<Schedule>? = listOf(
        Schedule.model()
    )
): OverviewRace = OverviewRace(
    season = season,
    round = round,
    name = name,
    circuit = circuit,
    date = date,
    laps = laps,
    time = time,
    hasQualifying = hasQualifying,
    hasRace = hasRace,
    hasSprint = hasSprint,
    schedule = schedule
)