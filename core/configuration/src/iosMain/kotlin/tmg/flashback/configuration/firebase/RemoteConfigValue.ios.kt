package tmg.flashback.configuration.firebase

import Firebase.RemoteConfig.ConfigValue

actual class RemoteConfigValue(
    private val value: ConfigValue
) {
    actual fun asBoolean(): Boolean = value.boolValue
    actual fun asDouble(): Double = value.numberValue.doubleValue
    actual fun asLong(): Long = value.numberValue.longValue
    actual fun asString(): String? = value.stringValue
}