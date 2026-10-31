package tmg.flashback.feature.maintenance.repository

import tmg.flashback.configuration.manager.ConfigManager
import org.koin.core.annotation.Single

interface MaintenanceRepository {
    val softUpgrade: Boolean
    val forceUpgrade: Boolean
}

@Single
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