package tmg.flashback.configuration.usecases

import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify
import dev.mokkery.verify.VerifyMode
import dev.mokkery.verifySuspend
import kotlinx.coroutines.runBlocking
import tmg.flashback.configuration.Migrations
import tmg.flashback.configuration.manager.ConfigManager
import tmg.flashback.configuration.repositories.ConfigRepository
import kotlin.test.Test

internal class ResetConfigUseCaseTest {

    private val mockConfigManager: ConfigManager = mock(autoUnit)
    private val mockConfigRepository: ConfigRepository = mock(autoUnit)

    private lateinit var underTest: ResetConfigUseCaseImpl

    private fun initUnderTest() {
        underTest = ResetConfigUseCaseImpl(
            configManager = mockConfigManager,
            configRepository = mockConfigRepository
        )
    }

    @Test
    fun `reset calls config service reset`() {
        everySuspend { mockConfigManager.reset() } returns true
        initUnderTest()
        runBlocking {
            underTest.reset()
        }

        verify {
            mockConfigRepository.remoteConfigSync = 0
        }
        verifySuspend {
            mockConfigManager.reset()
        }
    }

    @Test
    fun `ensure reset calls reset if existing does match sync count`() {
        every { mockConfigRepository.resetAtMigrationVersion } returns Migrations.configurationSyncCount

        initUnderTest()
        runBlocking {
            underTest.ensureReset()
        }

        verify(VerifyMode.exactly(0)) {
            mockConfigRepository.remoteConfigSync = any<Int>()
        }
        verifySuspend(VerifyMode.exactly(0)) {
            mockConfigManager.reset()
        }
    }

    @Test
    fun `ensure reset doesnt calls reset if sync catch count`() {
        everySuspend { mockConfigManager.reset() } returns true
        every { mockConfigRepository.resetAtMigrationVersion } returns (Migrations.configurationSyncCount - 1)

        initUnderTest()
        runBlocking {
            underTest.ensureReset()
        }

        verify {
            mockConfigRepository.resetAtMigrationVersion = Migrations.configurationSyncCount
        }
        verifySuspend {
            mockConfigManager.reset()
        }
    }
}