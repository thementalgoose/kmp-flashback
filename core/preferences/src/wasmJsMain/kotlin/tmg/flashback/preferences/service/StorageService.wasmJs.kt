package tmg.flashback.preferences.service

actual class StorageService {

    val memoryCache: MutableMap<String, Any> = mutableMapOf()

    actual fun save(key: String, value: Int) {
        memoryCache[key] = value
    }
    actual fun save(key: String, value: String) {
        memoryCache[key] = value
    }
    actual fun save(key: String, value: Long) {
        memoryCache[key] = value
    }
    actual fun save(key: String, value: Float) {
        memoryCache[key] = value
    }
    actual fun save(key: String, value: Boolean) {
        memoryCache[key] = value
    }
    actual fun save(key: String, value: Set<String>) {
        memoryCache[key] = value
    }
    actual fun getInt(key: String, value: Int): Int {
        return (memoryCache[key] as? Int) ?: value
    }
    actual fun getString(key: String, value: String?): String? {
        return (memoryCache[key] as? String) ?:value
    }
    actual fun getLong(key: String, value: Long): Long {
        return (memoryCache[key] as? Long) ?:value
    }
    actual fun getFloat(key: String, value: Float): Float {
        return (memoryCache[key] as? Float) ?:value
    }
    actual fun getBoolean(key: String, value: Boolean): Boolean {
        return (memoryCache[key] as? Boolean) ?:value
    }
    actual fun getSet(key: String, value: Set<String>): MutableSet<String> {
        val result = memoryCache[key]
        return (memoryCache[key] as? Set<*>)?.map { it.toString() }?.toMutableSet() ?: value.toMutableSet()
    }
}
