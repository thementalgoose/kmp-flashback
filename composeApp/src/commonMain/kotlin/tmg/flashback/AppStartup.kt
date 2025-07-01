package tmg.flashback

import tmg.flashback.configuration.manager.ConfigManager

/**
 * App startup class
 *
 * Called on all platforms right at the end of koin initialisation
 */
class AppStartup(
    private val configManager: ConfigManager
) {
    fun start() {
        configManager.initialiseRemoteConfig(RemoteConfigDefaults.defaults)
    }
}