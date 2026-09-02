package tmg.flashback.persistence.flashback.models.race

import androidx.room3.Embedded
import androidx.room3.Relation
import tmg.flashback.persistence.flashback.models.constructors.Constructor
import tmg.flashback.persistence.flashback.models.drivers.Driver

data class SprintRaceDriverResult(
    @Embedded
    val sprintResult: SprintRaceResult,
    @Relation(
        parentColumns = ["driver_id"],
        entityColumns = ["id"]
    )
    val driver: Driver,
    @Relation(
        parentColumns = ["constructor_id"],
        entityColumns = ["id"]
    )
    val constructor: Constructor
) {
    companion object
}