package tmg.flashback.configuration.repositories

import tmg.flashback.configuration.Migrations
import tmg.flashback.preferences.manager.PreferenceManager

interface ConfigRepository {
    val requireSynchronisation: Boolean
    var remoteConfigSync: Int
    var resetAtMigrationVersion: Int
}

class ConfigRepositoryImpl(
    private val preferenceManager: PreferenceManager
): ConfigRepository {

    companion object {
        private const val keyRemoteConfigSync: String = "REMOTE_CONFIG_SYNC_COUNT"
        private const val keyRemoteConfigResetCalledAtMigrationVersion: String = "REMOTE_CONFIG_RESET_CALL"
    }

    //region Shared Prefs

    override val requireSynchronisation: Boolean
        get() = Migrations.configurationSyncCount != remoteConfigSync

    override var remoteConfigSync: Int
        get() = preferenceManager.getInt(keyRemoteConfigSync, 0)
        set(value) = preferenceManager.save(keyRemoteConfigSync, value)

    override var resetAtMigrationVersion: Int
        get() = preferenceManager.getInt(keyRemoteConfigResetCalledAtMigrationVersion, 0)
        set(value) = preferenceManager.save(keyRemoteConfigResetCalledAtMigrationVersion, value)

    //endregion

}