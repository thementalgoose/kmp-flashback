package tmg.flashback.persistence.flashback.models.standings

import androidx.room3.Embedded
import androidx.room3.Relation
import tmg.flashback.persistence.flashback.models.drivers.Driver

data class ConstructorStandingDriverWithDriver(
    @Embedded
    val standing: ConstructorStandingDriver,
    @Relation(
        parentColumns = ["driver_id"],
        entityColumns = ["id"]
    )
    val driver: Driver
) {
    companion object
}