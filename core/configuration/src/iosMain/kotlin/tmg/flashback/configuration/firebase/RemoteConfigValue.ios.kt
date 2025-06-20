package tmg.flashback.configuration.firebase


actual class RemoteConfigValue(
//    private val value: ConfigValue
) {
    actual fun asBoolean(): Boolean = false //  value.boolValue
    actual fun asDouble(): Double = 1.0 //  value.numberValue.doubleValue
    actual fun asLong(): Long = 1L // value.numberValue.longValue
    actual fun asString(): String? = "string" // value.stringValue
}