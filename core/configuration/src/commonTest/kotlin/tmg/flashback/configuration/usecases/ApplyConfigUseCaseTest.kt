package tmg.flashback.configuration.usecases

import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify
import dev.mokkery.verify.VerifyMode
import dev.mokkery.verifySuspend
import kotlinx.coroutines.runBlocking
import tmg.flashback.configuration.Migrations
import tmg.flashback.configuration.manager.ConfigManager
import tmg.flashback.configuration.repositories.ConfigRepository
import kotlin.test.Test

internal class ApplyConfigUseCaseTest {

    private val mockConfigManager: ConfigManager = mock(autoUnit)
    private val mockConfigRepository: ConfigRepository = mock(autoUnit)

    private lateinit var underTest: ApplyConfigUseCaseImpl

    private fun initUnderTest() {
        underTest = ApplyConfigUseCaseImpl(
            configManager = mockConfigManager,
            configRepository = mockConfigRepository
        )
    }

    @Test
    fun `remote config sync updated if activate succeeds`() {
        everySuspend { mockConfigManager.activate() } returns true

        initUnderTest()
        runBlocking {
            underTest()
        }

        verifySuspend {
            mockConfigManager.activate()
        }
        verify {
            mockConfigRepository.remoteConfigSync = Migrations.configurationSyncCount
        }
    }

    @Test
    fun `remote config sync not updated if activate fails`() {
        everySuspend { mockConfigManager.activate() } returns false

        initUnderTest()
        runBlocking {
            underTest()
        }

        verifySuspend{
            mockConfigManager.activate()
        }
        verify(VerifyMode.exactly(0)) {
            mockConfigRepository.remoteConfigSync = Migrations.configurationSyncCount
        }
    }
}