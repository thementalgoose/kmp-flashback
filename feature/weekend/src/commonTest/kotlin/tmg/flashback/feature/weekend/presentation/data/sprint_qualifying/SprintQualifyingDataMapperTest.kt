package tmg.flashback.feature.weekend.presentation.data.sprint_qualifying

import tmg.flashback.formula1.model.Race
import tmg.flashback.formula1.model.model
import kotlin.test.Test
import kotlin.test.assertEquals

class SprintQualifyingDataMapperTest {

    private lateinit var underTest: SprintQualifyingDataMapperImpl

    private fun initUnderTest() {
        underTest = SprintQualifyingDataMapperImpl()
    }

    @Test
    fun `data maps correctly`() {
        val input = Race.model()
        val expected = listOf(
            SprintQualifyingModel.Result.model()
        )

        initUnderTest()

        assertEquals(expected, underTest(input))
    }
}