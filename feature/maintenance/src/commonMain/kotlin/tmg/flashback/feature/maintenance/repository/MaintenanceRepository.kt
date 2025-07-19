package tmg.flashback.feature.maintenance.repository

import tmg.flashback.configuration.manager.ConfigManager

interface MaintenanceRepository {
    val softUpgrade: Boolean
}

internal class MaintenanceRepositoryImpl(
    private val configManager: ConfigManager
): MaintenanceRepository {

    override val softUpgrade: Boolean
        get() = configManager.getBoolean(keySoftUpgrade)

    companion object {
        private const val keySoftUpgrade: String = "soft_upgrade"
    }
}