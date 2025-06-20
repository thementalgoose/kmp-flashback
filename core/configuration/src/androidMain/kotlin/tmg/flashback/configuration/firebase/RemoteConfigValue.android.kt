package tmg.flashback.configuration.firebase

import com.google.firebase.remoteconfig.FirebaseRemoteConfigValue

actual class RemoteConfigValue(
    val value: FirebaseRemoteConfigValue
) {
    actual fun asBoolean(): Boolean = value.asBoolean()
    actual fun asDouble(): Double = value.asDouble()
    actual fun asLong(): Long = value.asLong()
    actual fun asString(): String? = value.asString()
}