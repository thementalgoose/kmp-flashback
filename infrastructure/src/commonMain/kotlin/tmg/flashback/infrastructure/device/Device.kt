package tmg.flashback.infrastructure.device

import tmg.flashback.infrastructure.log.logInfo

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

fun Device.log() {
    logInfo("Device", "applicationId: $applicationId")
    logInfo("Device", "versionCode: $versionCode")
    logInfo("Device", "versionName: $versionName")
    logInfo("Device", "isMonetThemeSupported: $isMonetThemeSupported")
    logInfo("Device", "isRuntimeNotificationsSupported: $isRuntimeNotificationsSupported")
    logInfo("Device", "isEmulator: $isEmulator")
    logInfo("Device", "isDebug: $isDebug")
    logInfo("Device", "platform: $platform")
    logInfo("Device", "osVersion: $osVersion")
    logInfo("Device", "brand: $brand")
    logInfo("Device", "hardware: $hardware")
    logInfo("Device", "board: $board")
    logInfo("Device", "fingerprint: $fingerprint")
    logInfo("Device", "model: $model")
    logInfo("Device", "manufacturer: $manufacturer")
    logInfo("Device", "product: $product")
    logInfo("Device", "device: $device")
}