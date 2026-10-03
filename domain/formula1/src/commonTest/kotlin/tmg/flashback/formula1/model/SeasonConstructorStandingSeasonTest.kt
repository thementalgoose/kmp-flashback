package tmg.flashback.formula1.model

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.api.Assertions.assertEquals

internal class SeasonConstructorStandingSeasonTest {

    data class TestCase(
        val championshipPosition: Int?,
        val expectedResult: Boolean
    )

    @ParameterizedTest
    @MethodSource("testCases")
    fun `has valid championship position returns based on position`(testCase: TestCase) {
        val model = SeasonConstructorStandingSeason.model(championshipPosition = testCase.championshipPosition)
        assertEquals(testCase.expectedResult, model.hasValidChampionshipPosition)
    }

    companion object {
        @JvmStatic
        fun testCases() = listOf(
            TestCase(null, false),
            TestCase(0, false),
            TestCase(1, true),
            TestCase(5, true),
            TestCase(15, true)
        )
    }
}