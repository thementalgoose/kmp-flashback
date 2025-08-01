package tmg.flashback.presentation.settings.notifications.upcoming

import app.cash.turbine.test
import dev.mokkery.MockMode.autoUnit
import dev.mokkery.MockMode.autofill
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify
import dev.mokkery.verifySuspend
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import tmg.flashback.device.usecases.OpenSettingsUseCase
import tmg.flashback.feature.notifications.model.NotificationReminder
import tmg.flashback.feature.notifications.repositories.NotificationSettingsRepository
import tmg.flashback.feature.notifications.usecases.ScheduleResult
import tmg.flashback.feature.notifications.usecases.ScheduleUpcomingNotificationsUseCase
import tmg.flashback.notifications.manager.NotificationManager
import tmg.flashback.ui.permissions.Permission.Notifications
import tmg.flashback.ui.permissions.PermissionManager
import tmg.flashback.ui.permissions.PermissionState.Granted
import tmg.flashback.ui.permissions.PermissionState.NotDetermined
import kotlin.test.Test
import kotlin.test.assertEquals

internal class SettingsNotificationUpcomingViewModelTest {

    private lateinit var underTest: SettingsNotificationUpcomingViewModel

    private val mockScheduleUpcomingNotificationsUseCase: ScheduleUpcomingNotificationsUseCase =
        mock(autoUnit)
    private val mockNotificationSettingsRepository: NotificationSettingsRepository =
        mock(autoUnit)
    private val mockPermissionManager: PermissionManager = mock(autofill)
    private val mockNotificationManager: NotificationManager = mock(autofill)
    private val mockOpenSettingsUseCase: OpenSettingsUseCase = mock(autoUnit)

    private fun initUnderTest() {
        everySuspend { mockScheduleUpcomingNotificationsUseCase.invoke(any()) } returns ScheduleResult.Scheduled
        every { mockNotificationSettingsRepository.notificationUpcomingEnabled } returns emptySet()
        every { mockNotificationSettingsRepository.notificationReminderPeriod } returns NotificationReminder.MINUTES_15
        every { mockNotificationManager.canScheduleExact } returns false
        underTest = SettingsNotificationUpcomingViewModel(
            notificationSettingsRepository = mockNotificationSettingsRepository,
            scheduleUpcomingNotificationsUseCase = mockScheduleUpcomingNotificationsUseCase,
            notificationManager = mockNotificationManager,
            permissionManager = mockPermissionManager,
            openSettingsUseCase = mockOpenSettingsUseCase,
            coroutineContext = Dispatchers.Unconfined
        )
    }

    @Test
    fun `permission state is set when vm is initialised`() = runTest {
        everySuspend { mockPermissionManager.getPermissionState(Notifications) } returns Granted
        initUnderTest()
        underTest.permissionState.test {
            assertEquals(Granted, awaitItem())
        }
    }

    @Test
    fun `request permission prompts permission and updates state to granted if allowed`() = runTest {
        everySuspend { mockPermissionManager.getPermissionState(Notifications) } returns NotDetermined
        everySuspend { mockPermissionManager.requestPermission(Notifications) } returns CompletableDeferred(Granted)

        initUnderTest()

        underTest.permissionState.test {
            assertEquals(NotDetermined, awaitItem())

            everySuspend { mockPermissionManager.getPermissionState(Notifications) } returns Granted

            underTest.requestPermissions()

            assertEquals(Granted, awaitItem())

        }
        verifySuspend {
            mockPermissionManager.requestPermission(Notifications)
        }
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
        everySuspend { mockPermissionManager.getPermissionState(any()) } returns Granted
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
        everySuspend { mockPermissionManager.getPermissionState(any()) } returns Granted
        every { mockOpenSettingsUseCase.openAlarmSettings() } returns Unit
        initUnderTest()
        underTest.goToAlarmSettings()
        verify {
            mockOpenSettingsUseCase.openAlarmSettings()
        }
    }
}