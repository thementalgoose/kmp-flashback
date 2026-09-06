package tmg.flashback.persistence.flashback.models.circuit

import androidx.room3.Embedded
import androidx.room3.Relation
import tmg.flashback.persistence.flashback.models.constructors.Constructor
import tmg.flashback.persistence.flashback.models.drivers.Driver

data class CircuitRoundResultWithDriverConstructor(
    @Embedded
    val result: CircuitRoundResult,
    @Relation(
        parentColumns = ["constructor_id"],
        entityColumns = ["id"]
    )
    val constructor: Constructor,
    @Relation(
        parentColumns = ["driver_id"],
        entityColumns = ["id"]
    )
    val driver: Driver
) {
    companion object
}