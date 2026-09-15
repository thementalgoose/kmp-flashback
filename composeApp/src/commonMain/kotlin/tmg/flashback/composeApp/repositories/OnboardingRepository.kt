package tmg.flashback.composeApp.repositories

import org.koin.core.annotation.Single
import tmg.flashback.preferences.manager.PreferenceManager

interface OnboardingRepository {
    var initialSyncCompleted: Boolean
}

@Single(binds = [OnboardingRepository::class])
internal class OnboardingRepositoryImpl(
    private val preferenceManager: PreferenceManager
): OnboardingRepository {
    override var initialSyncCompleted: Boolean
        get() = preferenceManager.getBoolean(keyInitialSync, false)
        set(value) { preferenceManager.save(keyInitialSync, value) }

    companion object {
        private const val keyInitialSync: String = "REPO_INITIAL_SYNC"
    }
}