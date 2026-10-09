package tmg.flashback.notifications.manager

import kotlin.test.Test
import kotlin.test.assertTrue

internal class NotificationManagerTest {

    private val underTest: NotificationManager = NotificationManagerImpl()

    @Test
    fun `isChannelActive returns expected default for platform`() {
        val isActive = underTest.isChannelActive("test_channel")
        assertTrue(isActive)
    }
}
