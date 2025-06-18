package tmg.flashback.formula1.model

import kotlin.test.Test
import kotlin.test.assertEquals

internal class SeasonConstructorStandingSeasonTest {

    private data class TestCase(
        val championshipPosition: Int?,
        val expectedResult: Boolean
    )

    private val testCases = listOf(
        TestCase(null,false),
        TestCase(0,false),
        TestCase(1,true),
        TestCase(5,true),
        TestCase(15,true),
    )
    @Test
    fun `has valid championship position returns based on position`() {
        testCases.forEach { (championshipPosition, expectedResult) ->
            val model = SeasonConstructorStandingSeason.model(championshipPosition = championshipPosition)
            assertEquals(expectedResult, model.hasValidChampionshipPosition)
        }
    }
}