package tmg.flashback.formula1.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class SeasonDriverStandingSeasonTest {

    private data class TestCase(
        val championshipPosition: Int?,
        val expectedResult: Boolean
    )

    private val testCases = listOf(
        TestCase(null, false),
        TestCase(0, false),
        TestCase(1, true),
        TestCase(5, true),
        TestCase(15, true),
    )
    @Test
    fun `has valid championship position returns based on position`() {
        testCases.forEach { (championshipPosition, expectedResult) ->
            val model = SeasonDriverStandingSeason.model(championshipPosition = championshipPosition)

            assertEquals(expectedResult, model.hasValidChampionshipPosition)
        }
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
}