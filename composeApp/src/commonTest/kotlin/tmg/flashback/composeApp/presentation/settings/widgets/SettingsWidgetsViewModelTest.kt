package tmg.flashback.composeApp.presentation.settings.widgets

import app.cash.turbine.test
import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import kotlinx.coroutines.test.runTest
import tmg.flashback.widgets.upnext.repositories.UpNextWidgetRepository
import tmg.flashback.widgets.upnext.usecases.AddWidgetUseCase
import kotlin.test.Test
import kotlin.test.assertEquals

internal class SettingsWeatherViewModelTest {

    private lateinit var underTest: SettingsWidgetsViewModel

    private val mockUpNextWidgetRepository: UpNextWidgetRepository = mock(autoUnit)
    private val mockAddWidgetUseCase: AddWidgetUseCase = mock(autoUnit)

    private fun initUnderTest() {
        underTest = SettingsWidgetsViewModel(
            widgetsRepository = mockUpNextWidgetRepository,
            addWidgetUseCase = mockAddWidgetUseCase
        )
    }

    @Test
    fun `is add widget supported is populated from use case`() = runTest {
        every { mockUpNextWidgetRepository.showBackground } returns false
        every { mockUpNextWidgetRepository.showWeather } returns false
        every { mockUpNextWidgetRepository.deeplinkToEvent } returns false
        every { mockAddWidgetUseCase.isSupported } returns true
        initUnderTest()
        underTest.uiState.test {
            assertEquals(true, awaitItem().isAddWidgetSupported)
        }
    }

    @Test
    fun `add widget invokes add widget use case`() = runTest {
        every { mockUpNextWidgetRepository.showBackground } returns false
        every { mockUpNextWidgetRepository.showWeather } returns false
        every { mockUpNextWidgetRepository.deeplinkToEvent } returns false
        every { mockAddWidgetUseCase.isSupported } returns true
        every { mockAddWidgetUseCase() } returns true
        initUnderTest()

        underTest.addWidget()

        verify {
            mockAddWidgetUseCase()
        }
    }

    @Test
    fun `link to event is populated from repo`() = runTest {
        every { mockAddWidgetUseCase.isSupported } returns false
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
        every { mockAddWidgetUseCase.isSupported } returns false
        every { mockUpNextWidgetRepository.showBackground } returns false
        every { mockUpNextWidgetRepository.showWeather } returns true
        every { mockUpNextWidgetRepository.deeplinkToEvent } returns false
        initUnderTest()
        underTest.uiState.test {
            assertEquals(true, awaitItem().showWeather)
        }
    }

    @Test
    fun `show background is populated from repo`() = runTest {
        every { mockAddWidgetUseCase.isSupported } returns false
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
        every { mockAddWidgetUseCase.isSupported } returns false
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