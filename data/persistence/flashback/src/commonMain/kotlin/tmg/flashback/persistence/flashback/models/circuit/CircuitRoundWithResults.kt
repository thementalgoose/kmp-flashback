package tmg.flashback.persistence.flashback.models.circuit

import androidx.room3.Embedded
import androidx.room3.Relation

data class CircuitRoundWithResults(
    @Embedded
    val round: CircuitRound,
    @Relation(
        entity = CircuitRoundResult::class,
        parentColumns = ["season_round"],
        entityColumns = ["season_round_id"]
    )
    val results: List<CircuitRoundResultWithDriverConstructor>
) {
    companion object
}