package tmg.flashback.flashbackapi.api.models.lineup

import tmg.flashback.flashbackapi.api.models.constructors.Constructor
import tmg.flashback.flashbackapi.api.models.constructors.model
import tmg.flashback.flashbackapi.api.models.drivers.Driver
import tmg.flashback.flashbackapi.api.models.drivers.model

fun Lineup.Companion.model(
    drivers: Map<String, Driver> = mapOf(
        "driverId" to Driver.model()
    ),
    constructors: Map<String, Constructor> = mapOf(
        "constructorId" to Constructor.model()
    ),
    lineup: Map<String, LineupSeason> = mapOf(
        "s2020" to LineupSeason.model()
    )
): Lineup = Lineup(
    drivers = drivers,
    constructors = constructors,
    lineup = lineup,
)