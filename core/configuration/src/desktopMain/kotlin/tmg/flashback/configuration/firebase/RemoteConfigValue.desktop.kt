package tmg.flashback.configuration.firebase

actual class RemoteConfigValue(val value: Any) {
    actual fun asBoolean() = false
    actual fun asDouble() = 0.0
    actual fun asLong() = 1L
    actual fun asString(): String? = null
}