package tmg.flashback.persistence.flashback.models.constructors

import androidx.room3.Embedded
import androidx.room3.Relation
import tmg.flashback.persistence.flashback.models.drivers.Driver

data class ConstructorSeasonDriverWithDriver(
    @Embedded
    val results: ConstructorSeasonDriver,
    @Relation(
        parentColumn = "driver_id",
        entityColumn = "id"
    )
    val driver: Driver
) {
    companion object
}