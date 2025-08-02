package tmg.flashback.presentation.settings.notifications.results

import app.cash.turbine.test
import dev.mokkery.MockMode.autoUnit
import dev.mokkery.MockMode.autofill
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify
import dev.mokkery.verifySuspend
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import tmg.flashback.device.usecases.OpenSettingsUseCase
import tmg.flashback.feature.notifications.repositories.NotificationSettingsRepository
import tmg.flashback.feature.notifications.usecases.SubscribeResultNotificationsUseCase
import tmg.flashback.ui.permissions.Permission.Notifications
import tmg.flashback.ui.permissions.PermissionManager
import tmg.flashback.ui.permissions.PermissionState.Granted
import tmg.flashback.ui.permissions.PermissionState.NotDetermined
import tmg.flashback.ui.permissions.PermissionState.NotGranted
import kotlin.coroutines.coroutineContext
import kotlin.test.Test
import kotlin.test.assertEquals

internal class SettingsNotificationResultsViewModelTest {

    private lateinit var underTest: SettingsNotificationResultsViewModel

    private val mockSubscribeResultNotificationsUseCase: SubscribeResultNotificationsUseCase =
        mock(autoUnit)
    private val mockNotificationSettingsRepository: NotificationSettingsRepository =
        mock(autoUnit)
    private val mockPermissionManager: PermissionManager = mock(autofill)
    private val mockOpenSettingsUseCase: OpenSettingsUseCase = mock(autoUnit)

    private fun initUnderTest() {
        everySuspend { mockSubscribeResultNotificationsUseCase.invoke() } returns Unit
        every { mockNotificationSettingsRepository.notificationResultsEnabled } returns emptySet()

        underTest = SettingsNotificationResultsViewModel(
            notificationSettingsRepository = mockNotificationSettingsRepository,
            subscribeResultNotificationsUseCase = mockSubscribeResultNotificationsUseCase,
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

            underTest.requestPermissions()
        }
        verifySuspend {
            mockPermissionManager.requestPermission(Notifications)
        }
    }

    @Test
    fun `request permission opens settings if permission not granted`() = runTest {
        everySuspend { mockPermissionManager.getPermissionState(Notifications) } returns NotDetermined
        everySuspend { mockPermissionManager.requestPermission(Notifications) } returns CompletableDeferred(NotGranted)

        initUnderTest()

        underTest.permissionState.test {
            assertEquals(NotDetermined, awaitItem())

            underTest.requestPermissions()
        }

        verifySuspend {
            mockOpenSettingsUseCase.openNotificationSettings()
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
}