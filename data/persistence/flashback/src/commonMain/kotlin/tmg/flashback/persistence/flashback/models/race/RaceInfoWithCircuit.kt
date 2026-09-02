package tmg.flashback.persistence.flashback.models.race

import androidx.room3.Embedded
import androidx.room3.Relation
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