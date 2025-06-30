package tmg.flashback.presentation.settings.widgets

import app.cash.turbine.test
import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import kotlinx.coroutines.test.runTest
import tmg.flashback.widgets.upnext.repositories.UpNextWidgetRepository
import kotlin.test.Test
import kotlin.test.assertEquals

internal class SettingsWeatherViewModelTest {

    private lateinit var underTest: SettingsWidgetsViewModel

    private val mockUpNextWidgetRepository: UpNextWidgetRepository = mock(autoUnit)

    private fun initUnderTest() {
        underTest = SettingsWidgetsViewModel(
            widgetsRepository = mockUpNextWidgetRepository
        )
    }

    @Test
    fun `link to event is populated from repo`() = runTest {
        every { mockUpNextWidgetRepository.showBackground } returns false
        every { mockUpNextWidgetRepository.showWeather } returns false
        every { mockUpNextWidgetRepository.deeplinkToEvent } returns true
        initUnderTest()
        underTest.uiState.test {
            assertEquals(true, awaitItem().linkToEvent)
        }
    }

    @Test
    fun `show weather is populated from repo`() = runTest {
        every { mockUpNextWidgetRepository.showBackground } returns false
        every { mockUpNextWidgetRepository.showWeather } returns true
        every { mockUpNextWidgetRepository.deeplinkToEvent } returns false
        initUnderTest()
        underTest.uiState.test {
            assertEquals(true, awaitItem().linkToEvent)
        }
    }

    @Test
    fun `show background is populated from repo`() = runTest {
        every { mockUpNextWidgetRepository.showWeather } returns false
        every { mockUpNextWidgetRepository.deeplinkToEvent } returns false
        every { mockUpNextWidgetRepository.showBackground } returns true
        initUnderTest()
        underTest.uiState.test {
            assertEquals(true, awaitItem().showBackground)
        }
    }

    @Test
    fun `updating values saves values to repo`() = runTest {
        every { mockUpNextWidgetRepository.showWeather } returns false
        every { mockUpNextWidgetRepository.deeplinkToEvent } returns false
        every { mockUpNextWidgetRepository.showBackground } returns false

        initUnderTest()

        underTest.updateShowBackground(true)
        verify {
            mockUpNextWidgetRepository.showBackground = true
        }

        underTest.updateDeeplinkToEvent(true)
        verify {
            mockUpNextWidgetRepository.deeplinkToEvent = true
        }

        underTest.updateDeeplinkToEvent(true)
        verify {
            mockUpNextWidgetRepository.deeplinkToEvent = true
        }
    }
}