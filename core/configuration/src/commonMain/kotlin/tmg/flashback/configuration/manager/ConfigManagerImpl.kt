package tmg.flashback.configuration.manager

import tmg.flashback.configuration.firebase.FirebaseService
import tmg.flashback.configuration.firebase.FirebaseSettings

internal class ConfigManagerImpl(
    private val firebaseService: FirebaseService
): ConfigManager {
    override fun getBoolean(key: String): Boolean {
        return firebaseService.getValue(key).asBoolean()
    }

    override fun getString(key: String): String? {
        return firebaseService.getValue(key).asString()
    }

    override fun <T> getJson(key: String): T? {
        return null
    }

    override fun initialiseRemoteConfig(defaultValues: Map<String, Any>) {
        firebaseService.setConfigSettingsAsync(
            minimumFetchInterval = FirebaseSettings.minimumFetchInterval
        )
        firebaseService.setDefaultsAsync(
            defaultValues = defaultValues
        )
    }

    override suspend fun activate(): Boolean {
        return true
    }

    override suspend fun reset(): Boolean {
        return true
    }

    override suspend fun fetch(andActivate: Boolean): Boolean {
        return true
    }
}