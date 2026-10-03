package tmg.flashback.formula1.model

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull

internal class SeasonDriverStandingSeasonTest {

    data class TestCase(
        val championshipPosition: Int?,
        val expectedResult: Boolean
    )

    @ParameterizedTest
    @MethodSource("testCases")
    fun `has valid championship position returns based on position`(testCase: TestCase) {
        val model = SeasonDriverStandingSeason.model(championshipPosition = testCase.championshipPosition)
        assertEquals(testCase.expectedResult, model.hasValidChampionshipPosition)
    }

    @Test
    fun `in progress content returns value when round is in progress`() {
        val model = SeasonDriverStandingSeason.model(
            inProgress = true,
            inProgressName = "PROGRESS",
            inProgressRound = 1
        )

        assertEquals(Pair("PROGRESS", 1), model.inProgressContent)
    }

    @Test
    fun `in progress content returns null when in progress is false`() {
        val model = SeasonDriverStandingSeason.model(
            inProgress = false,
            inProgressName = "PROGRESS",
            inProgressRound = 1
        )

        assertNull(model.inProgressContent)
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