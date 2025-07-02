package tmg.flashback.infrastructure.device

import tmg.flashback.infrastructure.log.logDebug

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

fun Device.logDebug() {
    logDebug("Device", "applicationId: $applicationId")
    logDebug("Device", "versionCode: $versionCode")
    logDebug("Device", "versionName: $versionName")
    logDebug("Device", "isMonetThemeSupported: $isMonetThemeSupported")
    logDebug("Device", "isRuntimeNotificationsSupported: $isRuntimeNotificationsSupported")
    logDebug("Device", "isEmulator: $isEmulator")
    logDebug("Device", "isDebug: $isDebug")
    logDebug("Device", "platform: $platform")
    logDebug("Device", "osVersion: $osVersion")
    logDebug("Device", "brand: $brand")
    logDebug("Device", "hardware: $hardware")
    logDebug("Device", "board: $board")
    logDebug("Device", "fingerprint: $fingerprint")
    logDebug("Device", "model: $model")
    logDebug("Device", "manufacturer: $manufacturer")
    logDebug("Device", "product: $product")
    logDebug("Device", "device: $device")
}