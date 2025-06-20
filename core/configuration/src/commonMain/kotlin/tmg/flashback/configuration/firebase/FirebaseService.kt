package tmg.flashback.configuration.firebase

expect class FirebaseService {
    fun setConfigSettingsAsync(minimumFetchInterval: Int)
    fun setDefaultsAsync(defaultValues: Map<String, Any>)

    fun getValue(key: String): RemoteConfigValue
}