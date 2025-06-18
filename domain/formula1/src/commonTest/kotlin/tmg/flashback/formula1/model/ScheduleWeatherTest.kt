package tmg.flashback.formula1.model

import kotlin.test.Test
import kotlin.test.assertEquals

internal class ScheduleWeatherTest {

    companion object {
        private const val WIND_MS = 10.0
        private const val RAIN_PERCENT = 30.0
        private const val WIND_BEARING = 180
        private const val TEMP_MAX_C = 20.0
        private const val TEMP_MIN_C = 10.0

        private const val WIND_MPH_EXPECTED = 22.3694
        private const val WIND_KPH_EXPECTED = 36.0
        private const val TEMP_AVERAGE_C_EXPECTED = 15.0
        private const val TEMP_MAX_F_EXPECTED = 68.0
        private const val TEMP_MIN_F_EXPECTED = 50.0
        private const val TEMP_AVERAGE_F_EXPECTED = 59.0
    }

    private lateinit var underTest: ScheduleWeather

    private fun initUnderTest() {
        underTest = ScheduleWeather(
            rainPercent = RAIN_PERCENT,
            windMs = WIND_MS,
            windBearing = WIND_BEARING,
            tempMaxC = TEMP_MAX_C,
            tempMinC = TEMP_MIN_C,
            summary = listOf(WeatherType.CLOUDS_LIGHT)
        )
    }

    @Test
    fun `wind mph converts from ms`() {
        initUnderTest()
        assertEquals(WIND_MPH_EXPECTED, underTest.windMph, absoluteTolerance = 0.00001)
    }

    @Test
    fun `wind kph converts from ms`() {
        initUnderTest()
        assertEquals(WIND_KPH_EXPECTED, underTest.windKph)
    }

    @Test
    fun `temp average c`() {
        initUnderTest()
        assertEquals(TEMP_AVERAGE_C_EXPECTED, underTest.tempAverageC)
    }

    @Test
    fun `temp max f`() {
        initUnderTest()
        assertEquals(TEMP_MAX_F_EXPECTED, underTest.tempMaxF)
    }

    @Test
    fun `temp min f`() {
        initUnderTest()
        assertEquals(TEMP_MIN_F_EXPECTED, underTest.tempMinF)
    }

    @Test
    fun `temp average f`() {
        initUnderTest()
        assertEquals(TEMP_AVERAGE_F_EXPECTED, underTest.tempAverageF)
    }
}