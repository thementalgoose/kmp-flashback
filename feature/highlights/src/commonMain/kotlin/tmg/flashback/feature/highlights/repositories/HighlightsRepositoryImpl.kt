package tmg.flashback.feature.highlights.repositories

import org.koin.core.annotation.Single
import tmg.flashback.preferences.manager.PreferenceManager

@Single(binds = [HighlightsRepository::class])
class HighlightsRepositoryImpl(
    private val preferenceManager: PreferenceManager
): HighlightsRepository {

    override var show: Boolean
        get() = preferenceManager.getBoolean(keyRecentHighlights, true)
        set(value) = preferenceManager.save(keyRecentHighlights, value)

    companion object {
        private const val keyRecentHighlights = "RECENT_HIGHLIGHTS"
    }
}