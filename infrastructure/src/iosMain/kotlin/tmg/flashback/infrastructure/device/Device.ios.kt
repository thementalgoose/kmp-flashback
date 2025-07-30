package tmg.flashback.infrastructure.device

import platform.Foundation.NSBundle
import platform.Foundation.version
import platform.UIKit.UIDevice

const val IOS_UNKNOWN = "ios(unknown)"

actual object Device {

    actual val applicationId: String
        get() = NSBundle.mainBundle().bundleIdentifier ?: IOS_UNKNOWN

    actual val versionCode: Int
        get() = NSBundle.version().toInt()

    actual val versionName: String
        get() = (NSBundle.mainBundle().infoDictionary
            ?.getOrElse("CFBundleShortVersionString") { IOS_UNKNOWN } as? String)
            ?: IOS_UNKNOWN

    actual val isMonetThemeSupported: Boolean
        get() = false

    actual val isRuntimeNotificationsSupported: Boolean
        get() = true
    actual val isEmulator: Boolean
        get() = false
    actual val isDebug: Boolean
        get() = false

    actual val platform: Platform
        get() = Platform.IOS
    actual val osVersion: String
        get() = UIDevice.currentDevice.systemVersion()
    actual val brand: String
        get() = "Apple"
    actual val hardware: String
        get() = UIDevice.currentDevice.model()
    actual val board: String
        get() = "Unknown"
    actual val fingerprint: String
        get() = "Unknown"
    actual val model: String
        get() = UIDevice.currentDevice.systemName
    actual val manufacturer: String
        get() = "Apple"
    actual val product: String
        get() = UIDevice.currentDevice.model()
    actual val device: String
        get() = UIDevice.currentDevice.model()

    actual fun string(): String = """
        Platform: iOS
        OS Version: $osVersion
        Brand: $brand
        Manufacturer: $manufacturer
        Hardware: $hardware
        Model: $model
        Product: $product
        Device: $device
    """.trimIndent()
}