package tmg.flashback.persistence.flashback.models.race

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import tmg.flashback.persistence.flashback.models.constructors.Constructor
import tmg.flashback.persistence.flashback.models.drivers.Driver

@Entity
data class SprintQualifyingDriverResult(
    @Embedded
    val qualifyingResult: SprintQualifyingResult,
    @Relation(
        parentColumn = "driver_id",
        entityColumn = "id"
    )
    val driver: Driver,
    @Relation(
        parentColumn = "constructor_id",
        entityColumn = "id"
    )
    val constructor: Constructor
) {
    companion object
}