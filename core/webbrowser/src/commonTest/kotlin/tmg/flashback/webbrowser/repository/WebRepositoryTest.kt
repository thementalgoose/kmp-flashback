package tmg.flashback.webbrowser.repository

import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import tmg.flashback.preferences.manager.PreferenceManager
import kotlin.test.Test
import kotlin.test.assertTrue

internal class WebBrowserRepositoryTest {

    private val mockPreferenceManager: PreferenceManager = mock(autoUnit)

    private lateinit var underTest: WebRepositoryImpl

    private fun initUnderTest() {
        underTest = WebRepositoryImpl(
            preferenceManager = mockPreferenceManager
        )
    }

    @Test
    fun `open in external reads from preferences`() {
        every { mockPreferenceManager.getBoolean(keyOpenInExternal, true) } returns true
        initUnderTest()
        assertTrue(underTest.openInExternal)
        verify {
            mockPreferenceManager.getBoolean(keyOpenInExternal, true)
        }
    }

    @Test
    fun `open in external writes to preferences`() {
        initUnderTest()
        underTest.openInExternal = true
        verify {
            mockPreferenceManager.save(keyOpenInExternal, true)
        }
    }

    @Test
    fun `enable javascript reads from preferences`() {
        every { mockPreferenceManager.getBoolean(keyEnableJavascript, true) } returns true
        initUnderTest()
        assertTrue(underTest.enableJavascript)
        verify {
            mockPreferenceManager.getBoolean(keyEnableJavascript, true)
        }
    }

    @Test
    fun `enable javascript writes to preferences`() {
        initUnderTest()
        underTest.enableJavascript = true
        verify {
            mockPreferenceManager.save(keyEnableJavascript, true)
        }
    }

    companion object {
        private const val keyOpenInExternal = "WEB_BROWSER_OPEN_IN_EXTERNAL"
        private const val keyEnableJavascript = "WEB_BROWSER_ENABLE_JAVASCRIPT"
    }
}