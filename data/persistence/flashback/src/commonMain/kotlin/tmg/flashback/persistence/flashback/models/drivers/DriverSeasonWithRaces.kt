package tmg.flashback.persistence.flashback.models.drivers

import androidx.room3.Embedded
import androidx.room3.Relation

data class DriverSeasonWithRaces(
    @Embedded
    val driverSeason: DriverSeason,
    @Relation(
        entity = DriverSeasonRace::class,
        parentColumns = ["id"],
        entityColumns = ["driver_season_id"]
    )
    val races: List<DriverSeasonRaceWithConstructor>
) {
    companion object
}