package tmg.flashback.eastereggs.repository

import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import tmg.flashback.configuration.manager.ConfigManager
import kotlin.test.Test
import kotlin.test.assertTrue

internal class EasterEggsRepositoryTest {

    private val mockConfigManager: ConfigManager = mock(autoUnit)

    private lateinit var underTest: EasterEggsRepositoryImpl

    private fun initUnderTest() {
        underTest = EasterEggsRepositoryImpl(
            configManager = mockConfigManager
        )
    }

    @Test
    fun `is snow enabled gets from config`() {
        every { mockConfigManager.getBoolean(keySnow) } returns true

        initUnderTest()
        assertTrue(underTest.isSnowEnabled)

        verify {
            mockConfigManager.getBoolean(keySnow)
        }
    }


    @Test
    fun `is summer enabled gets from config`() {
        every { mockConfigManager.getBoolean(keySummer) } returns true

        initUnderTest()
        assertTrue(underTest.isSummerEnabled)

        verify {
            mockConfigManager.getBoolean(keySummer)
        }
    }

    @Test
    fun `is ukraine enabled gets from config`() {
        every { mockConfigManager.getBoolean(keyUkraine) } returns true

        initUnderTest()
        assertTrue(underTest.isUkraineEnabled)

        verify {
            mockConfigManager.getBoolean(keyUkraine)
        }
    }

    companion object {
        private const val keySnow = "easteregg_snow"
        private const val keySummer = "easteregg_summer"
        private const val keyUkraine = "easteregg_ukraine"
    }
}