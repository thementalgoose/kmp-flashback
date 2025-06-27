package tmg.flashback.feature.weekend.presentation.data.sprint_qualifying

import tmg.flashback.formula1.model.DriverEntry
import tmg.flashback.formula1.model.SprintQualifyingResult

sealed class SprintQualifyingModel(
    val id: String
) {
    data class Result(
        val driver: DriverEntry,
        private val finalQualifyingPosition: Int?,
        val sq1: SprintQualifyingResult?,
        val sq2: SprintQualifyingResult?,
        val sq3: SprintQualifyingResult?,
        val qualified: Int? = finalQualifyingPosition ?: sq3?.position ?: sq2?.position ?: sq1?.position,
        val grid: Int?
    ) : SprintQualifyingModel(driver.driver.id) {
        companion object
    }
}