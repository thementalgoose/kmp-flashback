package tmg.flashback.infrastructure.device

actual object Device {
    actual val applicationId: String
        get() = "tmg.flashback"
    actual val versionCode: Int
        get() = 1
    actual val versionName: String
        get() = "1.0.0"
    actual val isMonetThemeSupported: Boolean
        get() = false
    actual val isRuntimeNotificationsSupported: Boolean
        get() = false
    actual val isEmulator: Boolean
        get() = false
    actual val isDebug: Boolean
        get() = false
    actual val platform: Platform
        get() = Platform.Other
    actual val osVersion: String
        get() = "N/A"
    actual val brand: String
        get() = "Desktop"
    actual val hardware: String
        get() = "Unknown"
    actual val board: String
        get() = "Unknown"
    actual val fingerprint: String
        get() = "Unknown"
    actual val model: String
        get() = "Unknown"
    actual val manufacturer: String
        get() = "Unknown"
    actual val product: String
        get() = "Unknown"
    actual val device: String
        get() = "Unknown"

    actual fun string(): String = """
        Platform: Other
        OS Version: $osVersion
        Brand: $brand
        Hardware: $hardware
        Board: $board
        Model: $model
        Manufacturer: $manufacturer
        Product: $product
        Device: $device
    """.trimIndent()
}