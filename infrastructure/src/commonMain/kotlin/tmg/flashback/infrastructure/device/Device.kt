package tmg.flashback.infrastructure.device

expect object Device {
    val applicationId: String
    val versionCode: Int
    val versionName: String
    val isMonetThemeSupported: Boolean
    val isRuntimeNotificationsSupported: Boolean
    val isEmulator: Boolean
    val isDebug: Boolean
    val platform: Platform
    val osVersion: String
    val brand: String
    val hardware: String
    val board: String
    val fingerprint: String
    val model: String
    val manufacturer: String
    val product: String
    val device: String
}

enum class Platform {
    Android,
    IOS,
    Other
}