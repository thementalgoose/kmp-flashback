package tmg.flashback.feature.maintenance.repository

import tmg.flashback.configuration.manager.ConfigManager

interface MaintenanceRepository {
    val softUpgrade: Boolean
    val forceUpgrade: Boolean
}

internal class MaintenanceRepositoryImpl(
    private val configManager: ConfigManager
): MaintenanceRepository {

    override val softUpgrade: Boolean
        get() = configManager.getBoolean(keySoftUpgrade)

    override val forceUpgrade: Boolean
        get() = configManager.getBoolean(keyForceUpgrade)

    companion object {
        private const val keySoftUpgrade: String = "soft_upgrade"
        private const val keyForceUpgrade: String = "force_upgrade"
    }
}