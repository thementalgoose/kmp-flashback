package tmg.flashback.configuration.firebase

expect class FirebaseService {

    suspend fun activate(): Boolean
    suspend fun reset()
    suspend fun fetch(minimumFetchInterval: Int? = null)

    fun setConfigSettingsAsync(minimumFetchInterval: Int)
    fun setDefaultsAsync(defaultValues: Map<String, Any>)

    fun getValue(key: String): RemoteConfigValue
}