package tmg.flashback.persistence.flashback.models.lineup

import androidx.room3.Embedded
import androidx.room3.Relation
import tmg.flashback.persistence.flashback.models.constructors.Constructor
import tmg.flashback.persistence.flashback.models.drivers.Driver

data class LineupWithDrivers(
    @Embedded
    val lineup: Lineup,
    @Relation(
        parentColumn = "constructor_id",
        entityColumn = "id"
    )
    val constructor: Constructor,
    @Relation(
        parentColumn = "driver_id",
        entityColumn = "id"
    )
    val driver: Driver
) {
    companion object
}