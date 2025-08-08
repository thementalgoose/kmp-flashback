package tmg.flashback.highlights.repositories

import tmg.flashback.preferences.manager.PreferenceManager

class HighlightsRepositoryImpl(
    private val preferenceManager: PreferenceManager
): HighlightsRepository {

    override var show: Boolean
        get() = preferenceManager.getBoolean(keyRecentHighlights, false)
        set(value) = preferenceManager.save(keyRecentHighlights, value)

    companion object {
        private const val keyRecentHighlights = "RECENT_HIGHLIGHTS"
    }
}