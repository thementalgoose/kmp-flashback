package tmg.flashback.configuration.firebase

expect class RemoteConfigValue {
    fun asBoolean(): Boolean
    fun asDouble(): Double
    fun asLong(): Long
    fun asString(): String?
}