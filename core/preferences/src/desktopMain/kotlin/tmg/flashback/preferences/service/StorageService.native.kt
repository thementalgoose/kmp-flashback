package tmg.flashback.preferences.service

actual class StorageService {
    actual fun save(key: String, value: Int) {

    }
    actual fun save(key: String, value: String) {

    }
    actual fun save(key: String, value: Long) {

    }
    actual fun save(key: String, value: Float) {

    }
    actual fun save(key: String, value: Boolean) {

    }
    actual fun save(key: String, value: Set<String>) {

    }
    actual fun getInt(key: String, value: Int): Int {
        return value
    }
    actual fun getString(key: String, value: String?): String? {
        return value
    }
    actual fun getLong(key: String, value: Long): Long {
        return value
    }
    actual fun getFloat(key: String, value: Float): Float {
        return value
    }
    actual fun getBoolean(key: String, value: Boolean): Boolean {
        return value
    }
    actual fun getSet(key: String, value: Set<String>): MutableSet<String> {
        return value.toMutableSet()
    }
}