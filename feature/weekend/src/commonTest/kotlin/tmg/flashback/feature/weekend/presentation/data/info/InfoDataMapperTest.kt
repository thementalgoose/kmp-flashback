package tmg.flashback.feature.weekend.presentation.data.info

import tmg.flashback.formula1.model.Race
import tmg.flashback.formula1.model.model
import kotlin.test.Test
import kotlin.test.assertEquals

internal class InfoDataMapperTest {

    private lateinit var underTest: InfoDataMapperImpl

    private fun initUnderTest() {
        underTest = InfoDataMapperImpl()
    }

    @Test
    fun `data maps correctly`() {
        val input = Race.model()
        val expected = InfoModel.model()

        initUnderTest()

        assertEquals(expected, underTest(input))
    }
}