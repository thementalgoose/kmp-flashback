package tmg.flashback.feature.rss.extensions

import kotlin.test.Test
import kotlin.test.assertEquals

class StringExtensionsTest {

    data class TestCase(
        val input: String,
        val expected: String
    )

    private val testCases = listOf(
        TestCase("https://www.google.com", "google.com"),
        TestCase("http://www.google.com", "google.com"),
        TestCase("https://google.com", "google.com"),
        TestCase("http://google.com", "google.com"),
        TestCase("www.google.com", "google.com"),
        TestCase("ww.google.com", "ww.google.com")
    )

    @Test
    fun `stripHost strips the prefixes off of values`() {
        testCases.forEach { (input, expected) ->
            assertEquals(expected, input.stripHTTP())
        }
    }

    @Test
    fun `stripWWW strips www off start`() {
        assertEquals("google.com", "www.google.com".stripWWW())
    }
}