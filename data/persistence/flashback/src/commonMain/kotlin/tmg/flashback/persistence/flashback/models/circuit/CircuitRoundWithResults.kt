package tmg.flashback.persistence.flashback.models.circuit

import androidx.room.Embedded
import androidx.room.Relation

data class CircuitRoundWithResults(
    @Embedded
    val round: CircuitRound,
    @Relation(
        entity = CircuitRoundResult::class,
        parentColumn = "season_round",
        entityColumn = "season_round_id"
    )
    val results: List<CircuitRoundResultWithDriverConstructor>
) {
    companion object
}