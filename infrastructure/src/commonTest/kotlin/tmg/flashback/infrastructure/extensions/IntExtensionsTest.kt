package tmg.flashback.infrastructure.extensions

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull

internal class IntExtensionsTest {

    //region extend

    data class TestCaseExtend(
        val value: Int,
        val extendWithChar: Char?,
        val numberOfDigits: Int,
        val expected: String
    )

    @ParameterizedTest
    @MethodSource("testCasesExtend")
    fun `extend pads integer with specified character to reach desired length`(testCase: TestCaseExtend) {
        assertEquals(testCase.expected, testCase.value.extend(testCase.numberOfDigits, testCase.extendWithChar ?: '0'), "Failed for value: ${testCase.value}")
    }

    @Test
    fun `extend uses default character when not specified`() {
        assertEquals("007", 7.extend(3))
        assertEquals("42", 42.extend(2))
    }

    //endregion

    //region itemsOf

    @Test
    fun `itemsOf creates list with correct indices`() {
        val indexCheck: List<Int> = listOf(0, 1, 2, 3)
        assertEquals(indexCheck, 4.itemsOf { it })
    }

    @Test
    fun `itemsOf creates list with transformed values`() {
        val lookup: Map<Int, String> = mapOf(
            0 to "1234",
            1 to "5678",
            2 to "1357"
        )
        val expected: List<String?> = lookup.values.toList()
        assertEquals(expected, 3.itemsOf { lookup[it] })
    }

    @Test
    fun `itemsOf with zero creates empty list`() {
        assertEquals(emptyList<Int>(), 0.itemsOf { it })
    }

    @Test
    fun `itemsOf creates list of custom objects`() {
        data class TestItem(val index: Int, val value: String)
        val result = 3.itemsOf { TestItem(it, "item$it") }
        assertEquals(
            listOf(
                TestItem(0, "item0"),
                TestItem(1, "item1"),
                TestItem(2, "item2")
            ),
            result
        )
    }

    //endregion

    //region toEnum

    private enum class TestEnum {
        FIRST,
        SECOND,
        THIRD
    }

    private enum class TestEnumWithCustomOrdinal(val customOrdinal: Int) {
        VALUE_A(10),
        VALUE_B(20),
        VALUE_C(30)
    }

    @Test
    fun `toEnum returns correct enum value by ordinal`() {
        assertEquals(TestEnum.FIRST, 0.toEnum<TestEnum>())
        assertEquals(TestEnum.SECOND, 1.toEnum<TestEnum>())
        assertEquals(TestEnum.THIRD, 2.toEnum<TestEnum>())
    }

    @Test
    fun `toEnum returns null for invalid ordinal`() {
        assertNull((-1).toEnum<TestEnum>())
        assertNull(3.toEnum<TestEnum>())
        assertNull(100.toEnum<TestEnum>())
    }

    @Test
    fun `toEnum with custom mapping returns correct enum value`() {
        assertEquals(
            TestEnumWithCustomOrdinal.VALUE_A,
            10.toEnum<TestEnumWithCustomOrdinal> { it.customOrdinal }
        )
        assertEquals(
            TestEnumWithCustomOrdinal.VALUE_B,
            20.toEnum<TestEnumWithCustomOrdinal> { it.customOrdinal }
        )
        assertEquals(
            TestEnumWithCustomOrdinal.VALUE_C,
            30.toEnum<TestEnumWithCustomOrdinal> { it.customOrdinal }
        )
    }

    @Test
    fun `toEnum with custom mapping returns null for non-matching value`() {
        assertNull(0.toEnum<TestEnumWithCustomOrdinal> { it.customOrdinal })
        assertNull(15.toEnum<TestEnumWithCustomOrdinal> { it.customOrdinal })
    }

    //endregion

    //region ordinalAbbreviation

    data class TestCaseOrdinal(
        val value: Int,
        val expected: String
    )

    @ParameterizedTest
    @MethodSource("testCasesOrdinal")
    fun `ordinalAbbreviation returns correct suffix for integers`(testCase: TestCaseOrdinal) {
        assertEquals(testCase.expected, testCase.value.ordinalAbbreviation, "Failed for value: ${testCase.value}")
    }

    //endregion

    companion object {
        @JvmStatic
        fun testCasesExtend() = listOf(
            TestCaseExtend(3, '0', 4, "0003"),
            TestCaseExtend(0, '1', 2, "10"),
            TestCaseExtend(-1, '2', 4, "-1"),
            TestCaseExtend(34, 'A', 4, "AA34"),
            TestCaseExtend(123, '0', 3, "123"),
            TestCaseExtend(1234, '0', 3, "1234"),
            TestCaseExtend(0, '0', 1, "0"),
            TestCaseExtend(9, '0', 2, "09")
        )

        @JvmStatic
        fun testCasesOrdinal() = listOf(
            TestCaseOrdinal(11, "11th"),
            TestCaseOrdinal(12, "12th"),
            TestCaseOrdinal(13, "13th"),
            TestCaseOrdinal(1, "1st"),
            TestCaseOrdinal(2, "2nd"),
            TestCaseOrdinal(3, "3rd"),
            TestCaseOrdinal(4, "4th"),
            TestCaseOrdinal(5, "5th"),
            TestCaseOrdinal(6, "6th"),
            TestCaseOrdinal(7, "7th"),
            TestCaseOrdinal(8, "8th"),
            TestCaseOrdinal(9, "9th"),
            TestCaseOrdinal(10, "10th"),
            TestCaseOrdinal(21, "21st"),
            TestCaseOrdinal(22, "22nd"),
            TestCaseOrdinal(23, "23rd"),
            TestCaseOrdinal(31, "31st"),
            TestCaseOrdinal(32, "32nd"),
            TestCaseOrdinal(33, "33rd"),
            TestCaseOrdinal(14, "14th"),
            TestCaseOrdinal(20, "20th"),
            TestCaseOrdinal(24, "24th"),
            TestCaseOrdinal(100, "100th"),
            TestCaseOrdinal(101, "101st"),
            TestCaseOrdinal(111, "111st"),
            TestCaseOrdinal(112, "112nd"),
            TestCaseOrdinal(113, "113rd"),
            TestCaseOrdinal(0, "0th"),
            TestCaseOrdinal(-1, "-1st")
        )
    }
}
