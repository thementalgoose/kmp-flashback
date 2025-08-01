package tmg.flashback.feature.notifications.repositories

import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import tmg.flashback.feature.notifications.model.NotificationReminder.*
import tmg.flashback.feature.notifications.model.NotificationResultsAvailable
import tmg.flashback.feature.notifications.model.NotificationResultsAvailable.SPRINT
import tmg.flashback.feature.notifications.model.NotificationResultsAvailable.SPRINT_QUALIFYING
import tmg.flashback.feature.notifications.model.NotificationUpcoming
import tmg.flashback.feature.notifications.model.NotificationUpcoming.QUALIFYING
import tmg.flashback.feature.notifications.model.NotificationUpcoming.RACE
import tmg.flashback.feature.notifications.repositories.NotificationSettingsRepositoryImpl.Companion.saveKey
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
        every { mockPreferenceManager.getInt(expectedKeyNotificationReminder, 3600) } returns -1

        initUnderTest()

        assertEquals(MINUTES_30, underTest.notificationReminderPeriod)
    }

    @Test
    fun `notification reminder reads from prefs`() {
        every { mockPreferenceManager.getInt(expectedKeyNotificationReminder, 3600) } returns 3600

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

    @Test
    fun `notification upcoming defaults to all options`() {
        val all = NotificationUpcoming.entries.map { it.saveKey }.toSet()
        every { mockPreferenceManager.getSet(expectedKeyNotificationUpcoming, all) } returns all.toMutableSet()

        initUnderTest()
        assertEquals(NotificationUpcoming.entries.toSet(), underTest.notificationUpcomingEnabled)
    }

    @Test
    fun `notification upcoming setting option saves to prefs`() {
        initUnderTest()

        underTest.notificationUpcomingEnabled = setOf(QUALIFYING, RACE)
        verify {
            mockPreferenceManager.save(expectedKeyNotificationUpcoming, setOf(QUALIFYING.saveKey, RACE.saveKey))
        }
    }

    @Test
    fun `notification results defaults to all options`() {
        val all = NotificationResultsAvailable.entries.map { it.saveKey }.toSet()
        every { mockPreferenceManager.getSet(expectedKeyNotificationResult, all) } returns all.toMutableSet()

        initUnderTest()
        assertEquals(NotificationResultsAvailable.entries.toSet(), underTest.notificationResultsEnabled)

    }

    @Test
    fun `notification result setting option saves to prefs`() {
        initUnderTest()

        underTest.notificationResultsEnabled = setOf(SPRINT_QUALIFYING, SPRINT)
        verify {
            mockPreferenceManager.save(expectedKeyNotificationResult, setOf(SPRINT_QUALIFYING.saveKey, SPRINT.saveKey))
        }
    }

    @Test
    fun `notification prompt returns false`() {
        every { mockPreferenceManager.getBoolean(expectedKeyPromptNotifications, false) } returns true

        initUnderTest()
        assertEquals(true, underTest.notificationPromptSeen)
    }

    @Test
    fun `notification prompt saves value`() {
        initUnderTest()
        underTest.notificationPromptSeen = true
        verify {
            mockPreferenceManager.save(expectedKeyPromptNotifications, true)
        }
    }

    companion object {

        private const val expectedKeyPromptNotifications: String = "RUNTIME_NOTIFICATION_PROMPT"
        private const val expectedKeyNotificationUpcoming: String = "NOTIFICATION_UPCOMING_CATEGORIES"
        private const val expectedKeyNotificationResult: String = "NOTIFICATION_RESULT_AVAILABLE_CATEGORIES"

        private const val expectedKeyNotificationReminder: String = "UP_NEXT_NOTIFICATION_REMINDER"
    }
}