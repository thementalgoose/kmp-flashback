package tmg.flashback.formula1.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class LapTimeTest {

    @Test
    fun `no time returns true when using default initializer`() {
        val model = LapTime()
        assertTrue(model.noTime)
        assertEquals(0, model.totalMillis)
    }

    @Test
    fun `no time returns false when specifying a laptime via millis`() {
        val model = LapTime(1001)
        assertFalse(model.noTime)
    }

    @Test
    fun `no time returns false when specifying a laptime via hours, mins, seconds`() {
        val model = LapTime(0, 1, 2, 3)
        assertFalse(model.noTime)
    }

    @Test
    fun `time string returns no time when no time true`() {
        val model = LapTime()
        assertTrue(model.noTime)
        assertEquals("No time", model.time)
    }

    private data class TestCaseTime(
        val hours: Int,
        val mins: Int,
        val seconds: Int,
        val milliseconds: Int,
        val expected: String
    )

    private val testCasesTime = listOf(
        TestCaseTime(-1,-1,-1,-1,"No time"),
        TestCaseTime(0,0,0,0,"0.000"),
        TestCaseTime(0,0,1,1,"1.001"),
        TestCaseTime(0,1,1,1,"1:01.001"),
        TestCaseTime(1,1,1,1,"1:01:01.001"),
    )
    @Test
    fun `time returns valid value`() {
        testCasesTime.forEach { (hours, mins, seconds, milliseconds, expected) ->
            val model = LapTime(hours, mins, seconds, milliseconds)
            assertEquals(expected, model.time)
        }
    }

    private val testCasesContentDescription = listOf(
        TestCaseTime(-1,-1,-1,-1,"No time"),
        TestCaseTime(0,0,0,0,"0 seconds and 000 milliseconds"),
        TestCaseTime(0,0,1,1,"1 seconds and 001 milliseconds"),
        TestCaseTime(0,1,1,1,"1 minutes, 01 seconds and 001 milliseconds"),
        TestCaseTime(1,1,1,1,"1 hours, 1 minutes, 01 seconds and 001 milliseconds"),
    )
    @Test
    fun `content description returns valid value`() {
        testCasesContentDescription.forEach { (hours, mins, seconds, milliseconds, expected) ->
            val model = LapTime(hours, mins, seconds, milliseconds)
            assertEquals(expected, model.contentDescription)
        }
    }

    private data class TestCaseDelta(
        val fromMillis: Int,
        val toMillis: Int,
        val delta: String
    )

    private val testCasesDelta = listOf(
        TestCaseDelta(1001,1002,"+0.001"),
        TestCaseDelta(1003,1002,"-0.001"),
        TestCaseDelta(132456,654321,"+8:41.865")
    )
    @Test
    fun `deltaTo shows correct delta`() {
        testCasesDelta.forEach { (fromMillis, toMillis, delta) ->
            val model = LapTime(fromMillis)
            val toModel = LapTime(toMillis)

            assertEquals(delta, model.deltaTo(toModel))
        }
    }

    @Test
    fun `deltaTo returns 0 if delta is identical`() {
        val model = LapTime(10010)
        assertEquals("0.000", model.deltaTo(model))
    }

    @Test
    fun `total millis counts hours mins seconds`() {
        val hours = 1
        val mins = 6
        val seconds = 6
        val millis = 101
        val expected = 3966101

        val model = LapTime(hours, mins, seconds, millis)

        assertEquals(expected, model.totalMillis)
    }
}