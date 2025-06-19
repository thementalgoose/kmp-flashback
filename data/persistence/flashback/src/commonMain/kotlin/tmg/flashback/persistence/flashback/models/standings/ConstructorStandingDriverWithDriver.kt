package tmg.flashback.persistence.flashback.models.standings

import androidx.room.Embedded
import androidx.room.Relation
import tmg.flashback.persistence.flashback.models.drivers.Driver

data class ConstructorStandingDriverWithDriver(
    @Embedded
    val standing: ConstructorStandingDriver,
    @Relation(
        parentColumn = "driver_id",
        entityColumn = "id"
    )
    val driver: Driver
) {
    companion object
}