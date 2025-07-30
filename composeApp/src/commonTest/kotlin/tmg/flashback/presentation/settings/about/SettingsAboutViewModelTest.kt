package tmg.flashback.presentation.settings.about

import dev.mokkery.MockMode.autoUnit
import dev.mokkery.mock
import dev.mokkery.verify
import tmg.flashback.configuration.repositories.ConfigRepository
import tmg.flashback.device.usecases.OpenStorePageUseCase
import kotlin.test.Test

internal class SettingsAboutViewModelTest {

    private lateinit var underTest: SettingsAboutViewModel

    private val mockOpenStorePageUseCase: OpenStorePageUseCase = mock(autoUnit)
    private val mockConfigRepository: ConfigRepository = mock(autoUnit)

    private fun initUnderTest() {
        underTest = SettingsAboutViewModel(
            openStorePageUseCase = mockOpenStorePageUseCase,
            configRepository = mockConfigRepository
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
            mockConfigRepository.remoteConfigSync = 0
        }
    }
}