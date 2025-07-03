package tmg.flashback.presentation.settings.notifications

import app.cash.turbine.test
import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify
import dev.mokkery.verifySuspend
import kotlinx.coroutines.test.runTest
import tmg.flashback.feature.notifications.model.NotificationReminder
import tmg.flashback.feature.notifications.repositories.NotificationSettingsRepository
import tmg.flashback.feature.notifications.usecases.ScheduleResult
import tmg.flashback.feature.notifications.usecases.ScheduleUpcomingNotificationsUseCase
import tmg.flashback.presentation.settings.notifications.upcoming.SettingsNotificationUpcomingViewModel
import kotlin.test.Test
import kotlin.test.assertEquals

internal class SettingsNotificationUpcomingViewModelTest {

    private lateinit var underTest: SettingsNotificationUpcomingViewModel

    private val mockScheduleUpcomingNotificationsUseCase: ScheduleUpcomingNotificationsUseCase = mock(autoUnit)
    private val mockNotificationSettingsRepository: NotificationSettingsRepository = mock(autoUnit)

    private fun initUnderTest() {
        underTest = SettingsNotificationUpcomingViewModel(
            notificationSettingsRepository = mockNotificationSettingsRepository,
            scheduleUpcomingNotificationsUseCase = mockScheduleUpcomingNotificationsUseCase
        )
    }

    @Test
    fun `settings reminder period reschedules notifications`() {
        every { mockNotificationSettingsRepository.notificationUpcomingEnabled } returns emptySet()
        every { mockNotificationSettingsRepository.notificationReminderPeriod } returns NotificationReminder.MINUTES_30
        initUnderTest()
        underTest.notificationReminderClicked(NotificationReminder.MINUTES_60)

        verify {
            mockNotificationSettingsRepository.notificationReminderPeriod = NotificationReminder.MINUTES_60
        }
        verifySuspend {
            mockScheduleUpcomingNotificationsUseCase(true)
        }
    }

    @Test
    fun `settings reminder period read from repo`() = runTest {
        every { mockNotificationSettingsRepository.notificationUpcomingEnabled } returns emptySet()
        every { mockNotificationSettingsRepository.notificationReminderPeriod } returns NotificationReminder.MINUTES_15
        initUnderTest()
        underTest.uiState.test {
            assertEquals(NotificationReminder.MINUTES_15, awaitItem().reminder)
        }
    }
}