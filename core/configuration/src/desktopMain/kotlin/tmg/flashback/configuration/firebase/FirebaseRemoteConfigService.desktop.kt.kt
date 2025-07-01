package tmg.flashback.configuration.firebase

actual class FirebaseRemoteConfigService actual constructor() {

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

    actual fun setConfigSettingsAsync(minimumFetchInterval: Int) {

    }
    actual fun setDefaultsAsync(defaultValues: Map<String, Any>) {

    }

    actual fun getValue(key: String): RemoteConfigValue {
        return RemoteConfigValue(value = "")
    }
}