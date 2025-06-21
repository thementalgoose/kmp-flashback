package tmg.flashback.configuration.firebase

internal actual class FirebaseRemoteConfigService {

//    val remoteConfig = RemoteConfig.remoteConfig

    @Throws(Exception::class)
    actual suspend fun activate(): Boolean {
        return true
    }

    @Throws(Exception::class)
    actual suspend fun reset() {

    }

    @Throws(Exception::class)
    actual suspend fun fetch(minimumFetchInterval: Int?) {

    }

    actual fun setConfigSettingsAsync(
        minimumFetchInterval: Int
    ) {
//        val settings = RemoteConfigSettings()
//        settings.minimumFetchInterval = 0
//        remoteConfig.configSettings = settings
    }

    actual fun setDefaultsAsync(defaultValues: Map<String, Any>) {

    }

    @Throws(IllegalArgumentException::class)
    actual fun getValue(key: String): RemoteConfigValue {
        throw IllegalArgumentException()
//        return RemoteConfigValue
//        return RemoteConfigValue(value = ConfigValue)
    }
}