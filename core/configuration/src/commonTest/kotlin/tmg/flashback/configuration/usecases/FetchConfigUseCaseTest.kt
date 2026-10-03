package tmg.flashback.configuration.usecases

import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify
import dev.mokkery.verify.VerifyMode
import kotlinx.coroutines.runBlocking
import tmg.flashback.configuration.Migrations
import tmg.flashback.configuration.manager.ConfigManager
import tmg.flashback.configuration.repositories.ConfigRepository
import kotlin.test.Test

internal class FetchConfigUseCaseTest {

    private val mockConfigService: ConfigManager = mock(autoUnit)
    private val mockConfigRepository: ConfigRepository = mock(autoUnit)

    private lateinit var underTest: FetchConfigUseCaseImpl

    private fun initUnderTest() {
        underTest = FetchConfigUseCaseImpl(
            configManager = mockConfigService,
            configRepository = mockConfigRepository
        )
    }

    @Test
    fun `fetch calls fetch false`() {
        everySuspend { mockConfigService.fetch(false) } returns true

        initUnderTest()
        runBlocking {
            underTest.fetch()
        }

        verify(VerifyMode.exactly(0)) {
            mockConfigRepository.remoteConfigSync = Migrations.configurationSyncCount
        }
    }

    @Test
    fun `fetch calls fetch true and updates remote config sync when fetch succeeds`() {
        everySuspend { mockConfigService.fetch(true) } returns true

        initUnderTest()
        runBlocking {
            underTest.fetchAndApply()
        }

        verify {
            mockConfigRepository.remoteConfigSync = Migrations.configurationSyncCount
        }
    }

    @Test
    fun `fetch calls fetch true and updates remote config sync when fetch fails`() {
        everySuspend { mockConfigService.fetch(true) } returns false

        initUnderTest()
        runBlocking {
            underTest.fetchAndApply()
        }

        verify(VerifyMode.exactly(0)) {
            mockConfigRepository.remoteConfigSync = Migrations.configurationSyncCount
        }
    }
}