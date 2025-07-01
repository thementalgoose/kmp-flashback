package tmg.flashback.configuration.firebase

interface FirebaseRemoteConfigService {

    @Throws(Exception::class)
    suspend fun activate(): Boolean

    @Throws(Exception::class)
    suspend fun reset()

    @Throws(Exception::class)
    suspend fun fetch(minimumFetchInterval: Int? = null)

    fun setConfigSettingsAsync(minimumFetchInterval: Int)
    fun setDefaultsAsync(defaultValues: Map<String, Any>)

    @Throws(IllegalArgumentException::class)
    fun getValueString(key: String): String?

    @Throws(IllegalArgumentException::class)
    fun getValueBoolean(key: String): Boolean
}