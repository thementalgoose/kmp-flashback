package tmg.flashback.preferences.service

actual class StorageService {
    actual fun save(key: String, value: Int) { }
    actual fun save(key: String, value: String) { }
    actual fun save(key: String, value: Long) { }
    actual fun save(key: String, value: Float) { }
    actual fun save(key: String, value: Boolean) { }
    actual fun save(key: String, value: Set<String>) { }
    actual fun getInt(key: String, value: Int): Int = value
    actual fun getString(key: String, value: String?): String? = value
    actual fun getLong(key: String, value: Long): Long = value
    actual fun getFloat(key: String, value: Float): Float = value
    actual fun getBoolean(key: String, value: Boolean): Boolean = value
    actual fun getSet(key: String, value: Set<String>): MutableSet<String> = value.toMutableSet()
}
