package tmg.flashback.persistence.flashback.models.standings

import androidx.room3.Embedded
import androidx.room3.Relation
import tmg.flashback.persistence.flashback.models.drivers.Driver

data class DriverStandingWithConstructors(
    @Embedded
    val standing: DriverStanding,
    @Relation(
        parentColumns = ["driver_id"],
        entityColumns = ["id"]
    )
    val driver: Driver,
    @Relation(
        entity = DriverStandingConstructor::class,
        parentColumns = ["id"],
        entityColumns = ["driver_season_id"]
    )
    val constructors: List<DriverStandingConstructorWithConstructor>
) {
    companion object
}