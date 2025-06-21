package tmg.flashback.preferences.manager

import tmg.flashback.preferences.service.StorageService

interface PreferenceManager {
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

internal class PreferenceManagerImpl(
    private val storageService: StorageService
): PreferenceManager {
    override fun save(key: String, value: Int) {
        storageService.save(key, value)
    }

    override fun save(key: String, value: String) {
        storageService.save(key, value)
    }

    override fun save(key: String, value: Long) {
        storageService.save(key, value)
    }

    override fun save(key: String, value: Float) {
        storageService.save(key, value)
    }

    override fun save(key: String, value: Boolean) {
        storageService.save(key, value)
    }

    override fun save(key: String, value: Set<String>) {
        storageService.save(key, value)
    }

    override fun getInt(key: String, value: Int): Int {
        return storageService.getInt(key, value)
    }

    override fun getString(key: String, value: String?): String? {
        return storageService.getString(key, value)
    }

    override fun getLong(key: String, value: Long): Long {
        return storageService.getLong(key, value)
    }

    override fun getFloat(key: String, value: Float): Float {
        return storageService.getFloat(key, value)
    }

    override fun getBoolean(key: String, value: Boolean): Boolean {
        return storageService.getBoolean(key, value)
    }

    override fun getSet(
        key: String,
        value: Set<String>
    ): MutableSet<String> {
        return storageService.getSet(key, value)
    }
}