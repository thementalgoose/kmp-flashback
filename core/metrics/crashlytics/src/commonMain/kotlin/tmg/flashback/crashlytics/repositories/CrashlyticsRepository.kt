package tmg.flashback.crashlytics.repositories

import org.koin.core.annotation.Single
import tmg.flashback.preferences.manager.PreferenceManager

interface CrashlyticsRepository {
    var crashlyticsEnabled: Boolean
}

@Single
internal class CrashlyticsRepositoryImpl(
    private val preferenceManager: PreferenceManager
): CrashlyticsRepository {
    override var crashlyticsEnabled: Boolean
        get() = preferenceManager.getBoolean(keyCrashlytics, true)
        set(value) {
            preferenceManager.save(keyCrashlytics, value)
        }

    companion object {
        private const val keyCrashlytics: String = "CRASH_REPORTING"
    }
}