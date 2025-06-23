package tmg.flashback.configuration.manager

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import tmg.flashback.configuration.firebase.FirebaseRemoteConfigService
import tmg.flashback.configuration.firebase.FirebaseSettings

internal class ConfigManagerImpl(
    private val firebaseRemoteConfigService: FirebaseRemoteConfigService
): ConfigManager {
    override fun getBoolean(key: String): Boolean {
        return firebaseRemoteConfigService.getValue(key).asBoolean()
    }

    override fun getString(key: String): String? {
        return firebaseRemoteConfigService.getValue(key).asString()
    }

    override fun <T> getJson(key: String, serializer: KSerializer<T>): T? {
        val string = firebaseRemoteConfigService.getValue(key).asString() ?: return null
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