package tmg.flashback.repositories

import tmg.flashback.preferences.manager.PreferenceManager

interface ContentSyncRepository {
    var initialSyncCompleted: Boolean
}

internal class ContentSyncRepositoryImpl(
    private val preferenceManager: PreferenceManager
): ContentSyncRepository {
    override var initialSyncCompleted: Boolean
        get() = preferenceManager.getBoolean(keyInitialSync, false)
        set(value) { preferenceManager.save(keyInitialSync, value) }

    companion object {
        private const val keyInitialSync: String = "REPO_INITIAL_SYNC"
    }
}