package tmg.flashback.feature.weekend.presentation.data.qualifying

import tmg.flashback.feature.weekend.presentation.data.sprint_qualifying.SprintQualifyingModel
import tmg.flashback.feature.weekend.presentation.data.sprint_qualifying.model
import tmg.flashback.formula1.model.LapTime
import tmg.flashback.formula1.model.QualifyingResult
import tmg.flashback.formula1.model.QualifyingRound
import tmg.flashback.formula1.model.QualifyingType
import tmg.flashback.formula1.model.Race
import tmg.flashback.formula1.model.model
import kotlin.test.Test
import kotlin.test.assertEquals

class QualifyingDataMapperTest {

    private lateinit var underTest: QualifyingDataMapperImpl

    private fun initUnderTest() {
        underTest = QualifyingDataMapperImpl()
    }

    @Test
    fun `data maps correctly for q1 q2 q3`() {
        val input = Race.model()
        val expected = listOf(
            QualifyingModel.Q1Q2Q3.model()
        )
        initUnderTest()

        assertEquals(expected, underTest(input))
    }

    @Test
    fun `data maps correctly for q1 q2`() {
        val input = Race.model(
            qualifying = listOf(
                QualifyingRound.model(label = QualifyingType.Q1, results = listOf(
                    QualifyingResult.model(lapTime = LapTime.model(0,1,2,1))
                )),
                QualifyingRound.model(label = QualifyingType.Q2, results = listOf(
                    QualifyingResult.model(lapTime = LapTime.model(0,1,2,2))
                ))
            )
        )
        val expected = listOf(
            QualifyingModel.Q1Q2.model()
        )
        initUnderTest()

        assertEquals(expected, underTest(input))
    }

    @Test
    fun `data maps correctly for q1`() {
        val input = Race.model(
            qualifying = listOf(
                QualifyingRound.model(label = QualifyingType.Q1, results = listOf(
                    QualifyingResult.model(lapTime = LapTime.model(0,1,2,1))
                ))
            )
        )
        val expected = listOf(
            QualifyingModel.Q1.model()
        )
        initUnderTest()

        assertEquals(expected, underTest(input))
    }
}