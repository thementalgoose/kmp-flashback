package tmg.flashback.infrastructure.datetime

import kotlinx.datetime.LocalTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

internal class LocalTimeTest {

    //region requireFromTime

    @Test
    fun `requireFromTime parses HH-mm format`() {
        assertEquals(LocalTime(10, 30), requireFromTime("10:30"))
        assertEquals(LocalTime(0, 0), requireFromTime("00:00"))
        assertEquals(LocalTime(23, 59), requireFromTime("23:59"))
    }

    @Test
    fun `requireFromTime parses HH-mm-ss format`() {
        assertEquals(LocalTime(10, 30, 45), requireFromTime("10:30:45"))
        assertEquals(LocalTime(0, 0, 0), requireFromTime("00:00:00"))
        assertEquals(LocalTime(23, 59, 59), requireFromTime("23:59:59"))
    }

    @Test
    fun `requireFromTime parses HH-mm-ssZ format`() {
        assertEquals(LocalTime(10, 30, 45), requireFromTime("10:30:45Z"))
        assertEquals(LocalTime(0, 0, 0), requireFromTime("00:00:00Z"))
        assertEquals(LocalTime(23, 59, 59), requireFromTime("23:59:59Z"))
    }

    @Test
    fun `requireFromTime throws IllegalArgumentException for invalid format`() {
        assertFailsWith<IllegalArgumentException> {
            requireFromTime("invalid")
        }
        assertFailsWith<IllegalArgumentException> {
            requireFromTime("10:30:45:00")
        }
        assertFailsWith<IllegalArgumentException> {
            requireFromTime("10-30-45")
        }
        assertFailsWith<IllegalArgumentException> {
            requireFromTime("")
        }
        assertFailsWith<IllegalArgumentException> {
            requireFromTime("25:00")
        }
        assertFailsWith<IllegalArgumentException> {
            requireFromTime("10:60")
        }
    }

    //endregion

    //region fromTime

    @Test
    fun `fromTime returns LocalTime for valid HH-mm format`() {
        assertEquals(LocalTime(10, 30), fromTime("10:30"))
        assertEquals(LocalTime(23, 59), fromTime("23:59"))
    }

    @Test
    fun `fromTime returns LocalTime for valid HH-mm-ss format`() {
        assertEquals(LocalTime(10, 30, 45), fromTime("10:30:45"))
    }

    @Test
    fun `fromTime returns LocalTime for valid HH-mm-ssZ format`() {
        assertEquals(LocalTime(10, 30, 45), fromTime("10:30:45Z"))
    }

    @Test
    fun `fromTime returns null for null input`() {
        assertNull(fromTime(null))
    }

    @Test
    fun `fromTime returns null for invalid format`() {
        assertNull(fromTime("invalid"))
        assertNull(fromTime("10-30-45"))
        assertNull(fromTime(""))
        assertNull(fromTime("25:00"))
    }

    //endregion
}
