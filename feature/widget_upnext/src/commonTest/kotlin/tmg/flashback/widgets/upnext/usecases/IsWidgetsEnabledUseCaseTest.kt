package tmg.flashback.widgets.upnext.usecases

import kotlin.test.Test
import kotlin.test.assertFalse

internal class IsWidgetsEnabledUseCaseTest {

    private lateinit var underTest: IsWidgetsEnabledUseCaseImpl

    private fun initUnderTest() {
        underTest = IsWidgetsEnabledUseCaseImpl()
    }

    @Test
    fun `is widgets enabled`() {
        initUnderTest()

        assertFalse(underTest.invoke())
    }
}