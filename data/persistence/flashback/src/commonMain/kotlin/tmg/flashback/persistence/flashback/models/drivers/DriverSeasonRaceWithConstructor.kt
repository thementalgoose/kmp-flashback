package tmg.flashback.persistence.flashback.models.drivers

import androidx.room.Embedded
import androidx.room.Relation
import tmg.flashback.persistence.flashback.models.constructors.Constructor
import tmg.flashback.persistence.flashback.models.race.RaceInfo
import tmg.flashback.persistence.flashback.models.race.RaceInfoWithCircuit

data class DriverSeasonRaceWithConstructor(
    @Embedded
    val race: DriverSeasonRace,
    @Relation(
        parentColumn = "constructor_id",
        entityColumn = "id"
    )
    val constructor: Constructor,
    @Relation(
        entity = RaceInfo::class,
        parentColumn = "season_round_id",
        entityColumn = "id"
    )
    val round: RaceInfoWithCircuit
) {
    companion object
}