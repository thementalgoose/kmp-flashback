package tmg.flashback.infrastructure.datetime

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

internal class LocalDateTest {

    //region daysBetween

    data class TestCaseDaysBetween(
        val start: LocalDate,
        val end: LocalDate,
        val expected: Int
    )

    @ParameterizedTest
    @MethodSource("testCasesDaysBetween")
    fun `daysBetween calculates correct number of days`(testCase: TestCaseDaysBetween) {
        assertEquals(testCase.expected, daysBetween(testCase.start, testCase.end), "Failed for start: ${testCase.start}, end: ${testCase.end}")
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

    data class TestCaseStartOfWeek(
        val date: LocalDate,
        val expectedStartOfWeek: LocalDate
    )

    @ParameterizedTest
    @MethodSource("testCasesStartOfWeek")
    fun `startOfWeek returns Monday of the week`(testCase: TestCaseStartOfWeek) {
        val result = testCase.date.startOfWeek()
        assertEquals(testCase.expectedStartOfWeek, result, "Failed for date: ${testCase.date}")
        assertEquals(DayOfWeek.MONDAY, result.dayOfWeek, "Start of week should be Monday for date: ${testCase.date}")
    }

    //endregion

    companion object {
        @JvmStatic
        fun testCasesDaysBetween() = listOf(
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
            )
        )

        @JvmStatic
        fun testCasesStartOfWeek() = listOf(
            TestCaseStartOfWeek(
                LocalDate(2024, 1, 1),
                LocalDate(2024, 1, 1)
            ),
            TestCaseStartOfWeek(
                LocalDate(2024, 1, 2),
                LocalDate(2024, 1, 1)
            ),
            TestCaseStartOfWeek(
                LocalDate(2024, 1, 3),
                LocalDate(2024, 1, 1)
            ),
            TestCaseStartOfWeek(
                LocalDate(2024, 1, 4),
                LocalDate(2024, 1, 1)
            ),
            TestCaseStartOfWeek(
                LocalDate(2024, 1, 5),
                LocalDate(2024, 1, 1)
            ),
            TestCaseStartOfWeek(
                LocalDate(2024, 1, 6),
                LocalDate(2024, 1, 1)
            ),
            TestCaseStartOfWeek(
                LocalDate(2024, 1, 7),
                LocalDate(2024, 1, 1)
            ),
            TestCaseStartOfWeek(
                LocalDate(2024, 1, 8),
                LocalDate(2024, 1, 8)
            ),
            TestCaseStartOfWeek(
                LocalDate(2024, 1, 14),
                LocalDate(2024, 1, 8)
            ),
            TestCaseStartOfWeek(
                LocalDate(2024, 2, 1),
                LocalDate(2024, 1, 29)
            ),
            TestCaseStartOfWeek(
                LocalDate(2024, 1, 3),
                LocalDate(2024, 1, 1)
            )
        )
    }
}
