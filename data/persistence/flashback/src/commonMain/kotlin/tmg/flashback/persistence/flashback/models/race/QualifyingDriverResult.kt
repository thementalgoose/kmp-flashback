package tmg.flashback.persistence.flashback.models.race

import androidx.room3.Embedded
import androidx.room3.Relation
import tmg.flashback.persistence.flashback.models.constructors.Constructor
import tmg.flashback.persistence.flashback.models.drivers.Driver

class QualifyingDriverResult(
    @Embedded
    val qualifyingResult: QualifyingResult,
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