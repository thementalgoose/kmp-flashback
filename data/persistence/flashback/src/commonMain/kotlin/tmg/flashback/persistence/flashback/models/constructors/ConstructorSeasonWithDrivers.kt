package tmg.flashback.persistence.flashback.models.constructors

import androidx.room3.Embedded
import androidx.room3.Relation

data class ConstructorSeasonWithDrivers(
    @Embedded
    val constructorSeason: ConstructorSeason,
    @Relation(
        entity = ConstructorSeasonDriver::class,
        parentColumn = "id",
        entityColumn = "constructor_season_id"
    )
    val drivers: List<ConstructorSeasonDriverWithDriver>
) {
    companion object
}