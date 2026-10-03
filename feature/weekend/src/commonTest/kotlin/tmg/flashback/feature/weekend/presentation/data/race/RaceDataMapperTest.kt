package tmg.flashback.feature.weekend.presentation.data.race

import tmg.flashback.feature.weekend.presentation.data.ResultType
import tmg.flashback.formula1.model.Race
import tmg.flashback.formula1.model.model
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

class RaceDataMapperTest {

    private lateinit var underTest: RaceDataMapperImpl

    private fun initUnderTest() {
        underTest = RaceDataMapperImpl()
    }

    @Test
    fun `data maps correctly for driver result type`() {
        val input = Race.model()
        val expected = listOf(
            RaceModel.DriverResult.model()
        )

        initUnderTest()

        assertEquals(expected, underTest(input, ResultType.DRIVERS))
    }

    @Test
    fun `data maps correctly for constructor result type`() {
        val input = Race.model()
        val expected = listOf(
            RaceModel.ConstructorResult.model()
        )
        initUnderTest()

        assertEquals(expected, underTest(input, ResultType.CONSTRUCTORS))
    }
}