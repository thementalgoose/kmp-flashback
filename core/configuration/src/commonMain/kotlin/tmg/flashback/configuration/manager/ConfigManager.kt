package tmg.flashback.configuration.manager

interface ConfigManager {
    fun getBoolean(key: String): Boolean
    fun getString(key: String): String?
    fun <T> getJson(key: String): T?

    fun initialiseRemoteConfig(defaultValues: Map<String, Any>)
    suspend fun reset(): Boolean
    suspend fun fetch(andActivate: Boolean): Boolean
    suspend fun activate(): Boolean
}