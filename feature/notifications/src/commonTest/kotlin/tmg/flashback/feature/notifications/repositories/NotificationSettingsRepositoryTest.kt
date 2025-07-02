package tmg.flashback.feature.notifications.repositories

import dev.mokkery.MockMode
import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import tmg.flashback.feature.notifications.model.NotificationReminder
import tmg.flashback.feature.notifications.model.NotificationReminder.*
import tmg.flashback.preferences.manager.PreferenceManager
import kotlin.test.Test
import kotlin.test.assertEquals

internal class NotificationSettingsRepositoryTest {

    private lateinit var underTest: NotificationSettingsRepositoryImpl

    private val mockPreferenceManager: PreferenceManager = mock(autoUnit)

    private fun initUnderTest() {
        underTest = NotificationSettingsRepositoryImpl(
            preferenceManager = mockPreferenceManager
        )
    }

    @Test
    fun `notification reminder period defaults to 30 mins`() {
        every { mockPreferenceManager.getInt(expectedKeyNotificationReminder, 1800) } returns -1

        initUnderTest()

        assertEquals(MINUTES_30, underTest.notificationReminderPeriod)
    }

    @Test
    fun `notification reminder reads from prefs`() {
        every { mockPreferenceManager.getInt(expectedKeyNotificationReminder, 1800) } returns 3600

        initUnderTest()

        assertEquals(MINUTES_60, underTest.notificationReminderPeriod)
    }

    @Test
    fun `notification reminder saves to prefs`() {
        initUnderTest()
        underTest.notificationReminderPeriod = MINUTES_15

        verify {
            mockPreferenceManager.save(expectedKeyNotificationReminder, 900)
        }
    }

    companion object {
        private const val expectedKeyNotificationReminder: String = "UP_NEXT_NOTIFICATION_REMINDER"
    }
}