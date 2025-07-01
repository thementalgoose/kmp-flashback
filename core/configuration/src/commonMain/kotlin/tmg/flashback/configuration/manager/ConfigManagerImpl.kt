package tmg.flashback.configuration.manager

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import tmg.flashback.configuration.firebase.FirebaseRemoteConfigService
import tmg.flashback.configuration.firebase.FirebaseSettings
import tmg.flashback.infrastructure.log.logDebug

internal class ConfigManagerImpl(
    private val firebaseRemoteConfigService: FirebaseRemoteConfigService
): ConfigManager {
    override fun getBoolean(key: String): Boolean {
        try {
            return firebaseRemoteConfigService.getValueBoolean(key)
        } catch (e: IllegalArgumentException) {
            logDebug("Failed to get boolean behind key $key")
            e.printStackTrace()
            return false
        }
    }

    override fun getString(key: String): String? {
        try {
            return firebaseRemoteConfigService.getValueString(key)
        } catch (e: IllegalArgumentException) {
            logDebug("Failed to get string key $key")
            e.printStackTrace()
            return null
        }
    }

    override fun <T> getJson(key: String, serializer: KSerializer<T>): T? {
        val string = getString(key) ?: return null
        if (string.isEmpty() == true) return null
        if (string == "false") return null
        if (string == "true") return null
        return try {
            Json.decodeFromString(serializer, string)
        } catch (e: SerializationException) {
            // TODO: Log this!
            null
        }
    }

    override fun initialiseRemoteConfig(defaultValues: Map<String, Any>) {
        firebaseRemoteConfigService.setConfigSettingsAsync(
            minimumFetchInterval = FirebaseSettings.minimumFetchInterval
        )
        firebaseRemoteConfigService.setDefaultsAsync(
            defaultValues = defaultValues
        )
    }

    override suspend fun activate(): Boolean {
        try {
            val result = firebaseRemoteConfigService.activate()
            return result
        } catch (e: Exception) {
            e.printStackTrace()
//            logInfo(e)
            return false
        }
    }

    override suspend fun reset(): Boolean {
        firebaseRemoteConfigService.reset()
        return true
    }

    override suspend fun fetch(andActivate: Boolean): Boolean {
        try {
            when (andActivate) {
                true -> {
                    firebaseRemoteConfigService.fetch(0)
                    val activate = firebaseRemoteConfigService.activate()
                    return activate
                }
                false -> {
                    firebaseRemoteConfigService.fetch()
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