package tmg.flashback.configuration.usecases

import dev.mokkery.MockMode
import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import tmg.flashback.configuration.Migrations
import tmg.flashback.configuration.repositories.ConfigRepository
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class DoesConfigRequireSyncUseCaseTest {

    private lateinit var underTest: DoesConfigRequireSyncUseCaseImpl

    private val mockConfigRepository: ConfigRepository = mock(autoUnit)

    private fun initUnderTest() {
        underTest = DoesConfigRequireSyncUseCaseImpl(
            configRepository = mockConfigRepository
        )
    }

    @Test
    fun `requires config returns true when sync count doesnt match migrations`() {
        every { mockConfigRepository.requireSynchronisation } returns true
        initUnderTest()
        assertTrue(underTest.invoke())
    }

    @Test
    fun `requires config returns false when sync count matches match migrations`() {
        every { mockConfigRepository.requireSynchronisation } returns false
        initUnderTest()
        assertFalse(underTest.invoke())
    }
}