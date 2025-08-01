package tmg.flashback.formula1.model

import kotlin.time.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.FixedOffsetTimeZone
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.UtcOffset
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.days

internal class TimestampTest {

    @Test
    fun `perform runs if time when time is supplied`() {
        val localDate: LocalDate = LocalDate(2020, 1, 1)
        val localTime: LocalTime = LocalTime(12, 0, 0)
        val zone = TimeZone.UTC

        val sut = Timestamp(localDate, localTime, zone)

        val resultDateTimeUTC: LocalDateTime = sut.utcLocalDateTime
        val resultDateTimeDevice: LocalDateTime = sut.deviceLocalDateTime

        assertEquals(LocalDateTime(2020, 1, 1, 12, 0), resultDateTimeUTC)
        assertEquals(LocalDateTime(2020, 1, 1, 12, 0), resultDateTimeDevice)
    }

    private data class TestCaseDateTimes(
        val offset: Int,
        val utcHour: Int,
        val deviceHour: Int
    )
    private val testCasesDateTimes = listOf(
        TestCaseDateTimes(-9,12,3),
        TestCaseDateTimes(-8,12,4),
        TestCaseDateTimes(-7,12,5),
        TestCaseDateTimes(-6,12,6),
        TestCaseDateTimes(-5,12,7),
        TestCaseDateTimes(-4,12,8),
        TestCaseDateTimes(-3,12,9),
        TestCaseDateTimes(-2,12,10),
        TestCaseDateTimes(-1,12,11),
        TestCaseDateTimes(0,12,12),
        TestCaseDateTimes(1,12,13),
        TestCaseDateTimes(2,12,14),
        TestCaseDateTimes(3,12,15),
        TestCaseDateTimes(4,12,16),
        TestCaseDateTimes(5,12,17),
        TestCaseDateTimes(6,12,18),
        TestCaseDateTimes(7,12,19),
        TestCaseDateTimes(8,12,20),
        TestCaseDateTimes(9,12,21),
    )
    @Test
    fun `perform runs if time when time is supplied and adheres to zone offset`() {
        testCasesDateTimes.forEach { (offset, utcHour, deviceHour) ->
            val localDate = LocalDate(2020, 1, 1)
            val localTime = LocalTime(utcHour, 0, 0)
            val zone = FixedOffsetTimeZone(UtcOffset(offset))

            val sut = Timestamp(localDate, localTime, zone)

            val resultDateTimeUTC: LocalDateTime = sut.utcLocalDateTime
            val resultDateTimeDevice: LocalDateTime = sut.deviceLocalDateTime

            assertEquals(LocalDateTime(2020, 1, 1, 12, 0), resultDateTimeUTC)
            assertEquals(LocalDateTime(2020, 1, 1, deviceHour, 0), resultDateTimeDevice)
        }
    }

    @Test
    fun `string representation of original time`() {
        val localDate = LocalDate(2020, 1, 1)
        val localTime = LocalTime(12, 0, 0)
        val zone = TimeZone.UTC

        val sut = Timestamp(localDate, localTime, zone)

        assertEquals("2020-01-01 12:00:00", sut.string())
    }

    //region isInPast

    @Test
    fun `is in past returns true when date is yesterday and original time is before`() {
        val date = LocalDate.now().minus(1, DateTimeUnit.DAY)
        val time = LocalTime.now() .plus(1)
        val zone = TimeZone.UTC

        val sut = Timestamp(date, time, zone)

        assertTrue(sut.isInPast)
    }

    @Test
    fun `is in past returns true when date is today and original time is in past`() {
        val date = LocalDate.now()
        val time = LocalTime.now().minus(1)
        val zone = TimeZone.UTC

        val sut = Timestamp(date, time, zone)

        assertTrue(sut.isInPast)
    }

    @Test
    fun `is in past returns false when date is today and original time is now`() {
        val date = LocalDate.now()
        val time = LocalTime.now().plus(1)
        val zone = TimeZone.UTC

        val sut = Timestamp(date, time, zone)

        assertFalse(sut.isInPast)
    }

    @Test
    fun `is in past returns false when date is today and original time is in future`() {
        val date = LocalDate.now()
        val time = LocalTime.now().plus(1)
        val zone = TimeZone.UTC

        val sut = Timestamp(date, time, zone)

        assertFalse(sut.isInPast)
    }

    @Test
    fun `is in past returns false when date is tomorrow and original time is later`() {
        val date = LocalDate.now().plus(1L, DateTimeUnit.DAY)
        val time = LocalTime.now().minus(1)
        val zone = TimeZone.UTC

        val sut = Timestamp(date, time, zone)

        assertFalse(sut.isInPast)
    }

    //endregion

    //region isInPast

    @Test
    fun `is today returns false when date is yesterday and original time is before`() {
        val date = LocalDate.now().minus(1, DateTimeUnit.DAY)
        val time = LocalTime.now().plus(1)
        val zone = TimeZone.UTC

        val sut = Timestamp(date, time, zone)

        assertFalse(sut.isToday)
    }

    @Test
    fun `is today returns true when date is today and original time is today`() {
        val date = LocalDate.now()
        val time = LocalTime.now().minus(1)
        val zone = TimeZone.UTC

        val sut = Timestamp(date, time, zone)

        assertTrue(sut.isToday)
    }

    @Test
    fun `is today returns true when date is today and original time is now`() {
        val date = LocalDate.now()
        val time = LocalTime.now().plus(1)
        val zone = TimeZone.UTC

        val sut = Timestamp(date, time, zone)

        assertTrue(sut.isToday)
    }

    @Test
    fun `is today returns true when date is today and original time is in future`() {
        val date = LocalDate.now()
        val time = LocalTime.now().plus(1)
        val zone = TimeZone.UTC

        val sut = Timestamp(date, time, zone)

        assertTrue(sut.isToday)
    }

    @Test
    fun `is today returns false when date is tomorrow and original time is later`() {
        val date = LocalDate.now().plus(1, DateTimeUnit.DAY)
        val time = LocalTime.now().minus(1)
        val zone = TimeZone.UTC

        val sut = Timestamp(date, time, zone)

        assertFalse(sut.isToday)
    }

    //endregion


    private fun LocalDateTime.Companion.now(): LocalDateTime {
        return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    }

    private fun LocalDate.Companion.now(): LocalDate {
        return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    }

    private fun LocalTime.Companion.now(): LocalTime {
        return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time
    }

    private fun LocalTime.minus(seconds: Int): LocalTime = this.plus(-seconds)
    private fun LocalTime.plus(seconds: Int): LocalTime {
        var millis = this.toMillisecondOfDay()
        millis += (seconds * 1000)
        return LocalTime.fromMillisecondOfDay(millis)
    }
}