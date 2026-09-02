package tmg.flashback.persistence.flashback.models.constructors

import androidx.room3.Embedded
import androidx.room3.Relation

data class ConstructorHistory(
    @Embedded
    val constructor: Constructor,
    @Relation(
        entity = ConstructorSeason::class,
        parentColumn = "id",
        entityColumn = "constructor_id"
    )
    val seasons: List<ConstructorSeasonWithDrivers>
) {
    companion object
}