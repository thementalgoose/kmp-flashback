package tmg.flashback.feature.weekend.presentation.data.info

import dev.mokkery.MockMode
import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import tmg.flashback.feature.weekend.repositories.WeatherRepository
import tmg.flashback.formula1.model.Race
import tmg.flashback.formula1.model.model
import kotlin.test.Test
import kotlin.test.assertEquals

internal class InfoDataMapperTest {

    private lateinit var underTest: InfoDataMapperImpl

    private val mockWeatherRepository: WeatherRepository = mock(autoUnit)

    private fun initUnderTest() {
        every { mockWeatherRepository.weatherTemperatureMetric } returns true
        every { mockWeatherRepository.weatherWindspeedMetric } returns true
        underTest = InfoDataMapperImpl(
            weatherRepository = mockWeatherRepository
        )
    }

    @Test
    fun `data maps correctly`() {
        val input = Race.model()
        val expected = InfoModel.model()

        initUnderTest()

        assertEquals(expected, underTest(input))
    }
}