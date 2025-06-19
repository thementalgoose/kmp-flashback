package tmg.flashback.persistence.flashback.models.circuit

import androidx.room.Embedded
import androidx.room.Relation

data class CircuitHistory(
    @Embedded
    val circuit: Circuit,
    @Relation(
        entity = CircuitRound::class,
        parentColumn = "id",
        entityColumn = "circuit_id"
    )
    val races: List<CircuitRoundWithResults>
) {
    companion object
}