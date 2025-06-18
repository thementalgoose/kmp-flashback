package tmg.flashback.formula1.extensions

import kotlin.test.Test
import kotlin.test.assertEquals

internal class DoubleExtensionsTest {

    private data class TestCase(
        val input: Double,
        val expected: String
    )
    private val testCases = listOf(
        TestCase(0.0,"0"),
        TestCase(0.4,"0"),
        TestCase(0.5,"0.5"),
        TestCase(1.00013,"1"),
        TestCase(1.5,"1.5"),
        TestCase(1.50,"1.5"),
        TestCase(1.0,"1"),
        TestCase(6.0,"6"),
        TestCase(213.5,"213.5"),
        TestCase(220.0,"220"),
        TestCase(0.000002,"0"),
        TestCase(1.50000,"1.5"),
        TestCase(0.499999999999,"0.5")
    )
    @Test
    fun `display method on points double displays the points in a readable format`() {
        testCases.forEach { (input, expected) ->
            assertEquals(expected, input.pointsDisplay())
        }
    }
}