package tmg.flashback.presentation.settings.about

import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.settings_pref_reset_toast
import tmg.flashback.device.repositories.DeviceRepository
import tmg.flashback.device.usecases.OpenEmailUseCase
import tmg.flashback.device.usecases.OpenStorePageUseCase
import tmg.flashback.repositories.OnboardingRepository
import tmg.flashback.ui.toasts.ToastManager
import kotlin.test.Test

internal class SettingsAboutViewModelTest {

    private lateinit var underTest: SettingsAboutViewModel

    private val mockOpenStorePageUseCase: OpenStorePageUseCase = mock(autoUnit)
    private val mockOpenEmailUseCase: OpenEmailUseCase = mock(autoUnit)
    private val mockOnboardingRepository: OnboardingRepository = mock(autoUnit)
    private val mockDeviceRepository: DeviceRepository = mock(autoUnit)
    private val mockToastManager: ToastManager = mock(autoUnit)

    private fun initUnderTest() {
        underTest = SettingsAboutViewModel(
            openStorePageUseCase = mockOpenStorePageUseCase,
            openEmailUseCase = mockOpenEmailUseCase,
            onboardingRepository = mockOnboardingRepository,
            deviceRepository = mockDeviceRepository,
            toastManager = mockToastManager
        )
    }

    @Test
    fun `open store page calls use case`() {
        initUnderTest()

        underTest.openReview()

        verify {
            mockOpenStorePageUseCase.invoke()
        }
    }

    @Test
    fun `first app sync resets counter`() {
        initUnderTest()

        underTest.firstTimeSync()

        verify {
            mockOnboardingRepository.initialSyncCompleted = false
            mockToastManager.showMessage(string.settings_pref_reset_toast)
        }
    }
}