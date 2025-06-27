package tmg.flashback.feature.weekend.presentation.data.qualifying

import tmg.flashback.formula1.model.DriverEntry
import tmg.flashback.formula1.model.QualifyingResult

sealed class QualifyingModel(
    val id: String,
) {
    data class Q1Q2Q3(
        val driver: DriverEntry,
        private val finalQualifyingPosition: Int?,
        val q1: QualifyingResult?,
        val q2: QualifyingResult?,
        val q3: QualifyingResult?,
        val qualified: Int? = finalQualifyingPosition ?: q3?.position ?: q2?.position ?: q1?.position,
        val grid: Int?,
        val sprintRaceGrid: Int? // For 2021 and 2022 when qualifying set grid for sprint
    ) : QualifyingModel(driver.driver.id) {
        companion object
    }

    data class Q1Q2(
        val driver: DriverEntry,
        private val finalQualifyingPosition: Int?,
        val q1: QualifyingResult?,
        val q2: QualifyingResult?,
        val qualified: Int? = finalQualifyingPosition ?: q2?.position ?: q1?.position
    ) : QualifyingModel(driver.driver.id) {
        companion object
    }

    data class Q1(
        val driver: DriverEntry,
        private val finalQualifyingPosition: Int?,
        val q1: QualifyingResult?,
        val qualified: Int? = finalQualifyingPosition ?: q1?.position
    ) : QualifyingModel(driver.driver.id) {
        companion object
    }
}