package tmg.flashback.feature.rss.extensions

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

class StringExtensionsTest {

    data class TestCase(
        val input: String,
        val expected: String
    )

    @ParameterizedTest
    @MethodSource("testCases")
    fun `stripHost strips the prefixes off of values`(testCase: TestCase) {
        assertEquals(testCase.expected, testCase.input.stripHTTP())
    }

    @Test
    fun `stripWWW strips www off start`() {
        assertEquals("google.com", "www.google.com".stripWWW())
    }

    companion object {
        @JvmStatic
        fun testCases() = listOf(
            TestCase("https://www.google.com", "google.com"),
            TestCase("http://www.google.com", "google.com"),
            TestCase("https://google.com", "google.com"),
            TestCase("http://google.com", "google.com"),
            TestCase("www.google.com", "google.com"),
            TestCase("ww.google.com", "ww.google.com")
        )
    }
}