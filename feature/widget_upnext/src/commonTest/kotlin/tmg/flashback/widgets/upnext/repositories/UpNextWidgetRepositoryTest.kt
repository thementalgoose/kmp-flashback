package tmg.flashback.widgets.upnext.repositories

import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import tmg.flashback.preferences.manager.PreferenceManager
import kotlin.test.Test
import kotlin.test.assertTrue

internal class WidgetRepositoryTest {

    private val mockPreferenceManager: PreferenceManager = mock(autoUnit)

    private lateinit var underTest: UpNextWidgetRepositoryImpl

    private fun initUnderTest() {
        underTest = UpNextWidgetRepositoryImpl(
            preferenceManager = mockPreferenceManager
        )
    }

    @Test
    fun `up next - saving show background`() {
        initUnderTest()
        underTest.showBackground = true

        verify {
            mockPreferenceManager.save(keyWidgetShowBackground, true)
        }
    }

    @Test
    fun `up next - get show background`() {
        every { mockPreferenceManager.getBoolean(keyWidgetShowBackground, true) } returns true

        initUnderTest()
        assertTrue(underTest.showBackground)

        verify {
            mockPreferenceManager.getBoolean(keyWidgetShowBackground, true)
        }
    }

    @Test
    fun `up next - saving deeplink to event`() {
        initUnderTest()
        underTest.deeplinkToEvent = true

        verify {
            mockPreferenceManager.save(keyWidgetDeeplinkToEvent, true)
        }
    }

    @Test
    fun `up next - get deeplink to event`() {
        every { mockPreferenceManager.getBoolean(keyWidgetDeeplinkToEvent, false) } returns true

        initUnderTest()
        assertTrue(underTest.deeplinkToEvent)

        verify {
            mockPreferenceManager.getBoolean(keyWidgetDeeplinkToEvent, false)
        }
    }

    @Test
    fun `up next - saving show weather`() {
        initUnderTest()
        underTest.showWeather = true

        verify {
            mockPreferenceManager.save(keyWidgetShowWeather, true)
        }
    }

    @Test
    fun `up next - get show weather`() {
        every { mockPreferenceManager.getBoolean(keyWidgetShowWeather, false) } returns true

        initUnderTest()
        assertTrue(underTest.showWeather)

        verify {
            mockPreferenceManager.getBoolean(keyWidgetShowWeather, false)
        }
    }

    companion object {
        private const val keyWidgetShowBackground: String = "WIDGET_SHOW_BACKGROUND"
        private const val keyWidgetDeeplinkToEvent: String = "WIDGET_DEEPLINK_EVENT"
        private const val keyWidgetShowWeather: String = "WIDGET_SHOW_WEATHER"
    }
}