package tmg.flashback.feature.notifications.presentation.results

import app.cash.turbine.test
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
import tmg.flashback.feature.notifications.model.NotificationResultsAvailable
import tmg.flashback.feature.notifications.repositories.NotificationSettingsRepository
import tmg.flashback.feature.notifications.usecases.IsNotificationEnabledUseCase
import tmg.flashback.feature.notifications.usecases.ScheduleUpcomingNotificationsUseCase
import tmg.flashback.feature.notifications.usecases.SubscribeNewsNotificationsUseCase
import tmg.flashback.feature.notifications.usecases.SubscribeResultNotificationsUseCase
import tmg.flashback.ui.permissions.Permission
import tmg.flashback.ui.permissions.PermissionManager
import tmg.flashback.ui.permissions.PermissionState
import tmg.flashback.ui.permissions.PermissionState.Granted
import tmg.flashback.ui.permissions.PermissionState.NotGranted
import kotlin.test.Test
import kotlin.test.assertEquals

internal class NotificationResultViewModelTest {

    private val mockNotificationSettingsRepository: NotificationSettingsRepository = mock(autofill)
    private val mockPermissionManager: PermissionManager = mock(autofill)
    private val mockOpenSettingsUseCase: OpenSettingsUseCase = mock(autofill)
    private val mockScheduleUpcomingNotificationsUseCase: ScheduleUpcomingNotificationsUseCase = mock(autofill)
    private val mockSubscribeResultNotificationsUseCase: SubscribeResultNotificationsUseCase = mock(autofill)
    private val mockSubscribeNewsNotificationsUseCase: SubscribeNewsNotificationsUseCase = mock(autofill)
    private val mockIsNotificationEnabledUseCase: IsNotificationEnabledUseCase = mock(autofill)

    private lateinit var underTest: NotificationResultViewModel

    private fun initUnderTest() {
        underTest = NotificationResultViewModel(
            notificationSettingsRepository = mockNotificationSettingsRepository,
            scheduleUpcomingNotificationsUseCase = mockScheduleUpcomingNotificationsUseCase,
            subscribeResultNotificationsUseCase = mockSubscribeResultNotificationsUseCase,
            subscribeNewsNotificationsUseCase = mockSubscribeNewsNotificationsUseCase,
            isNotificationEnabledUseCase = mockIsNotificationEnabledUseCase,
            permissionManager = mockPermissionManager,
            openSettingsUseCase = mockOpenSettingsUseCase,
            coroutineContext = Dispatchers.Unconfined
        )
    }

    @Test
    fun `forResult sets prompt notification to true when not seen and not enabled`() = runTest {
        every { mockNotificationSettingsRepository.notificationPromptResultAvailableSeen } returns emptySet()
        every { mockIsNotificationEnabledUseCase.invoke(NotificationResultsAvailable.RACE) } returns false

        initUnderTest()

        underTest.promptNotification.test {
            assertEquals(false, awaitItem())

            underTest.forResult(NotificationResultsAvailable.RACE)

            assertEquals(true, awaitItem())
        }
    }

    @Test
    fun `forResult does not prompt notification when result already seen`() = runTest {
        every { mockNotificationSettingsRepository.notificationPromptResultAvailableSeen } returns setOf(NotificationResultsAvailable.RACE)
        every { mockIsNotificationEnabledUseCase.invoke(NotificationResultsAvailable.RACE) } returns false

        initUnderTest()

        underTest.promptNotification.test {
            assertEquals(false, awaitItem())

            underTest.forResult(NotificationResultsAvailable.RACE)

            expectNoEvents()
        }
    }

    @Test
    fun `forResult does not prompt notification when notification is enabled`() = runTest {
        every { mockNotificationSettingsRepository.notificationPromptResultAvailableSeen } returns emptySet()
        every { mockIsNotificationEnabledUseCase.invoke(NotificationResultsAvailable.RACE) } returns true

        initUnderTest()

        underTest.promptNotification.test {
            assertEquals(false, awaitItem())

            underTest.forResult(NotificationResultsAvailable.RACE)

            expectNoEvents()
        }
    }

    @Test
    fun `close updates state to false`() = runTest {
        every { mockNotificationSettingsRepository.notificationPromptResultAvailableSeen } returns emptySet()
        every { mockIsNotificationEnabledUseCase.invoke(NotificationResultsAvailable.RACE) } returns false

        initUnderTest()
        underTest.forResult(NotificationResultsAvailable.RACE)

        underTest.promptNotification.test {
            assertEquals(true, awaitItem())

            underTest.close(NotificationResultsAvailable.RACE)

            assertEquals(false, awaitItem())
        }

        verify {
            mockNotificationSettingsRepository.notificationPromptResultAvailableSeen = setOf(NotificationResultsAvailable.RACE)
        }
    }

    @Test
    fun `prompt notifications requests permissions and opens settings if not granted`() = runTest {
        every { mockNotificationSettingsRepository.notificationPromptResultAvailableSeen } returns emptySet()
        every { mockIsNotificationEnabledUseCase.invoke(NotificationResultsAvailable.RACE) } returns false
        everySuspend { mockPermissionManager.requestPermission(any()) } returns CompletableDeferred(NotGranted)

        initUnderTest()
        underTest.forResult(NotificationResultsAvailable.RACE)

        underTest.promptNotification.test {
            assertEquals(true, awaitItem())

            underTest.promptRuntimeNotifications(NotificationResultsAvailable.RACE)

            assertEquals(false, awaitItem())
        }

        verifySuspend {
            mockPermissionManager.requestPermission(any())
            mockNotificationSettingsRepository.notificationPromptResultAvailableSeen = setOf(NotificationResultsAvailable.RACE)
            mockOpenSettingsUseCase.openNotificationSettings()
        }
    }

    @Test
    fun `prompt notifications requests permissions`() = runTest {
        every { mockNotificationSettingsRepository.notificationPromptResultAvailableSeen } returns emptySet()
        every { mockIsNotificationEnabledUseCase.invoke(NotificationResultsAvailable.RACE) } returns false
        everySuspend { mockPermissionManager.requestPermission(any()) } returns CompletableDeferred(Granted)

        initUnderTest()
        underTest.forResult(NotificationResultsAvailable.RACE)

        underTest.promptNotification.test {
            assertEquals(true, awaitItem())

            underTest.promptRuntimeNotifications(NotificationResultsAvailable.RACE)

            assertEquals(false, awaitItem())
        }

        verifySuspend {
            mockScheduleUpcomingNotificationsUseCase.invoke()
            mockSubscribeResultNotificationsUseCase.invoke()
            mockSubscribeNewsNotificationsUseCase.invoke()
            mockPermissionManager.requestPermission(any())
            mockNotificationSettingsRepository.notificationPromptResultAvailableSeen = setOf(NotificationResultsAvailable.RACE)
            mockOpenSettingsUseCase.openNotificationSettings()
        }
    }
}
