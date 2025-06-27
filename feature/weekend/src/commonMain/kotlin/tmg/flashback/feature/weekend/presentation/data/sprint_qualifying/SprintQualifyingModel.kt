package tmg.flashback.feature.weekend.presentation.data.sprint_qualifying

import tmg.flashback.formula1.model.DriverEntry
import tmg.flashback.formula1.model.LapTime
import tmg.flashback.formula1.model.SprintQualifyingResult
import tmg.flashback.formula1.preview.preview

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

fun SprintQualifyingModel.Result.Companion.preview() = SprintQualifyingModel.Result(
    driver = DriverEntry.preview(),
    finalQualifyingPosition = 1,
    sq1 = SprintQualifyingResult(DriverEntry.preview(), LapTime.preview(), 1),
    sq2 = SprintQualifyingResult(DriverEntry.preview(), LapTime.preview(), 1),
    sq3 = SprintQualifyingResult(DriverEntry.preview(), LapTime.preview(), 1),
    grid = 1,
)