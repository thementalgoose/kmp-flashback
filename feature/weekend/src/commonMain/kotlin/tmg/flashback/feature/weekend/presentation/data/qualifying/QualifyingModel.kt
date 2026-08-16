package tmg.flashback.feature.weekend.presentation.data.qualifying

import tmg.flashback.formula1.model.DriverEntry
import tmg.flashback.formula1.model.LapTime
import tmg.flashback.formula1.model.QualifyingResult
import tmg.flashback.formula1.model.QualifyingType
import tmg.flashback.formula1.preview.preview

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
    ) : QualifyingModel("driver-${driver.driver.id}") {

        internal fun comparatorValue(type: QualifyingType?): Int {
            return when (type) {
                QualifyingType.Q1 -> q1?.lapTime?.totalMillis ?: 0
                QualifyingType.Q2 -> q2?.lapTime?.totalMillis ?: 0
                QualifyingType.Q3 -> q3?.lapTime?.totalMillis ?: 0
                else -> qualified ?: 0
            }
        }

        companion object
    }

    data class Q1Q2(
        val driver: DriverEntry,
        private val finalQualifyingPosition: Int?,
        val q1: QualifyingResult?,
        val q2: QualifyingResult?,
        val qualified: Int? = finalQualifyingPosition ?: q2?.position ?: q1?.position
    ) : QualifyingModel("driver-${driver.driver.id}") {

        internal fun comparatorValue(type: QualifyingType?): Int {
            return when (type) {
                QualifyingType.Q1 -> q1?.lapTime?.totalMillis ?: 0
                QualifyingType.Q2 -> q2?.lapTime?.totalMillis ?: 0
                else -> qualified ?: 0
            }
        }

        companion object
    }

    data class Q1(
        val driver: DriverEntry,
        private val finalQualifyingPosition: Int?,
        val q1: QualifyingResult?,
        val qualified: Int? = finalQualifyingPosition ?: q1?.position
    ) : QualifyingModel("driver-${driver.driver.id}") {

        internal fun comparatorValue(type: QualifyingType?): Int {
            return when (type) {
                QualifyingType.Q1 -> q1?.lapTime?.totalMillis ?: 0
                else -> qualified ?: 0
            }
        }

        companion object
    }
}

fun List<QualifyingModel>.sortedBy(type: QualifyingType?): List<QualifyingModel> {
    return this.sortedBy {
        return@sortedBy when (it) {
            is QualifyingModel.Q1 -> it.comparatorValue(type)
            is QualifyingModel.Q1Q2 -> it.comparatorValue(type)
            is QualifyingModel.Q1Q2Q3 -> it.comparatorValue(type)
        }
    }
}


fun QualifyingModel.Q1.Companion.preview() = QualifyingModel.Q1(
    driver = DriverEntry.preview(),
    finalQualifyingPosition = 1,
    q1 = QualifyingResult(DriverEntry.preview(), LapTime.preview(), 1)
)

fun QualifyingModel.Q1Q2.Companion.preview() = QualifyingModel.Q1Q2(
    driver = DriverEntry.preview(),
    finalQualifyingPosition = 1,
    q1 = QualifyingResult(DriverEntry.preview(), LapTime.preview(), 1),
    q2 = QualifyingResult(DriverEntry.preview(), LapTime.preview(), 1)
)

fun QualifyingModel.Q1Q2Q3.Companion.preview() = QualifyingModel.Q1Q2Q3(
    driver = DriverEntry.preview(),
    finalQualifyingPosition = 1,
    q1 = QualifyingResult(DriverEntry.preview(), LapTime.preview(), 1),
    q2 = QualifyingResult(DriverEntry.preview(), LapTime.preview(), 1),
    q3 = QualifyingResult(DriverEntry.preview(), LapTime.preview(), 1),
    grid = 1,
    sprintRaceGrid = null
)