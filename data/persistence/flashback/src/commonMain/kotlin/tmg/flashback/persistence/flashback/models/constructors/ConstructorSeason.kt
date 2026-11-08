package tmg.flashback.persistence.flashback.models.constructors

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity
data class ConstructorSeason(
    @ColumnInfo(name = "constructor_id")
    val constructorId: String,
    @ColumnInfo(name = "season")
    val season: Int,
    @ColumnInfo(name = "championship_standing")
    val championshipStanding: Int?,
    @ColumnInfo(name = "points")
    val points: Double,
    @ColumnInfo(name = "in_progress")
    val inProgress: Boolean,
    @ColumnInfo(name = "in_progress_max_position")
    val inProgressMaxPosition: Int?,
    @ColumnInfo(name = "in_progress_min_position")
    val inProgressMinPosition: Int?,
    @ColumnInfo(name = "races")
    val races: Int,
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = "${constructorId}_${season}"
) {
    companion object
}