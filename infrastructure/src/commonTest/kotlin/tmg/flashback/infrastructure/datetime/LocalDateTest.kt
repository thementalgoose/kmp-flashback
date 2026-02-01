package tmg.flashback.infrastructure.datetime

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

internal class LocalDateTest {

    //region daysBetween

    private data class TestCaseDaysBetween(
        val start: LocalDate,
        val end: LocalDate,
        val expected: Int
    )

    private val testCasesDaysBetween = listOf(
        TestCaseDaysBetween(
            LocalDate(2024, 1, 1),
            LocalDate(2024, 1, 1),
            0
        ),
        TestCaseDaysBetween(
            LocalDate(2024, 1, 1),
            LocalDate(2024, 1, 2),
            1
        ),
        TestCaseDaysBetween(
            LocalDate(2024, 1, 1),
            LocalDate(2024, 1, 31),
            30
        ),
        TestCaseDaysBetween(
            LocalDate(2024, 1, 1),
            LocalDate(2024, 12, 31),
            365
        ),
        TestCaseDaysBetween(
            LocalDate(2024, 1, 2),
            LocalDate(2024, 1, 1),
            -1
        ),
        TestCaseDaysBetween(
            LocalDate(2023, 1, 1),
            LocalDate(2024, 1, 1),
            365
        ),
        TestCaseDaysBetween(
            LocalDate(2024, 2, 28),
            LocalDate(2024, 3, 1),
            2
        ),
    )

    @Test
    fun `daysBetween calculates correct number of days`() {
        testCasesDaysBetween.forEach { (start, end, expected) ->
            assertEquals(expected, daysBetween(start, end), "Failed for start: $start, end: $end")
        }
    }

    //endregion

    //region requireFromDate

    @Test
    fun `requireFromDate parses YYYY-M-D format`() {
        assertEquals(LocalDate(2024, 3, 5), requireFromDate("2024-3-5"))
        assertEquals(LocalDate(2024, 12, 25), requireFromDate("2024-12-25"))
    }

    @Test
    fun `requireFromDate parses YYYY-MM-D format`() {
        assertEquals(LocalDate(2024, 3, 5), requireFromDate("2024-03-5"))
        assertEquals(LocalDate(2024, 12, 5), requireFromDate("2024-12-5"))
    }

    @Test
    fun `requireFromDate parses YYYY-MM-DD format`() {
        assertEquals(LocalDate(2024, 3, 5), requireFromDate("2024-03-05"))
        assertEquals(LocalDate(2024, 12, 25), requireFromDate("2024-12-25"))
    }

    @Test
    fun `requireFromDate parses YYYY-MMM-D format`() {
        assertEquals(LocalDate(2024, 3, 5), requireFromDate("2024-Mar-5"))
        assertEquals(LocalDate(2024, 12, 5), requireFromDate("2024-Dec-5"))
    }

    @Test
    fun `requireFromDate parses YYYY-MMM-DD format`() {
        assertEquals(LocalDate(2024, 3, 5), requireFromDate("2024-Mar-05"))
        assertEquals(LocalDate(2024, 12, 25), requireFromDate("2024-Dec-25"))
    }

    @Test
    fun `requireFromDate parses YYYY-MMMM-D format`() {
        assertEquals(LocalDate(2024, 3, 5), requireFromDate("2024-March-05"))
        assertEquals(LocalDate(2024, 12, 5), requireFromDate("2024-December-05"))
    }

    @Test
    fun `requireFromDate parses YYYY-MMMM-DD format`() {
        assertEquals(LocalDate(2024, 3, 5), requireFromDate("2024-March-05"))
        assertEquals(LocalDate(2024, 12, 25), requireFromDate("2024-December-25"))
    }

    @Test
    fun `requireFromDate throws IllegalArgumentException for invalid format`() {
        assertFailsWith<IllegalArgumentException> {
            requireFromDate("invalid")
        }
        assertFailsWith<IllegalArgumentException> {
            requireFromDate("05-03-2024")
        }
        assertFailsWith<IllegalArgumentException> {
            requireFromDate("2024/03/05")
        }
        assertFailsWith<IllegalArgumentException> {
            requireFromDate("")
        }
    }

    //endregion

    //region fromDate

    @Test
    fun `fromDate returns LocalDate for valid format`() {
        assertEquals(LocalDate(2024, 3, 5), fromDate("2024-03-05"))
        assertEquals(LocalDate(2024, 12, 25), fromDate("2024-12-25"))
    }

    @Test
    fun `fromDate returns null for null input`() {
        assertNull(fromDate(null))
    }

    @Test
    fun `fromDate returns null for invalid format`() {
        assertNull(fromDate("invalid"))
        assertNull(fromDate("05-03-2024"))
        assertNull(fromDate(""))
    }

    //endregion

    //region startOfWeek

    private data class TestCaseStartOfWeek(
        val date: LocalDate,
        val expectedStartOfWeek: LocalDate
    )

    private val testCasesStartOfWeek = listOf(
        // Monday is the start of week (ordinal 0)
        TestCaseStartOfWeek(
            LocalDate(2024, 1, 1), // Monday
            LocalDate(2024, 1, 1)
        ),
        TestCaseStartOfWeek(
            LocalDate(2024, 1, 2), // Tuesday
            LocalDate(2024, 1, 1)
        ),
        TestCaseStartOfWeek(
            LocalDate(2024, 1, 3), // Wednesday
            LocalDate(2024, 1, 1)
        ),
        TestCaseStartOfWeek(
            LocalDate(2024, 1, 4), // Thursday
            LocalDate(2024, 1, 1)
        ),
        TestCaseStartOfWeek(
            LocalDate(2024, 1, 5), // Friday
            LocalDate(2024, 1, 1)
        ),
        TestCaseStartOfWeek(
            LocalDate(2024, 1, 6), // Saturday
            LocalDate(2024, 1, 1)
        ),
        TestCaseStartOfWeek(
            LocalDate(2024, 1, 7), // Sunday
            LocalDate(2024, 1, 1)
        ),
        // Next week
        TestCaseStartOfWeek(
            LocalDate(2024, 1, 8), // Monday
            LocalDate(2024, 1, 8)
        ),
        TestCaseStartOfWeek(
            LocalDate(2024, 1, 14), // Sunday
            LocalDate(2024, 1, 8)
        ),
        // Cross month boundary
        TestCaseStartOfWeek(
            LocalDate(2024, 2, 1), // Thursday
            LocalDate(2024, 1, 29)
        ),
        // Cross year boundary
        TestCaseStartOfWeek(
            LocalDate(2024, 1, 3), // Wednesday
            LocalDate(2024, 1, 1)
        ),
    )

    @Test
    fun `startOfWeek returns Monday of the week`() {
        testCasesStartOfWeek.forEach { (date, expectedStartOfWeek) ->
            val result = date.startOfWeek()
            assertEquals(expectedStartOfWeek, result, "Failed for date: $date")
            assertEquals(DayOfWeek.MONDAY, result.dayOfWeek, "Start of week should be Monday for date: $date")
        }
    }

    //endregion
}
