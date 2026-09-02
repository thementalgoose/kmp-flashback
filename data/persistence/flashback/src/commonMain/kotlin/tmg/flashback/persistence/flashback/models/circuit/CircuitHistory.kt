package tmg.flashback.persistence.flashback.models.circuit

import androidx.room3.Embedded
import androidx.room3.Relation

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