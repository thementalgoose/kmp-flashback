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
        try {
            val result = firebaseService.activate()
            return result
        } catch (e: Exception) {
            e.printStackTrace()
//            logInfo(e)
            return false
        }
    }

    override suspend fun reset(): Boolean {
        firebaseService.reset()
        return true
    }

    override suspend fun fetch(andActivate: Boolean): Boolean {
        try {
            when (andActivate) {
                true -> {
                    firebaseService.fetch(0)
                    val activate = firebaseService.activate()
                    return activate
                }
                false -> {
                    firebaseService.fetch()
                    return false
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
//            logException(e)
            return false
        }
    }
}