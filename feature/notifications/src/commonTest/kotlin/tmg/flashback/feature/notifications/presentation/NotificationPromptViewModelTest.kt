package tmg.flashback.feature.notifications.presentation

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
import tmg.flashback.feature.notifications.repositories.NotificationSettingsRepository
import tmg.flashback.ui.permissions.PermissionManager
import tmg.flashback.ui.permissions.PermissionState.Granted
import tmg.flashback.ui.permissions.PermissionState.NotGranted
import kotlin.test.Test
import kotlin.test.assertEquals

internal class NotificationPromptViewModelTest {

    private val mockNotificationSettingsRepository: NotificationSettingsRepository = mock(autofill)
    private val mockPermissionManager: PermissionManager = mock(autofill)
    private val mockOpenSettingsUseCase: OpenSettingsUseCase = mock(autofill)

    private lateinit var underTest: NotificationPromptViewModel

    private fun initUnderTest() {
        underTest = NotificationPromptViewModel(
            notificationSettingsRepository = mockNotificationSettingsRepository,
            permissionManager = mockPermissionManager,
            openSettingsUseCase = mockOpenSettingsUseCase,
            coroutineContext = Dispatchers.Unconfined
        )
    }

    @Test
    fun `close updates state to true`() = runTest {
        every { mockNotificationSettingsRepository.notificationPromptSeen } returns false

        initUnderTest()

        underTest.uiState.test {
            assertEquals(false, awaitItem())

            underTest.close()

            assertEquals(true, awaitItem())
        }

        verify {
            mockNotificationSettingsRepository.notificationPromptSeen = true
        }
    }

    @Test
    fun `prompt notifications requests permissions and opens settings if not granted`() = runTest {
        every { mockNotificationSettingsRepository.notificationPromptSeen } returns false
        everySuspend { mockPermissionManager.requestPermission(any()) } returns CompletableDeferred(NotGranted)

        initUnderTest()

        underTest.uiState.test {
            assertEquals(false, awaitItem())

            underTest.promptRuntimeNotifications()

            assertEquals(true, awaitItem())
        }

        verifySuspend {
            mockPermissionManager.requestPermission(any())
            mockNotificationSettingsRepository.notificationPromptSeen = true
            mockOpenSettingsUseCase.openNotificationSettings()
        }
    }

    @Test
    fun `prompt notifications requests permissions`() = runTest {
        every { mockNotificationSettingsRepository.notificationPromptSeen } returns false
        everySuspend { mockPermissionManager.requestPermission(any()) } returns CompletableDeferred(Granted)

        initUnderTest()


        underTest.uiState.test {
            assertEquals(false, awaitItem())

            underTest.promptRuntimeNotifications()

            assertEquals(true, awaitItem())
        }

        verifySuspend {
            mockPermissionManager.requestPermission(any())
            mockNotificationSettingsRepository.notificationPromptSeen = true
        }
    }
}