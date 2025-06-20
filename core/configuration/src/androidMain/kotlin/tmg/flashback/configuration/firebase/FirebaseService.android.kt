package tmg.flashback.configuration.firebase

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigInfo
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import kotlin.jvm.Throws

actual class FirebaseService {

    private val remoteConfig = FirebaseRemoteConfig.getInstance()

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