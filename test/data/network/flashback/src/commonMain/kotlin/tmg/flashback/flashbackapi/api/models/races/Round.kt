package tmg.flashback.flashbackapi.api.models.races

import tmg.flashback.flashbackapi.api.models.constructors.Constructor
import tmg.flashback.flashbackapi.api.models.constructors.model
import tmg.flashback.flashbackapi.api.models.drivers.Driver
import tmg.flashback.flashbackapi.api.models.drivers.model
import tmg.flashback.flashbackapi.api.models.overview.Schedule
import tmg.flashback.flashbackapi.api.models.overview.model

fun Round.Companion.model(
    drivers: Map<String, Driver> = mapOf(
        "driverId" to Driver.model()
    ),
    constructors: Map<String, Constructor> = mapOf(
        "constructorId" to Constructor.model()
    ),
    data: RaceData = RaceData.model(),
    race: Map<String, RaceResult>? = mapOf(
        "driverId" to RaceResult.model()
    ),
    qualifying: Map<String, QualifyingResult>? = mapOf(
        "driverId" to QualifyingResult.model()
    ),
    schedule: List<Schedule> = listOf(
        Schedule.model()
    )
): Round = Round(
    drivers = drivers,
    constructors = constructors,
    data = data,
    race = race,
    qualifying = qualifying,
    schedule = schedule
)