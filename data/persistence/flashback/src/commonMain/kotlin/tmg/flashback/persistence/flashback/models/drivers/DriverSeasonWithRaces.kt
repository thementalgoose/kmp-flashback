package tmg.flashback.persistence.flashback.models.drivers

import androidx.room.Embedded
import androidx.room.Relation

data class DriverSeasonWithRaces(
    @Embedded
    val driverSeason: DriverSeason,
    @Relation(
        entity = DriverSeasonRace::class,
        parentColumn = "id",
        entityColumn = "driver_season_id"
    )
    val races: List<DriverSeasonRaceWithConstructor>
) {
    companion object
}