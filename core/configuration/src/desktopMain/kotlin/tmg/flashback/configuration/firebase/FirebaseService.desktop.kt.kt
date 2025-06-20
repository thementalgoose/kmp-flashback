package tmg.flashback.configuration.firebase

actual class FirebaseService {

    actual fun setConfigSettingsAsync(minimumFetchInterval: Int) {

    }
    actual fun setDefaultsAsync(defaultValues: Map<String, Any>) {

    }

    actual fun getValue(key: String): RemoteConfigValue {
        return RemoteConfigValue(value = "")
    }
}