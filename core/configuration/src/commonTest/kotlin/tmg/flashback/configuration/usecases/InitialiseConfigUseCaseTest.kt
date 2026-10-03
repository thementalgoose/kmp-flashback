package tmg.flashback.configuration.usecases

import dev.mokkery.MockMode.autoUnit
import dev.mokkery.mock
import dev.mokkery.verify
import tmg.flashback.configuration.manager.ConfigManager
import kotlin.test.Test

internal class InitialiseConfigUseCaseTest {

    private val mockConfigManager: ConfigManager = mock(autoUnit)

    private lateinit var underTest: InitialiseConfigUseCaseImpl

    private fun initUnderTest() {
        underTest = InitialiseConfigUseCaseImpl(
            configManager = mockConfigManager
        )
    }

    @Test
    fun `initialise calls remote config service`() {
        val defaultValues = mapOf(
            "myValue" to "true"
        )

        initUnderTest()
        underTest(defaultValues)

        verify {
            mockConfigManager.initialiseRemoteConfig(defaultValues)
        }
    }
}