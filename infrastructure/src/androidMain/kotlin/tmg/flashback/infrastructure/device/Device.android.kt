package tmg.flashback.infrastructure.device

import android.content.Context
import android.os.Build
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import tmg.flashback.infrastructure.BuildConfig

actual object Device: KoinComponent {

    private val applicationContext
        get() = inject<Context>()

    actual val applicationId: String
        get() = applicationContext.value.packageName

    actual val versionCode: Int
        get() = BuildConfig.VERSION_CODE.toIntOrNull() ?: 1

    actual val versionName: String
        get() = BuildConfig.VERSION_NAME

    actual val isMonetThemeSupported: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    actual val isRuntimeNotificationsSupported: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU

    actual val isEmulator: Boolean
        get() = (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || Build.FINGERPRINT.contains("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.HARDWARE.contains("goldfish")
                || Build.HARDWARE.contains("ranchu")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.PRODUCT.contains("sdk_google")
                || Build.PRODUCT.contains("google_sdk")
                || Build.PRODUCT.contains("sdk")
                || Build.PRODUCT.contains("sdk_x86")
                || Build.PRODUCT.contains("vbox86p")
                || Build.PRODUCT.contains("emulator")
                || Build.PRODUCT.contains("simulator")

    actual val isDebug: Boolean
        get() = BuildConfig.DEBUG

    actual val platform: Platform
        get() = Platform.Android

    actual val osVersion: String
        get() = Build.VERSION.SDK_INT.toString()

    actual val brand: String
        get() = Build.BRAND

    actual val hardware: String
        get() = Build.HARDWARE

    actual val board: String
        get() = Build.BOARD

    actual val fingerprint: String
        get() = Build.FINGERPRINT

    actual val model: String
        get() = Build.MODEL

    actual val manufacturer: String
        get() = Build.MANUFACTURER

    actual val product: String
        get() = Build.PRODUCT

    actual val device: String
        get() = Build.DEVICE

    actual fun string(): String = """
        Platform: Android
        OS Version: $osVersion
        Brand: $brand
        Manufacturer: $manufacturer
        Hardware: $hardware
        Board: $board
        Model: $model
        Product: $product
        Device: $device
    """.trimIndent()
}