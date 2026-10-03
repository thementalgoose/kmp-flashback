package tmg.flashback.feature.weekend.presentation.data.sprint_race

import tmg.flashback.feature.weekend.presentation.data.ResultType
import tmg.flashback.formula1.model.Race
import tmg.flashback.formula1.model.model
import kotlin.test.Test
import kotlin.test.assertEquals

internal class SprintRaceDataMapperTest {

    private lateinit var underTest: SprintRaceDataMapperImpl

    private fun initUnderTest() {
        underTest = SprintRaceDataMapperImpl()
    }

    @Test
    fun `data maps correctly for driver result type`() {
        val input = Race.model()
        val expected = listOf(
            SprintRaceModel.DriverResult.model()
        )

        initUnderTest()

        assertEquals(expected, underTest(input, ResultType.DRIVERS))
    }

    @Test
    fun `data maps correctly for constructor result type`() {
        val input = Race.model()
        val expected = listOf(
            SprintRaceModel.ConstructorResult.model()
        )
        initUnderTest()

        assertEquals(expected, underTest(input, ResultType.CONSTRUCTORS))
    }
}