package tmg.flashback.presentation.settings.notifications.upcoming

import app.cash.turbine.test
import dev.mokkery.MockMode
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify
import dev.mokkery.verifySuspend
import kotlinx.coroutines.test.runTest
import tmg.flashback.device.usecases.OpenSettingsUseCase
import tmg.flashback.feature.notifications.model.NotificationReminder
import tmg.flashback.feature.notifications.repositories.NotificationSettingsRepository
import tmg.flashback.feature.notifications.usecases.ScheduleResult
import tmg.flashback.feature.notifications.usecases.ScheduleUpcomingNotificationsUseCase
import tmg.flashback.ui.permissions.PermissionManager
import tmg.flashback.ui.permissions.PermissionState
import kotlin.test.Test
import kotlin.test.assertEquals

internal class SettingsNotificationUpcomingViewModelTest {

    private lateinit var underTest: SettingsNotificationUpcomingViewModel

    private val mockScheduleUpcomingNotificationsUseCase: ScheduleUpcomingNotificationsUseCase =
        mock(MockMode.autoUnit)
    private val mockNotificationSettingsRepository: NotificationSettingsRepository =
        mock(MockMode.autoUnit)
    private val mockPermissionManager: PermissionManager = mock(MockMode.autoUnit)
    private val mockOpenSettingsUseCase: OpenSettingsUseCase = mock(MockMode.autoUnit)

    private fun initUnderTest() {
        everySuspend { mockScheduleUpcomingNotificationsUseCase.invoke(any()) } returns ScheduleResult.Scheduled
        everySuspend { mockPermissionManager.getPermissionState(any()) } returns PermissionState.Granted
        every { mockNotificationSettingsRepository.notificationUpcomingEnabled } returns emptySet()
        every { mockNotificationSettingsRepository.notificationReminderPeriod } returns NotificationReminder.MINUTES_15
        underTest = SettingsNotificationUpcomingViewModel(
            notificationSettingsRepository = mockNotificationSettingsRepository,
            scheduleUpcomingNotificationsUseCase = mockScheduleUpcomingNotificationsUseCase,
            permissionManager = mockPermissionManager,
            openSettingsUseCase = mockOpenSettingsUseCase
        )
    }

    @Test
    fun `settings reminder period reschedules notifications`() {
        initUnderTest()
        underTest.notificationReminderClicked(NotificationReminder.MINUTES_60)

        verify {
            mockNotificationSettingsRepository.notificationReminderPeriod =
                NotificationReminder.MINUTES_60
        }
        verifySuspend {
            mockScheduleUpcomingNotificationsUseCase(true)
        }
    }

    @Test
    fun `settings reminder period read from repo`() = runTest {
        initUnderTest()
        underTest.uiState.test {
            assertEquals(NotificationReminder.MINUTES_15, awaitItem().reminder)
        }
    }

    @Test
    fun `go to settings open settings`() = runTest {
        every { mockOpenSettingsUseCase.openNotificationSettings() } returns Unit
        initUnderTest()
        underTest.goToSettings()
        verify {
            mockOpenSettingsUseCase.openNotificationSettings()
        }
    }

    @Test
    fun `go to alarm settings open alarm settings`() = runTest {
        every { mockOpenSettingsUseCase.openAlarmSettings() } returns Unit
        initUnderTest()
        underTest.goToAlarmSettings()
        verify {
            mockOpenSettingsUseCase.openAlarmSettings()
        }
    }
}