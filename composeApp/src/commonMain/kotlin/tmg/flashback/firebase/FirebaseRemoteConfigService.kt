package tmg.flashback.firebase

import tmg.flashback.configuration.firebase.FirebaseRemoteConfigService

internal expect class FirebaseRemoteConfigServiceImpl(): FirebaseRemoteConfigService {

    @Throws(Exception::class)
    override suspend fun activate(): Boolean

    @Throws(Exception::class)
    override suspend fun reset()

    @Throws(Exception::class)
    override suspend fun fetch(minimumFetchInterval: Int?)

    override fun setConfigSettingsAsync(minimumFetchInterval: Int)
    override fun setDefaultsAsync(defaultValues: Map<String, Any>)

    @Throws(IllegalArgumentException::class)
    override fun getValueString(key: String): String?

    @Throws(IllegalArgumentException::class)
    override fun getValueBoolean(key: String): Boolean
}