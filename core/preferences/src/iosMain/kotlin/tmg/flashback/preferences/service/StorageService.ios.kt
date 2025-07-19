package tmg.flashback.preferences.service

import platform.Foundation.NSUserDefaults
import tmg.flashback.infrastructure.log.logDebug
import tmg.flashback.infrastructure.log.logInfo

actual class StorageService {
    actual fun save(key: String, value: Int) {
        NSUserDefaults.standardUserDefaults.setInteger(value.toLong(), forKey = key)
    }
    actual fun save(key: String, value: String) {
        NSUserDefaults.standardUserDefaults.setObject(value, forKey = key)
    }
    actual fun save(key: String, value: Long) {
        NSUserDefaults.standardUserDefaults.setInteger(value, forKey = key)
    }
    actual fun save(key: String, value: Float) {
        NSUserDefaults.standardUserDefaults.setFloat(value, forKey = key)
    }
    actual fun save(key: String, value: Boolean) {
        NSUserDefaults.standardUserDefaults.setBool(value, forKey = key)
    }
    actual fun save(key: String, value: Set<String>) {
        val saveString = value.joinToString(separator = "|")
        NSUserDefaults.standardUserDefaults.setObject(saveString, forKey = key)
    }
    actual fun getInt(key: String, value: Int): Int {
        if (NSUserDefaults.standardUserDefaults.objectForKey(key) == null) {
            return value
        }
        return NSUserDefaults.standardUserDefaults.integerForKey(key).toInt()
    }
    actual fun getString(key: String, value: String?): String? {
        if (NSUserDefaults.standardUserDefaults.objectForKey(key) == null) {
            return value
        }
        return NSUserDefaults.standardUserDefaults.objectForKey(key) as? String
    }
    actual fun getLong(key: String, value: Long): Long {
        if (NSUserDefaults.standardUserDefaults.objectForKey(key) == null) {
            return value
        }
        return NSUserDefaults.standardUserDefaults.integerForKey(key)
    }
    actual fun getFloat(key: String, value: Float): Float {
        if (NSUserDefaults.standardUserDefaults.objectForKey(key) == null) {
            return value
        }
        return NSUserDefaults.standardUserDefaults.floatForKey(key)
    }
    actual fun getBoolean(key: String, value: Boolean): Boolean {
        if (NSUserDefaults.standardUserDefaults.objectForKey(key) == null) {
            return value
        }
        return NSUserDefaults.standardUserDefaults.boolForKey(key)
    }
    actual fun getSet(key: String, value: Set<String>): MutableSet<String> {
        val string = NSUserDefaults.standardUserDefaults.objectForKey(key) as? String
        val set = string?.split("|")?.toMutableSet() ?: value.toMutableSet()
        logDebug("getSet $key -> $string -> $set")
        return set
            .filter { it.isNotBlank() }
            .toMutableSet()
    }
}