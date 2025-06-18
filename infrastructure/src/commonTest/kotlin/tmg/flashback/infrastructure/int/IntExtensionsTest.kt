package tmg.flashback.infrastructure.int

import kotlin.test.Test
import kotlin.test.assertEquals

internal class IntExtensionsTest {

    private data class TestCaseExtend(
        val value: Int,
        val extendWithChar: Char?,
        val numberOfDigits: Int,
        val expected: String
    )

    private val testCasesExtend = listOf(
        TestCaseExtend(3,'0',4,"0003"),
        TestCaseExtend(0,'1',2,"10"),
        TestCaseExtend(-1,'2',4,"-1"),
        TestCaseExtend(34,'A',4,"AA34")
    )
    @Test
    fun `IntExtensions extend calculates correct string length`() {
        testCasesExtend.forEach { (value, extendWithChar, numberOfDigits, expected) ->
            assertEquals(expected, value.extend(numberOfDigits, extendWithChar ?: '0'))
        }
    }

    @Test
    fun `IntExtensions itemsOf check items generate properly`() {
        val indexCheck: List<Int> = listOf(0,1,2,3)
        assertEquals(indexCheck, 4.itemsOf { it })


        val lookup: Map<Int, String> = mapOf(
            0 to "1234",
            1 to "5678",
            2 to "1357"
        )
        val expected: List<String> = lookup.values.toList()
        assertEquals(expected, 3.itemsOf { lookup[it] })
    }
}