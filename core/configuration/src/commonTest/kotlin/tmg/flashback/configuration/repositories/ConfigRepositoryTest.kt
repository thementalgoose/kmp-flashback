package tmg.flashback.configuration.repositories

import dev.mokkery.MockMode
import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import tmg.flashback.configuration.Migrations
import tmg.flashback.preferences.manager.PreferenceManager
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class ConfigRepositoryTest {

    private val mockPreferenceManager: PreferenceManager = mock(autoUnit)

    private lateinit var underTest: ConfigRepositoryImpl

    private fun initUnderTest() {
        underTest = ConfigRepositoryImpl(
            preferenceManager = mockPreferenceManager
        )
    }

    //region Remote Config Sync count

    @Test
    fun `remote config sync count calls shared prefs repository`() {
        every { mockPreferenceManager.getInt(keyRemoteConfigSync, 0) } returns 3
        initUnderTest()
        assertEquals(underTest.remoteConfigSync, 3)
        verify {
            mockPreferenceManager.getInt(keyRemoteConfigSync, 0)
        }
    }

    @Test
    fun `remote config sync count saves in shared prefs repository`() {
        initUnderTest()
        underTest.remoteConfigSync = 2
        verify {
            mockPreferenceManager.save(keyRemoteConfigSync, 2)
        }
    }

    //endregion

    //region Require sync

    @Test
    fun `require synchronisation reads value from remote config sync`() {

        every { mockPreferenceManager.getInt(keyRemoteConfigSync, 0) } returns Migrations.configurationSyncCount - 1
        initUnderTest()

        assertTrue(underTest.requireSynchronisation)
    }

    @Test
    fun `require synchronisation returns false when migrations match`() {

        every { mockPreferenceManager.getInt(keyRemoteConfigSync, 0) } returns Migrations.configurationSyncCount
        initUnderTest()

        assertFalse(underTest.requireSynchronisation)
    }

    //endregion

    //region Remote Config Sync count

    @Test
    fun `remote config reset to migration version calls shared prefs repository`() {
        every { mockPreferenceManager.getInt(keyRemoteConfigResetCalledAtMigrationVersion, 0) } returns 3
        initUnderTest()
        assertEquals(underTest.resetAtMigrationVersion, 3)
        verify {
            mockPreferenceManager.getInt(keyRemoteConfigResetCalledAtMigrationVersion, 0)
        }
    }

    @Test
    fun `remote config reset to migration version saves in shared prefs repository`() {
        initUnderTest()
        underTest.resetAtMigrationVersion = 2
        verify {
            mockPreferenceManager.save(keyRemoteConfigResetCalledAtMigrationVersion, 2)
        }
    }

    //endregion

    companion object {
        private const val keyRemoteConfigSync: String = "REMOTE_CONFIG_SYNC_COUNT"
        private const val keyRemoteConfigResetCalledAtMigrationVersion: String = "REMOTE_CONFIG_RESET_CALL"
    }
}