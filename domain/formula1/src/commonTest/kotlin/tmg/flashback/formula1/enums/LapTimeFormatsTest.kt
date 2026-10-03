package tmg.flashback.formula1.enums

import tmg.flashback.formula1.enums.LapTimeFormats.HOUR_MIN_SECOND_MILLIS
import tmg.flashback.formula1.enums.LapTimeFormats.MIN_SECOND_MILLIS
import tmg.flashback.formula1.enums.LapTimeFormats.SECOND_MILLIS
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class LapTimeFormatsTest {

    data class TestCase(
        val lapTime: String,
        val expectedIsValid: Boolean
    )

    @ParameterizedTest
    @MethodSource("testCasesSeconds")
    fun `regex for seconds matches seconds properly`(testCase: TestCase) {
        assertEquals(SECOND_MILLIS.regex.matches(testCase.lapTime), testCase.expectedIsValid, "Expecting ${testCase.lapTime} to be valid=${testCase.expectedIsValid}")
    }

    @ParameterizedTest
    @MethodSource("testCasesMinutes")
    fun `regex for minutes matches minutes properly`(testCase: TestCase) {
        assertEquals(MIN_SECOND_MILLIS.regex.matches(testCase.lapTime), testCase.expectedIsValid)
    }

    @ParameterizedTest
    @MethodSource("testCasesHours")
    fun `regex for hours matches hours properly`(testCase: TestCase) {
        assertEquals(HOUR_MIN_SECOND_MILLIS.regex.matches(testCase.lapTime), testCase.expectedIsValid)
    }

    companion object {
        @JvmStatic
        fun testCasesSeconds() = listOf(
            TestCase("00:000", true),
            TestCase("59:999", true),
            TestCase("23:012", true),
            TestCase("1:012", true),
            TestCase("9:012", true),
            TestCase("0:012", true),
            TestCase("103:0", false),
            TestCase("60:000", false),
            TestCase("34:12", true),
            TestCase("1:34:120", false),
            TestCase("oo:02", false)
        )

        @JvmStatic
        fun testCasesMinutes() = listOf(
            TestCase("1:00:000", true),
            TestCase("59:59:999", true),
            TestCase("3:09:012", true),
            TestCase("4:00:012", true),
            TestCase("1:34:120", true),
            TestCase("60:59:999", false),
            TestCase("84:23:012", false),
            TestCase("1:1:012", false),
            TestCase("103", false),
            TestCase("60:000", false),
            TestCase("34:12", false),
            TestCase("oo:02", false)
        )

        @JvmStatic
        fun testCasesHours() = listOf(
            TestCase("1:03:00:000", true),
            TestCase("23:02:59:999", true),
            TestCase("04:21:10:012", true),
            TestCase("18:00:00:000", true),
            TestCase("9:59:59:999", true),
            TestCase("1:20:00:012", true),
            TestCase("01:03:00:000", true),
            TestCase("9:99:99:999", false),
            TestCase("23:62:59:999", false),
            TestCase("24:00:00:000", false),
            TestCase("23:012", false),
            TestCase("60:000", false),
            TestCase("103", false),
            TestCase("34:12", false),
            TestCase("1:34:120", false),
            TestCase("oo:02", false)
        )
    }
}