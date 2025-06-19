package tmg.flashback.persistence.flashback.models.race

import androidx.room.Embedded
import androidx.room.Relation
import tmg.flashback.persistence.flashback.models.constructors.Constructor
import tmg.flashback.persistence.flashback.models.drivers.Driver

data class RaceDriverResult(
    @Embedded
    val raceResult: RaceResult,
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