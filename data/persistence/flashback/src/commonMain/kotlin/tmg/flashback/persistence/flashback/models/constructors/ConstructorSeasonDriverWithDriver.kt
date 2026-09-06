package tmg.flashback.persistence.flashback.models.constructors

import androidx.room3.Embedded
import androidx.room3.Relation
import tmg.flashback.persistence.flashback.models.drivers.Driver

data class ConstructorSeasonDriverWithDriver(
    @Embedded
    val results: ConstructorSeasonDriver,
    @Relation(
        parentColumns = ["driver_id"],
        entityColumns = ["id"]
    )
    val driver: Driver
) {
    companion object
}