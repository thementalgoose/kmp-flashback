package tmg.flashback.persistence.flashback.models.race

import androidx.room.Embedded
import androidx.room.Relation
import tmg.flashback.persistence.flashback.models.circuit.Circuit

data class RaceInfoWithCircuit(
    @Embedded
    val raceInfo: RaceInfo,
    @Relation(
        parentColumn = "circuit_id",
        entityColumn = "id"
    )
    val circuit: Circuit
) {
    companion object
}