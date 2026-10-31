package tmg.flashback.composeApp.firebase

import org.koin.core.annotation.Single
import tmg.flashback.configuration.firebase.FirebaseRemoteConfigService

@Single
internal actual class FirebaseRemoteConfigServiceImpl actual constructor(): FirebaseRemoteConfigService {

    @Throws(Exception::class)
    actual override suspend fun activate(): Boolean {
        return true
    }

    @Throws(Exception::class)
    actual override suspend fun reset() {

    }

    @Throws(Exception::class)
    actual override suspend fun fetch(minimumFetchInterval: Int?) {

    }

    actual override fun setConfigSettingsAsync(minimumFetchInterval: Int) {

    }

    actual override fun setDefaultsAsync(defaultValues: Map<String, Any>) {

    }

    actual override fun getValueString(key: String): String? {
        return null
    }

    actual override fun getValueBoolean(key: String): Boolean {
        return false
    }
}