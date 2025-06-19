package tmg.flashback.persistence.flashback.models.drivers

import androidx.room.Embedded
import androidx.room.Relation

data class DriverHistory(
    @Embedded
    val driver: Driver,
    @Relation(
        entity = DriverSeason::class,
        parentColumn = "id",
        entityColumn = "driver_id"
    )
    val seasons: List<DriverSeasonWithRaces>
) {
    companion object
}