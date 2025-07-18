package tmg.flashback.presentation.settings.about

import dev.mokkery.MockMode.autoUnit
import dev.mokkery.mock
import dev.mokkery.verify
import tmg.flashback.device.usecases.OpenStorePageUseCase
import kotlin.test.Test

internal class SettingsAboutViewModelTest {

    private lateinit var underTest: SettingsAboutViewModel

    private val mockOpenStorePageUseCase: OpenStorePageUseCase = mock(autoUnit)

    private fun initUnderTest() {
        underTest = SettingsAboutViewModel(
            openStorePageUseCase = mockOpenStorePageUseCase
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
}