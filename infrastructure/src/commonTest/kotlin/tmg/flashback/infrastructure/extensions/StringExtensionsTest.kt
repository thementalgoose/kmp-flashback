package tmg.flashback.infrastructure.extensions

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class StringExtensionsTest {

    //region toColourInt

    data class TestCaseToColourInt(
        val input: String,
        val expected: Int
    )

    @ParameterizedTest
    @MethodSource("testCasesToColourInt")
    fun `toColourInt parses valid hex color strings correctly`(testCase: TestCaseToColourInt) {
        assertEquals(testCase.expected, testCase.input.toColourInt(), "Failed for input: ${testCase.input}")
    }

    @Test
    fun `toColourInt returns grey for invalid length strings`() {
        val grey = 0xFF888888.toInt()
        assertEquals(grey, "".toColourInt())
        assertEquals(grey, "#".toColourInt())
        assertEquals(grey, "#12345".toColourInt())
        assertEquals(grey, "#1234567".toColourInt())
        assertEquals(grey, "#123456789".toColourInt())
        assertEquals(grey, "12345".toColourInt())
    }

    @Test
    fun `toColourInt returns grey for invalid hex characters`() {
        val grey = 0xFF888888.toInt()
        assertEquals(grey, "#GGGGGG".toColourInt())
        assertEquals(grey, "#ZZZZZZ".toColourInt())
        assertEquals(grey, "XXYYZZ".toColourInt())
    }

    //endregion

    //region toEnum

    private enum class TestEnum {
        VALUE_ONE,
        VALUE_TWO,
        VALUE_THREE
    }

    private enum class TestEnumWithCustomValue(val customValue: String) {
        FIRST("first_value"),
        SECOND("second_value"),
        THIRD("third_value")
    }

    @Test
    fun `toEnum returns correct enum value by name`() {
        assertEquals(TestEnum.VALUE_ONE, "VALUE_ONE".toEnum<TestEnum>())
        assertEquals(TestEnum.VALUE_TWO, "VALUE_TWO".toEnum<TestEnum>())
        assertEquals(TestEnum.VALUE_THREE, "VALUE_THREE".toEnum<TestEnum>())
    }

    @Test
    fun `toEnum returns null for non-matching name`() {
        assertNull("INVALID".toEnum<TestEnum>())
        assertNull("value_one".toEnum<TestEnum>())
        assertNull("".toEnum<TestEnum>())
    }

    @Test
    fun `toEnum with custom mapping returns correct enum value`() {
        assertEquals(
            TestEnumWithCustomValue.FIRST,
            "first_value".toEnum<TestEnumWithCustomValue> { it.customValue }
        )
        assertEquals(
            TestEnumWithCustomValue.SECOND,
            "second_value".toEnum<TestEnumWithCustomValue> { it.customValue }
        )
        assertEquals(
            TestEnumWithCustomValue.THIRD,
            "third_value".toEnum<TestEnumWithCustomValue> { it.customValue }
        )
    }

    @Test
    fun `toEnum with custom mapping returns null for non-matching value`() {
        assertNull("invalid".toEnum<TestEnumWithCustomValue> { it.customValue })
        assertNull("FIRST".toEnum<TestEnumWithCustomValue> { it.customValue })
    }

    //endregion

    companion object {
        @JvmStatic
        fun testCasesToColourInt() = listOf(
            TestCaseToColourInt("#FF0000", 0xFFFF0000.toInt()),
            TestCaseToColourInt("#00FF00", 0xFF00FF00.toInt()),
            TestCaseToColourInt("#0000FF", 0xFF0000FF.toInt()),
            TestCaseToColourInt("#FFFFFF", 0xFFFFFFFF.toInt()),
            TestCaseToColourInt("#000000", 0xFF000000.toInt()),
            TestCaseToColourInt("FF0000", 0xFFFF0000.toInt()),
            TestCaseToColourInt("00FF00", 0xFF00FF00.toInt()),
            TestCaseToColourInt("0000FF", 0xFF0000FF.toInt()),
            TestCaseToColourInt("#80FF0000", 0x80FF0000.toInt()),
            TestCaseToColourInt("#00FF0000", 0x00FF0000),
            TestCaseToColourInt("#FFFF0000", 0xFFFF0000.toInt()),
            TestCaseToColourInt("80FF0000", 0x80FF0000.toInt()),
            TestCaseToColourInt("00FFFFFF", 0x00FFFFFF),
            TestCaseToColourInt("#ff0000", 0xFFFF0000.toInt()),
            TestCaseToColourInt("aabbcc", 0xFFAABBCC.toInt())
        )
    }
}
