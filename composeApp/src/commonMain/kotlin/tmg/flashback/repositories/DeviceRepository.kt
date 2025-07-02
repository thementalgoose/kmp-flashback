package tmg.flashback.repositories

import tmg.flashback.preferences.manager.PreferenceManager
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface DeviceRepository {
    var deviceUdid: String
    var installationId: String
}

@OptIn(ExperimentalUuidApi::class)
internal class DeviceRepositoryImpl(
    private val preferenceManager: PreferenceManager
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

    companion object {
        private const val keyDeviceUDID: String = "UDID"
        private const val keyInstallationId: String = "INSTALLATION_ID"
    }
}