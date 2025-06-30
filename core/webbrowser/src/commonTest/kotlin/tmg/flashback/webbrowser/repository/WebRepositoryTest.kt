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
    fun `enabled reads from preferences`() {
        every { mockPreferenceManager.getBoolean(expectedKeyEnabled, false) } returns true
        initUnderTest()
        assertTrue(underTest.enabled)
        verify {
            mockPreferenceManager.getBoolean(expectedKeyEnabled, false)
        }
    }

    @Test
    fun `enabled writes to preferences`() {
        initUnderTest()
        underTest.enabled = true
        verify {
            mockPreferenceManager.save(expectedKeyEnabled, true)
        }
    }

    @Test
    fun `enable javascript reads from preferences`() {
        every { mockPreferenceManager.getBoolean(expectedKeyEnableJavascript, true) } returns true
        initUnderTest()
        assertTrue(underTest.enableJavascript)
        verify {
            mockPreferenceManager.getBoolean(expectedKeyEnableJavascript, true)
        }
    }

    @Test
    fun `enable javascript writes to preferences`() {
        initUnderTest()
        underTest.enableJavascript = true
        verify {
            mockPreferenceManager.save(expectedKeyEnableJavascript, true)
        }
    }

    companion object {
        private const val expectedKeyEnabled = "WEB_BROWSER_ENABLED"
        private const val expectedKeyEnableJavascript = "WEB_BROWSER_ENABLE_JAVASCRIPT"
    }
}