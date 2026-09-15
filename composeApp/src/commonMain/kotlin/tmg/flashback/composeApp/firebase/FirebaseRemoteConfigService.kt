package tmg.flashback.composeApp.firebase

import org.koin.core.annotation.Single
import tmg.flashback.configuration.firebase.FirebaseRemoteConfigService

@Single(binds = [FirebaseRemoteConfigService::class])
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