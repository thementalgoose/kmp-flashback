package tmg.flashback.composeApp.firebase

import org.koin.core.annotation.Single
import tmg.flashback.configuration.firebase.FirebaseRemoteConfigService

@Single(binds = [FirebaseRemoteConfigService::class])
internal actual class FirebaseRemoteConfigServiceImpl actual constructor() : FirebaseRemoteConfigService {
    actual override suspend fun activate(): Boolean = false
    actual override suspend fun reset() { }
    actual override suspend fun fetch(minimumFetchInterval: Int?) { }
    actual override fun setConfigSettingsAsync(minimumFetchInterval: Int) { }
    actual override fun setDefaultsAsync(defaultValues: Map<String, Any>) { }
    actual override fun getValueString(key: String): String? = null
    actual override fun getValueBoolean(key: String): Boolean = false
}
