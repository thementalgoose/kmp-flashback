package tmg.flashback.persistence.flashback.models.drivers

import tmg.flashback.persistence.flashback.models.constructors.Constructor
import tmg.flashback.persistence.flashback.models.constructors.model
import tmg.flashback.persistence.flashback.models.race.RaceInfoWithCircuit
import tmg.flashback.persistence.flashback.models.race.model

fun DriverSeasonRaceWithConstructor.Companion.model(
    race: DriverSeasonRace = DriverSeasonRace.model(),
    constructor: Constructor = Constructor.model(),
    round: RaceInfoWithCircuit = RaceInfoWithCircuit.model()
): DriverSeasonRaceWithConstructor = DriverSeasonRaceWithConstructor(
    race = race,
    constructor = constructor,
    round = round
)