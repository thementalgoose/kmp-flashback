package tmg.flashback.presentation.settings.notifications.results

import dev.mokkery.MockMode
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify
import kotlinx.coroutines.test.runTest
import tmg.flashback.device.usecases.OpenSettingsUseCase
import tmg.flashback.feature.notifications.repositories.NotificationSettingsRepository
import tmg.flashback.feature.notifications.usecases.SubscribeResultNotificationsUseCase
import tmg.flashback.ui.permissions.PermissionManager
import tmg.flashback.ui.permissions.PermissionState
import kotlin.test.Test

internal class SettingsNotificationResultsViewModelTest {

    private lateinit var underTest: SettingsNotificationResultsViewModel

    private val mockSubscribeResultNotificationsUseCase: SubscribeResultNotificationsUseCase =
        mock(MockMode.autoUnit)
    private val mockNotificationSettingsRepository: NotificationSettingsRepository =
        mock(MockMode.autoUnit)
    private val mockPermissionManager: PermissionManager = mock(MockMode.autoUnit)
    private val mockOpenSettingsUseCase: OpenSettingsUseCase = mock(MockMode.autoUnit)

    private fun initUnderTest() {
        everySuspend { mockSubscribeResultNotificationsUseCase.invoke() } returns Unit
        everySuspend { mockPermissionManager.getPermissionState(any()) } returns PermissionState.Granted
        every { mockNotificationSettingsRepository.notificationResultsEnabled } returns emptySet()

        underTest = SettingsNotificationResultsViewModel(
            notificationSettingsRepository = mockNotificationSettingsRepository,
            subscribeResultNotificationsUseCase = mockSubscribeResultNotificationsUseCase,
            permissionManager = mockPermissionManager,
            openSettingsUseCase = mockOpenSettingsUseCase
        )
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