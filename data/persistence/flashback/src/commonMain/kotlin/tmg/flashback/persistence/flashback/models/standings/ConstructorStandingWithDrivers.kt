package tmg.flashback.persistence.flashback.models.standings

import androidx.room3.Embedded
import androidx.room3.Relation
import tmg.flashback.persistence.flashback.models.constructors.Constructor

data class ConstructorStandingWithDrivers(
    @Embedded
    val standing: ConstructorStanding,
    @Relation(
        parentColumn = "constructor_id",
        entityColumn = "id"
    )
    val constructor: Constructor,
    @Relation(
        entity = ConstructorStandingDriver::class,
        parentColumn = "id",
        entityColumn = "constructor_season_id"
    )
    val drivers: List<ConstructorStandingDriverWithDriver>
) {
    companion object
}