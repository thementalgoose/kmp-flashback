package tmg.flashback.device.repositories

import tmg.flashback.configuration.manager.ConfigManager
import tmg.flashback.preferences.manager.PreferenceManager
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface DeviceRepository {
    var deviceUdid: String
    var installationId: String
    val contactEmail: String
}

@OptIn(ExperimentalUuidApi::class)
internal class DeviceRepositoryImpl(
    private val preferenceManager: PreferenceManager,
    private val configManager: ConfigManager
): DeviceRepository {

    /**
     * Randomly generated device UDID to uniquely identify app install session
     */
    override var deviceUdid: String
        set(value) = preferenceManager.save(keyDeviceUDID, value)
        get() {
            var key = preferenceManager.getString(keyDeviceUDID, "")
            if (key.isNullOrEmpty()) {
                val newKey = Uuid.random().toHexString()
                deviceUdid = newKey
                key = newKey
            }
            return key
        }

    override var installationId: String
        set(value) {
            preferenceManager.save(keyInstallationId, value)
        }
        get() = preferenceManager.getString(keyInstallationId, "") ?: ""

    override val contactEmail: String
        get() = configManager.getString(keyContactEmail) ?: "thementalgoose@gmail.com"

    companion object {

        private const val keyContactEmail: String = "email"

        private const val keyDeviceUDID: String = "UDID"
        private const val keyInstallationId: String = "INSTALLATION_ID"
    }
}