package tmg.flashback.configuration.manager

import kotlinx.serialization.KSerializer

interface ConfigManager {
    fun getBoolean(key: String): Boolean
    fun getString(key: String): String?
    fun <T> getJson(key: String, serializer: KSerializer<T>): T?

    fun initialiseRemoteConfig(defaultValues: Map<String, Any>)
    suspend fun reset(): Boolean
    suspend fun fetch(andActivate: Boolean): Boolean
    suspend fun activate(): Boolean
}