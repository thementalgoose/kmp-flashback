package tmg.flashback.formula1.utils

import tmg.flashback.formula1.model.LapTime
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.api.Assertions.assertEquals

class LapTimeUtilsKtTest {

    data class TestCaseTimeString(
        val time: String?,
        val expected: String?
    )

    @ParameterizedTest
    @MethodSource("testCaseTimes")
    fun `converting a supported string to a local time is done correctly`(testCase: TestCaseTimeString) {
        assertEquals(testCase.expected, testCase.time?.toLocalTime().toString())
    }

    data class TestCaseTimeMillis(
        val time: String?,
        val expected: Int
    )

    @ParameterizedTest
    @MethodSource("testCasesTimeMillis")
    fun `converting a string to a lap time object is done correctly`(testCase: TestCaseTimeMillis) {
        assertEquals(testCase.expected, testCase.time?.toLapTime()?.totalMillis ?: 0)
    }

    data class TestCaseAddDelta(
        val source: Int,
        val delta: Int,
        val expected: String
    )

    @ParameterizedTest
    @MethodSource("testCasesAddDelta")
    fun `adding millis to lap time results in correct lap time`(testCase: TestCaseAddDelta) {
        val lapTime: LapTime = LapTime(testCase.source)
        val lapTimeWithData: LapTime = lapTime.add(testCase.delta)
        assertEquals(testCase.expected, lapTimeWithData.toString())
    }

    data class TestCaseAddDeltaString(
        val source: Int,
        val delta: String,
        val expected: String
    )

    @ParameterizedTest
    @MethodSource("testCasesAddDeltaString")
    fun `adding delta string to lap time results in correct lap time`(testCase: TestCaseAddDeltaString) {
        val lapTime: LapTime = LapTime(testCase.source)
        val lapTimeWithDelta: LapTime = lapTime.addDelta(testCase.delta)
        assertEquals(testCase.expected, lapTimeWithDelta.toString())
    }

    data class TestCaseAddDeltaIndividual(
        val source: Int,
        val hour: Int,
        val min: Int,
        val sec: Int,
        val millis: Int,
        val expectedMillis: Int,
        val expectedString: String
    )

    @ParameterizedTest
    @MethodSource("testCaseAddDeltaIndividual")
    fun `adding delta hours mins seconds millis to lap time results in correct lap time`(testCase: TestCaseAddDeltaIndividual) {
        val lapTime: LapTime = LapTime(testCase.source)
        val lapTimeWithDelta = lapTime.addDelta(testCase.hour, testCase.min, testCase.sec, testCase.millis)

        assertEquals(testCase.expectedMillis, lapTimeWithDelta.totalMillis)
        assertEquals(testCase.expectedString, lapTimeWithDelta.toString())
    }

    companion object {
        @JvmStatic
        fun testCaseTimes() = listOf(
            TestCaseTimeString("1.123", "00:00:01.123"),
            TestCaseTimeString("-0.123", "00:00:00.123"),
            TestCaseTimeString("3:20.183", "00:03:20.183"),
            TestCaseTimeString("1:10:31.103", "01:10:31.103"),
            TestCaseTimeString("+1:123", "null"),
            TestCaseTimeString("invalid", "null"),
            TestCaseTimeString(null, "null")
        )

        @JvmStatic
        fun testCasesTimeMillis() = listOf(
            TestCaseTimeMillis("1.123", 1123),
            TestCaseTimeMillis("-0.123", 123),
            TestCaseTimeMillis("3:20.183", 200183),
            TestCaseTimeMillis("1:10:31.103", 4231103),
            TestCaseTimeMillis("+1:123", 0),
            TestCaseTimeMillis("invalid", 0),
            TestCaseTimeMillis(null, 0)
        )

        @JvmStatic
        fun testCasesAddDelta() = listOf(
            TestCaseAddDelta(91274, 4373, "1:35.647"),
            TestCaseAddDelta(187837, 19489, "3:27.326")
        )

        @JvmStatic
        fun testCasesAddDeltaString() = listOf(
            TestCaseAddDeltaString(38122, "+1.342", "39.464"),
            TestCaseAddDeltaString(438122, "+23:01.923", "30:20.045")
        )

        @JvmStatic
        fun testCaseAddDeltaIndividual() = listOf(
            TestCaseAddDeltaIndividual(5788, 0, 1, 2, 3, 67791, "1:07.791"),
            TestCaseAddDeltaIndividual(323294, 3, 23, 42, 232, 12545526, "3:29:05.526")
        )
    }
}