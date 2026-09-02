package tmg.flashback.persistence.flashback.models.drivers

import androidx.room3.Embedded
import androidx.room3.Relation
import tmg.flashback.persistence.flashback.models.constructors.Constructor
import tmg.flashback.persistence.flashback.models.race.RaceInfo
import tmg.flashback.persistence.flashback.models.race.RaceInfoWithCircuit

data class DriverSeasonRaceWithConstructor(
    @Embedded
    val race: DriverSeasonRace,
    @Relation(
        parentColumns = ["constructor_id"],
        entityColumns = ["id"]
    )
    val constructor: Constructor,
    @Relation(
        entity = RaceInfo::class,
        parentColumns = ["season_round_id"],
        entityColumns = ["id"]
    )
    val round: RaceInfoWithCircuit
) {
    companion object
}