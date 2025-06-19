package tmg.flashback.persistence.flashback.models.drivers

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DriverSeasonRace(
    @ColumnInfo(name = "driver_id")
    val driverId: String,
    @ColumnInfo(name = "season")
    val season: Int,
    @ColumnInfo(name = "round")
    val round: Int,
    @ColumnInfo(name = "constructor_id")
    val constructorId: String,
    @ColumnInfo(name = "sprint_qualifying")
    val sprintQualifying: Boolean,
    @ColumnInfo(name = "sprint_race")
    val sprintRace: Boolean,
    @ColumnInfo(name = "qualified")
    val qualified: Int?,
    @ColumnInfo(name = "gridPos")
    val gridPos: Int?,
    @ColumnInfo(name = "finished")
    val finished: Int,
    @ColumnInfo(name = "status")
    val status: String,
    @ColumnInfo(name = "points")
    val points: Double,
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = "${driverId}_${season}_${round}${pkSprint(sprintQualifying, sprintRace)}",
    @ColumnInfo(name = "driver_season_id")
    val driverSeasonId: String = "${driverId}_${season}",
    @ColumnInfo(name = "season_round_id")
    val seasonRoundId: String = "${season}_${round}"
) {
    companion object {
        private fun pkSprint(
            sprintQualifying: Boolean,
            sprintRace: Boolean
        ): String = when {
            sprintQualifying && sprintRace -> "_sprint_qr"
            sprintQualifying -> "_sprint_q"
            sprintRace -> "_sprint_r"
            else -> ""
        }
    }
}