package tmg.flashback.firebase

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import kotlinx.coroutines.tasks.await
import tmg.flashback.configuration.firebase.FirebaseRemoteConfigService

internal actual class FirebaseRemoteConfigServiceImpl actual constructor(): FirebaseRemoteConfigService {

    private val remoteConfig = FirebaseRemoteConfig.getInstance()

    @Throws(Exception::class)
    actual override suspend fun activate(): Boolean {
        return remoteConfig.activate().await()
    }

    @Throws(Exception::class)
    actual override suspend fun reset() {
        remoteConfig.reset().await()
    }

    @Throws(Exception::class)
    actual override suspend fun fetch(minimumFetchInterval: Int?) {
        if (minimumFetchInterval == null) {
            remoteConfig.fetch().await()
        } else {
            remoteConfig.fetch(minimumFetchInterval.toLong()).await()
        }
    }

    actual override fun setConfigSettingsAsync(
        minimumFetchInterval: Int
    ) {
        val settings = FirebaseRemoteConfigSettings
            .Builder()
            .setMinimumFetchIntervalInSeconds(minimumFetchInterval.toLong())
            .build()
        remoteConfig.setConfigSettingsAsync(settings)
    }

    actual override fun setDefaultsAsync(defaultValues: Map<String, Any>) {
        remoteConfig.setDefaultsAsync(defaultValues)
    }

    @Throws(IllegalArgumentException::class)
    actual override fun getValueString(key: String): String? {
        return remoteConfig.getValue(key).asString().takeIf { it.isNotEmpty() }
    }

    @Throws(IllegalArgumentException::class)
    actual override fun getValueBoolean(key: String): Boolean {
        return remoteConfig.getValue(key).asBoolean()
    }
}