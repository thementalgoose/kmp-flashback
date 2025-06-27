package tmg.flashback.feature.weekend.presentation.data.info

import tmg.flashback.formula1.model.Circuit
import tmg.flashback.formula1.model.Schedule

data class InfoModel(
    val season: Int,
    val round: Int,
    val raceName: String,
    val circuit: Circuit,
    val laps: String?,
    val youtubeUrl: String?,
    val wikipediaUrl: String?,
    val schedule: List<Schedule>
) {
    companion object
}