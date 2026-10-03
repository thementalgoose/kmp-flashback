package tmg.flashback.formula1.extensions

import tmg.flashback.infrastructure.extensions.roundToHalf
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.api.Assertions.assertEquals

internal class DoubleExtensionsTest {

    data class TestCase(
        val input: Double,
        val expected: String
    )

    @ParameterizedTest
    @MethodSource("testCases")
    fun `display method on points double displays the points in a readable format`(testCase: TestCase) {
        assertEquals(testCase.expected, testCase.input.roundToHalf())
    }

    companion object {
        @JvmStatic
        fun testCases() = listOf(
            TestCase(0.0, "0"),
            TestCase(0.4, "0"),
            TestCase(0.5, "0.5"),
            TestCase(1.00013, "1"),
            TestCase(1.5, "1.5"),
            TestCase(1.50, "1.5"),
            TestCase(1.0, "1"),
            TestCase(6.0, "6"),
            TestCase(213.5, "213.5"),
            TestCase(220.0, "220"),
            TestCase(0.000002, "0"),
            TestCase(1.50000, "1.5"),
            TestCase(0.499999999999, "0.5")
        )
    }
}