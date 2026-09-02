package tmg.flashback.persistence.flashback.models.drivers

import androidx.room3.Embedded
import androidx.room3.Relation

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