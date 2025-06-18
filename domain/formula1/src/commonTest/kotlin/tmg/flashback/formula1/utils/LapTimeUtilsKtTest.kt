package tmg.flashback.formula1.utils

import tmg.flashback.formula1.model.LapTime
import kotlin.test.Test
import kotlin.test.assertEquals

class LapTimeUtilsKtTest {

    private data class TestCaseTimeString(
        val time: String?,
        val expected: String?
    )
    private val testCaseTimes = listOf(
        TestCaseTimeString("1.123", "00:00:01.123"),
        TestCaseTimeString("-0.123", "00:00:00.123"),
        TestCaseTimeString("3:20.183", "00:03:20.183"),
        TestCaseTimeString("1:10:31.103", "01:10:31.103"),
        TestCaseTimeString("+1:123", "null"),
        TestCaseTimeString("invalid", "null"),
        TestCaseTimeString(null, "null")
    )
    @Test
    fun `converting a supported string to a local time is done correctly`() {
        testCaseTimes.forEach { (time, expected) ->
            assertEquals(expected, time?.toLocalTime().toString())
        }
    }

    private data class TestCaseTimeMillis(
        val time: String?,
        val expected: Int
    )
    private val testCasesTimeMillis = listOf(
        TestCaseTimeMillis("1.123",1123),
        TestCaseTimeMillis("-0.123",123),
        TestCaseTimeMillis("3:20.183",200183),
        TestCaseTimeMillis("1:10:31.103",4231103),
        TestCaseTimeMillis("+1:123",0),
        TestCaseTimeMillis("invalid",0),
        TestCaseTimeMillis(null,0),
    )
    @Test
    fun `converting a string to a lap time object is done correctly`() {
        testCasesTimeMillis.forEach { (time, expected) ->
            assertEquals(expected, time?.toLapTime()?.totalMillis ?: 0)
        }
    }

    private data class TestCaseAddDelta(
        val source: Int,
        val delta: Int,
        val expected: String
    )
    private val testCasesAddDelta = listOf(
        TestCaseAddDelta(91274,4373,"1:35.647"),
        TestCaseAddDelta(187837,19489,"3:27.326")
    )
    @Test
    fun `adding millis to lap time results in correct lap time`() {
        testCasesAddDelta.forEach { (source, delta, expected) ->
            val lapTime: LapTime = LapTime(source)
            val lapTimeWithData: LapTime = lapTime.add(delta)

            assertEquals(expected, lapTimeWithData.toString())
        }
    }

    private data class TestCaseAddDeltaString(
        val source: Int,
        val delta: String,
        val expected: String
    )
    private val testCasesAddDeltaString = listOf(
        TestCaseAddDeltaString(38122,"+1.342","39.464"),
        TestCaseAddDeltaString(438122,"+23:01.923", "30:20.045")
    )
    @Test
    fun `adding delta string to lap time results in correct lap time`() {
        testCasesAddDeltaString.forEach { (source, delta, expected) ->
            val lapTime: LapTime = LapTime(source)
            val lapTimeWithDelta: LapTime = lapTime.addDelta(delta)

            assertEquals(expected, lapTimeWithDelta.toString())
        }
    }

    private data class TestCaseAddDeltaIndividual(
        val source: Int,
        val hour: Int,
        val min: Int,
        val sec: Int,
        val millis: Int,
        val expectedMillis: Int,
        val expectedString: String
    )
    private val testCaseAddDeltaIndividual = listOf(
        TestCaseAddDeltaIndividual(5788,0,1,2,3,67791,"1:07.791"),
        TestCaseAddDeltaIndividual(323294,3,23,42,232,12545526,"3:29:05.526")
    )
    @Test
    fun `adding delta hours mins seconds millis to lap time results in correct lap time`() {
        testCaseAddDeltaIndividual.forEach { (source, hour, min, sec, millis, expectedMillis, expectedString) ->
            val lapTime: LapTime = LapTime(source)
            val lapTimeWithDelta = lapTime.addDelta(hour, min, sec, millis)

            assertEquals(expectedMillis, lapTimeWithDelta.totalMillis)
            assertEquals(expectedString, lapTimeWithDelta.toString())
        }
    }
}