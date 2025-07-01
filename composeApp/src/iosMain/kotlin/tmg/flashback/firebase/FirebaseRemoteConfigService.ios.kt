package tmg.flashback.firebase

import cocoapods.FirebaseRemoteConfig.FIRRemoteConfig
import cocoapods.FirebaseRemoteConfig.FIRRemoteConfigSettings
import kotlinx.cinterop.ExperimentalForeignApi
import tmg.flashback.configuration.firebase.FirebaseRemoteConfigService
import tmg.flashback.infrastructure.log.logInfo
import kotlin.coroutines.suspendCoroutine

@OptIn(ExperimentalForeignApi::class)
internal actual class FirebaseRemoteConfigServiceImpl actual constructor(): FirebaseRemoteConfigService {

    private val remoteConfig: FIRRemoteConfig by lazy {
        FIRRemoteConfig.remoteConfig()
    }

    @Throws(Exception::class)
    actual override suspend fun activate(): Boolean = suspendCoroutine { continuation ->
        remoteConfig.activateWithCompletion(completion = { successful, error ->
            if (error == null) {
                continuation.resumeWith(Result.success(successful))
            } else {
                continuation.resumeWith(Result.failure(Exception(error.description ?: "Remote config didn't activate successfully")))
            }
        })
    }

    @Throws(Exception::class)
    actual override suspend fun reset() {
        // No ability to reset
    }

    @Throws(Exception::class)
    actual override suspend fun fetch(minimumFetchInterval: Int?) {
        suspendCoroutine { continuation ->
            if (minimumFetchInterval == null) {
                remoteConfig.fetchWithCompletionHandler { status, error ->
                    logInfo("RemoteConfig", "Fetch $status")
                    continuation.resumeWith(Result.success(Unit))
                }
            } else {
                remoteConfig.fetchWithExpirationDuration(minimumFetchInterval.toDouble()) { status, error ->
                    logInfo("RemoteConfig", "Fetch $status")
                    continuation.resumeWith(Result.success(Unit))
                }
            }
        }
    }

    actual override fun setConfigSettingsAsync(
        minimumFetchInterval: Int
    ) {
        val settings = FIRRemoteConfigSettings.new()
        settings?.setMinimumFetchInterval(minimumFetchInterval.toDouble())
        if (settings != null) {
            remoteConfig.setConfigSettings(settings)
        }
    }

    actual override fun setDefaultsAsync(defaultValues: Map<String, Any>) {
        remoteConfig.setDefaults(defaultValues.toMap<Any?, Any>())
    }

    @Throws(IllegalArgumentException::class)
    actual override fun getValueString(key: String): String? {
        logInfo("RemoteConfig", "Getting value for key $key")
        return remoteConfig.configValueForKey(key).stringValue.takeIf { it.isNotEmpty() }
    }

    @Throws(IllegalArgumentException::class)
    actual override fun getValueBoolean(key: String): Boolean {
        logInfo("RemoteConfig", "Getting value for key $key")
        return remoteConfig.configValueForKey(key).boolValue
    }
}