package tmg.flashback.preferences.service

expect class StorageService {
    fun save(key: String, value: Int)
    fun save(key: String, value: String)
    fun save(key: String, value: Long)
    fun save(key: String, value: Float)
    fun save(key: String, value: Boolean)
    fun save(key: String, value: Set<String>)
    fun getInt(key: String, value: Int = -1): Int
    fun getString(key: String, value: String? = null): String?
    fun getLong(key: String, value: Long = -1L): Long
    fun getFloat(key: String, value: Float = -1f): Float
    fun getBoolean(key: String, value: Boolean = false): Boolean
    fun getSet(key: String, value: Set<String>): MutableSet<String>
}
