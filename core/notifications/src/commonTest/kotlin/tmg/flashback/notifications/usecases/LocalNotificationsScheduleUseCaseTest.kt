package tmg.flashback.notifications.usecases

import dev.mokkery.MockMode.autoUnit
import dev.mokkery.mock
import dev.mokkery.verify
import kotlinx.datetime.LocalDateTime
import tmg.flashback.notifications.manager.NotificationManager
import kotlin.test.Test

internal class LocalNotificationsScheduleUseCaseTest {

    private val mockNotificationManager: NotificationManager = mock(autoUnit)

    private lateinit var underTest: LocalNotificationsScheduleUseCaseImpl

    private fun initUnderTest() {
        underTest = LocalNotificationsScheduleUseCaseImpl(
            notificationManager = mockNotificationManager
        )
    }

    @Test
    fun `invoke schedules notification via notification manager`() {
        val uuid = 123
        val channelId = "test-channel"
        val title = "Test Title"
        val text = "Test notification text"
        val timestamp = LocalDateTime(2024, 1, 15, 10, 30)

        initUnderTest()
        underTest(
            uuid = uuid,
            channelId = channelId,
            title = title,
            text = text,
            timestamp = timestamp
        )

        verify {
            mockNotificationManager.schedule(
                uuid = uuid,
                channelId = channelId,
                title = title,
                text = text,
                timestamp = timestamp
            )
        }
    }

    @Test
    fun `invoke forwards all parameters to notification manager`() {
        val uuid = 456
        val channelId = "channel-id"
        val title = "Notification Title"
        val text = "Notification body text"
        val timestamp = LocalDateTime(2024, 6, 20, 14, 0)

        initUnderTest()
        underTest(
            uuid = uuid,
            channelId = channelId,
            title = title,
            text = text,
            timestamp = timestamp
        )

        verify {
            mockNotificationManager.schedule(
                uuid = 456,
                channelId = "channel-id",
                title = "Notification Title",
                text = "Notification body text",
                timestamp = LocalDateTime(2024, 6, 20, 14, 0)
            )
        }
    }

    @Test
    fun `invoke handles empty strings`() {
        val uuid = 0
        val channelId = ""
        val title = ""
        val text = ""
        val timestamp = LocalDateTime(2024, 1, 1, 0, 0)

        initUnderTest()
        underTest(
            uuid = uuid,
            channelId = channelId,
            title = title,
            text = text,
            timestamp = timestamp
        )

        verify {
            mockNotificationManager.schedule(
                uuid = 0,
                channelId = "",
                title = "",
                text = "",
                timestamp = LocalDateTime(2024, 1, 1, 0, 0)
            )
        }
    }

    @Test
    fun `invoke handles special characters in text`() {
        val uuid = 789
        val channelId = "test-channel"
        val title = "Title with \uD83D\uDE00 emoji"
        val text = "Text with special chars: <>&\""
        val timestamp = LocalDateTime(2024, 12, 31, 23, 59)

        initUnderTest()
        underTest(
            uuid = uuid,
            channelId = channelId,
            title = title,
            text = text,
            timestamp = timestamp
        )

        verify {
            mockNotificationManager.schedule(
                uuid = uuid,
                channelId = channelId,
                title = title,
                text = text,
                timestamp = timestamp
            )
        }
    }
}
