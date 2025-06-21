package tmg.flashback.flashbackapi.api.models.races

import tmg.flashback.flashbackapi.api.models.constructors.Constructor
import tmg.flashback.flashbackapi.api.models.constructors.model
import tmg.flashback.flashbackapi.api.models.drivers.Driver
import tmg.flashback.flashbackapi.api.models.drivers.model
import tmg.flashback.flashbackapi.api.models.overview.Event
import tmg.flashback.flashbackapi.api.models.overview.model

fun Season.Companion.model(
    season: Int = 2020,
    driverStandings: Map<String, DriverStandings>? = mapOf(
        "driverId" to DriverStandings.model()
    ),
    constructorStandings: Map<String, ConstructorStandings>? = mapOf(
        "constructorId" to ConstructorStandings.model()
    ),
    drivers: Map<String, Driver> = mapOf(
        "driverId" to Driver.model()
    ),
    constructors: Map<String, Constructor> = mapOf(
        "constructorId" to Constructor.model()
    ),
    races: Map<String, Race>? = mapOf(
        "r1" to Race.model()
    ),
    events: List<Event>? = listOf(
        Event.model()
    )
): Season = Season(
    season = season,
    driverStandings = driverStandings,
    constructorStandings = constructorStandings,
    drivers = drivers,
    constructors = constructors,
    races = races,
    events = events
)