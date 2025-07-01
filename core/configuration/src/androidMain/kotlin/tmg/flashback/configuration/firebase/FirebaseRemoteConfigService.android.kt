package tmg.flashback.configuration.firebase

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import kotlinx.coroutines.tasks.await

actual class FirebaseRemoteConfigService actual constructor() {

    private val remoteConfig = FirebaseRemoteConfig.getInstance()

    @Throws(Exception::class)
    actual suspend fun activate(): Boolean {
        return remoteConfig.activate().await()
    }

    @Throws(Exception::class)
    actual suspend fun reset() {
        remoteConfig.reset().await()
    }

    @Throws(Exception::class)
    actual suspend fun fetch(minimumFetchInterval: Int?) {
        if (minimumFetchInterval == null) {
            remoteConfig.fetch().await()
        } else {
            remoteConfig.fetch(minimumFetchInterval.toLong()).await()
        }
    }

    actual fun setConfigSettingsAsync(
        minimumFetchInterval: Int
    ) {
        val settings = FirebaseRemoteConfigSettings
            .Builder()
            .setMinimumFetchIntervalInSeconds(minimumFetchInterval.toLong())
            .build()
        remoteConfig.setConfigSettingsAsync(settings)
    }

    actual fun setDefaultsAsync(defaultValues: Map<String, Any>) {
        remoteConfig.setDefaultsAsync(defaultValues)
    }

    @Throws(IllegalArgumentException::class)
    actual fun getValue(key: String): RemoteConfigValue {
        return RemoteConfigValue(remoteConfig.getValue(key))
    }
}