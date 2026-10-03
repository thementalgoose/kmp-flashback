package tmg.flashback.infrastructure.datetime

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlin.test.Test
import kotlin.test.assertEquals

internal class LocalDateTimeTest {

    //region plus with DateBased units

    @Test
    fun `plus adds days correctly`() {
        val dateTime = LocalDateTime(2024, 1, 15, 10, 30, 0)
        val result = dateTime.plus(5, DateTimeUnit.DAY)
        assertEquals(LocalDateTime(2024, 1, 20, 10, 30, 0), result)
    }

    @Test
    fun `plus subtracts days with negative value`() {
        val dateTime = LocalDateTime(2024, 1, 15, 10, 30, 0)
        val result = dateTime.plus(-5, DateTimeUnit.DAY)
        assertEquals(LocalDateTime(2024, 1, 10, 10, 30, 0), result)
    }

    @Test
    fun `plus adds months correctly`() {
        val dateTime = LocalDateTime(2024, 1, 15, 10, 30, 0)
        val result = dateTime.plus(2, DateTimeUnit.MONTH)
        assertEquals(LocalDateTime(2024, 3, 15, 10, 30, 0), result)
    }

    @Test
    fun `plus adds years correctly`() {
        val dateTime = LocalDateTime(2024, 1, 15, 10, 30, 0)
        val result = dateTime.plus(1, DateTimeUnit.YEAR)
        assertEquals(LocalDateTime(2025, 1, 15, 10, 30, 0), result)
    }

    @Test
    fun `plus adds weeks correctly`() {
        val dateTime = LocalDateTime(2024, 1, 15, 10, 30, 0)
        val result = dateTime.plus(2, DateTimeUnit.WEEK)
        assertEquals(LocalDateTime(2024, 1, 29, 10, 30, 0), result)
    }

    @Test
    fun `plus preserves time when adding date-based units`() {
        val dateTime = LocalDateTime(2024, 1, 15, 14, 45, 30)
        val result = dateTime.plus(10, DateTimeUnit.DAY)
        assertEquals(14, result.hour)
        assertEquals(45, result.minute)
        assertEquals(30, result.second)
    }

    @Test
    fun `plus handles month boundary crossing`() {
        val dateTime = LocalDateTime(2024, 1, 31, 10, 30, 0)
        val result = dateTime.plus(1, DateTimeUnit.MONTH)
        assertEquals(LocalDateTime(2024, 2, 29, 10, 30, 0), result)
    }

    @Test
    fun `plus handles year boundary crossing`() {
        val dateTime = LocalDateTime(2024, 12, 15, 10, 30, 0)
        val result = dateTime.plus(1, DateTimeUnit.MONTH)
        assertEquals(LocalDateTime(2025, 1, 15, 10, 30, 0), result)
    }

    //endregion

    //region plus with TimeBased units

    @Test
    fun `plus adds hours correctly`() {
        val dateTime = LocalDateTime(2024, 1, 15, 10, 30, 0)
        val timeZone = TimeZone.UTC
        val result = dateTime.plus(5, DateTimeUnit.HOUR, timeZone)
        assertEquals(LocalDateTime(2024, 1, 15, 15, 30, 0), result)
    }

    @Test
    fun `plus adds minutes correctly`() {
        val dateTime = LocalDateTime(2024, 1, 15, 10, 30, 0)
        val timeZone = TimeZone.UTC
        val result = dateTime.plus(45, DateTimeUnit.MINUTE, timeZone)
        assertEquals(LocalDateTime(2024, 1, 15, 11, 15, 0), result)
    }

    @Test
    fun `plus adds seconds correctly`() {
        val dateTime = LocalDateTime(2024, 1, 15, 10, 30, 0)
        val timeZone = TimeZone.UTC
        val result = dateTime.plus(90, DateTimeUnit.SECOND, timeZone)
        assertEquals(LocalDateTime(2024, 1, 15, 10, 31, 30), result)
    }

    @Test
    fun `plus subtracts time with negative value`() {
        val dateTime = LocalDateTime(2024, 1, 15, 10, 30, 0)
        val timeZone = TimeZone.UTC
        val result = dateTime.plus(-2, DateTimeUnit.HOUR, timeZone)
        assertEquals(LocalDateTime(2024, 1, 15, 8, 30, 0), result)
    }

    @Test
    fun `plus handles day boundary crossing with time units`() {
        val dateTime = LocalDateTime(2024, 1, 15, 22, 30, 0)
        val timeZone = TimeZone.UTC
        val result = dateTime.plus(5, DateTimeUnit.HOUR, timeZone)
        assertEquals(LocalDateTime(2024, 1, 16, 3, 30, 0), result)
    }

    @Test
    fun `plus handles midnight boundary`() {
        val dateTime = LocalDateTime(2024, 1, 15, 23, 59, 59)
        val timeZone = TimeZone.UTC
        val result = dateTime.plus(1, DateTimeUnit.SECOND, timeZone)
        assertEquals(LocalDateTime(2024, 1, 16, 0, 0, 0), result)
    }

    //endregion
}
