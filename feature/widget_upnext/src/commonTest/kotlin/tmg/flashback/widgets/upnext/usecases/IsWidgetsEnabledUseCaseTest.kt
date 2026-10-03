package tmg.flashback.widgets.upnext.usecases

import tmg.flashback.infrastructure.device.Device
import tmg.flashback.infrastructure.device.Platform
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse

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