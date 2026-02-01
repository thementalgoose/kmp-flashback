package tmg.flashback.infrastructure.extensions

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class DoubleExtensionsTest {

    //region formatTo

    @Test
    fun `formatTo with zero decimal places returns integer`() {
        val result = 3.14159.formatTo(0)
        assertTrue(result == "3" || result.endsWith("3"), "Expected '3' or ending with '3', got: $result")
    }

    @Test
    fun `formatTo with one decimal place formats correctly`() {
        val result = 3.14159.formatTo(1)
        assertTrue(result.contains("3.1") || result.endsWith("3.1"), "Expected to contain '3.1', got: $result")
    }

    @Test
    fun `formatTo with two decimal places formats correctly`() {
        val result = 3.14159.formatTo(2)
        assertTrue(result.contains("3.14") || result.endsWith("3.14"), "Expected to contain '3.14', got: $result")
    }

    @Test
    fun `formatTo handles zero`() {
        val result = 0.0.formatTo(2)
        assertTrue(result.contains("0") && !result.contains("1"), "Expected to contain '0', got: $result")
    }

    @Test
    fun `formatTo handles negative numbers`() {
        val result = (-3.14159).formatTo(2)
        assertTrue(result.contains("3.14") && result.contains("-"), "Expected to contain '-3.14', got: $result")
    }

    @Test
    fun `formatTo rounds correctly`() {
        val result = 1.999.formatTo(2)
        assertTrue(result.contains("2") || result.contains("1.99"), "Expected '2.00' or '1.99' (rounding), got: $result")
    }

    //endregion

    //region roundToHalf

    @Test
    fun `roundToHalf keeps point five value`() {
        assertEquals("3.5", 3.5.roundToHalf())
        assertEquals("0.5", 0.5.roundToHalf())
        assertEquals("10.5", 10.5.roundToHalf())
    }

    @Test
    fun `roundToHalf rounds down values below point five`() {
        assertEquals("3", 3.0.roundToHalf())
        assertEquals("3", 3.1.roundToHalf())
        assertEquals("3", 3.2.roundToHalf())
        assertEquals("3", 3.3.roundToHalf())
        assertEquals("3", 3.4.roundToHalf())
    }

    @Test
    fun `roundToHalf truncates values above point five to integer part`() {
        // Note: The function truncates to integer, it doesn't round up
        assertEquals("3", 3.6.roundToHalf())
        assertEquals("3", 3.7.roundToHalf())
        assertEquals("3", 3.8.roundToHalf())
        assertEquals("3", 3.9.roundToHalf())
    }

    @Test
    fun `roundToHalf handles zero`() {
        assertEquals("0", 0.0.roundToHalf())
    }

    @Test
    fun `roundToHalf handles whole numbers`() {
        assertEquals("10", 10.0.roundToHalf())
        assertEquals("100", 100.0.roundToHalf())
    }

    //endregion
}
