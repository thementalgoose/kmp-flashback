package tmg.flashback.widgets.upnext.usecases

import tmg.flashback.infrastructure.device.Device
import tmg.flashback.infrastructure.device.Platform
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

internal class IsWidgetsEnabledUseCaseTest {

    private lateinit var underTest: IsWidgetsEnabledUseCaseImpl

    private fun initUnderTest() {
        underTest = IsWidgetsEnabledUseCaseImpl()
    }

    @Test
    fun `is widgets enabled`() {
        initUnderTest()

        val expected = Device.platform == Platform.Android
        assertEquals(expected, underTest.invoke())
    }
}