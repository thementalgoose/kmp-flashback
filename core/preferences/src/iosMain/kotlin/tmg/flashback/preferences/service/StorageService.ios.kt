package tmg.flashback.preferences.service

import platform.Foundation.NSUserDefaults

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
        return NSUserDefaults.standardUserDefaults.integerForKey(key).toInt()
    }
    actual fun getString(key: String, value: String?): String? {
        return NSUserDefaults.standardUserDefaults.objectForKey(key) as? String
    }
    actual fun getLong(key: String, value: Long): Long {
        return NSUserDefaults.standardUserDefaults.integerForKey(key)
    }
    actual fun getFloat(key: String, value: Float): Float {
        return NSUserDefaults.standardUserDefaults.floatForKey(key)
    }
    actual fun getBoolean(key: String, value: Boolean): Boolean {
        return NSUserDefaults.standardUserDefaults.boolForKey(key)
    }
    actual fun getSet(key: String, value: Set<String>): MutableSet<String> {
        val string = NSUserDefaults.standardUserDefaults.objectForKey(key) as? String
        return string?.split("|")?.toMutableSet() ?: value.toMutableSet()
    }
}