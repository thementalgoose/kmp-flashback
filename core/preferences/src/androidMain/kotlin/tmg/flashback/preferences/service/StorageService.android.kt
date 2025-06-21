package tmg.flashback.preferences.service

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

internal const val prefsKey = "Flashback"

actual class StorageService(
    private val context: Application
) {
    private val sharedPrefs: SharedPreferences
        get() = context.getSharedPreferences(prefsKey, Context.MODE_PRIVATE)

    actual fun save(key: String, value: Int) {
        sharedPrefs.edit().putInt(key, value).apply()
    }
    actual fun save(key: String, value: String) {
        sharedPrefs.edit().putString(key, value).apply()
    }
    actual fun save(key: String, value: Long) {
        sharedPrefs.edit().putLong(key, value).apply()
    }
    actual fun save(key: String, value: Float) {
        sharedPrefs.edit().putFloat(key, value).apply()
    }
    actual fun save(key: String, value: Boolean) {
        sharedPrefs.edit().putBoolean(key, value).apply()
    }
    actual fun save(key: String, value: Set<String>) {
        sharedPrefs.edit().putStringSet(key, value).apply()
    }
    actual fun getInt(key: String, value: Int): Int {
        return sharedPrefs.getInt(key, value)
    }
    actual fun getString(key: String, value: String?): String? {
        return sharedPrefs.getString(key, value)
    }
    actual fun getLong(key: String, value: Long): Long {
        return sharedPrefs.getLong(key, value)
    }
    actual fun getFloat(key: String, value: Float): Float {
        return sharedPrefs.getFloat(key, value)
    }
    actual fun getBoolean(key: String, value: Boolean): Boolean {
        return sharedPrefs.getBoolean(key, value)
    }
    actual fun getSet(key: String, value: Set<String>): MutableSet<String> {
        return sharedPrefs.getStringSet(key, value) ?: mutableSetOf()
    }
}