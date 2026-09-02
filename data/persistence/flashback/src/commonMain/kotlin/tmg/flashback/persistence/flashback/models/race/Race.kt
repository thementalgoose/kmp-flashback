package tmg.flashback.persistence.flashback.models.race

import androidx.room3.Embedded
import androidx.room3.Relation
import tmg.flashback.persistence.flashback.models.circuit.Circuit
import tmg.flashback.persistence.flashback.models.overview.Schedule

data class Race(
    @Embedded
    val raceInfo: RaceInfo,
    @Relation(
        parentColumns = ["circuit_id"],
        entityColumns = ["id"]
    )
    val circuit: Circuit,
    @Relation(
        entity = QualifyingResult::class,
        parentColumns = ["id"],
        entityColumns = ["season_round_id"]
    )
    val qualifying: List<QualifyingDriverResult>,
    @Relation(
        entity = RaceResult::class,
        parentColumns = ["id"],
        entityColumns = ["season_round_id"]
    )
    val race: List<RaceDriverResult>,
    @Relation(
        parentColumns = ["id"],
        entityColumns = ["season_round_id"]
    )
    val schedule: List<Schedule>,
    @Relation(
        entity = SprintQualifyingResult::class,
        parentColumns = ["id"],
        entityColumns = ["season_round_id"]
    )
    val sprintQualifying: List<SprintQualifyingDriverResult>,
    @Relation(
        entity = SprintRaceResult::class,
        parentColumns = ["id"],
        entityColumns = ["season_round_id"]
    )
    val sprintRace: List<SprintRaceDriverResult>
) {
    companion object
}