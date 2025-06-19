package tmg.flashback.persistence.flashback.models.constructors

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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
    @ColumnInfo(name = "races")
    val races: Int,
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = "${constructorId}_${season}"
) {
    companion object
}