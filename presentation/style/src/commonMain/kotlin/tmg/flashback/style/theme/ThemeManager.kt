package tmg.flashback.style.theme

import tmg.flashback.infrastructure.extensions.toEnum
import tmg.flashback.preferences.manager.PreferenceManager

interface ThemeManager {
    var currentTheme: Theme
    var currentNightMode: NightMode
}

internal class ThemeManagerImpl(
    private val preferenceManager: PreferenceManager
): ThemeManager {

    companion object {
        private const val PREFERENCE_THEME = "THEME_CHOICE"
        private const val PREFERENCE_NIGHT_MODE = "THEME" // Used to be called theme
    }

    private val Theme.saveKey: String
        get() = when (this) {
            Theme.Default -> "default"
            Theme.MaterialYou -> "material_you"
        }

    private val NightMode.saveKey: String
        get() = when (this) {
            NightMode.DEFAULT -> "AUTO"
            NightMode.DAY -> "DAY"
            NightMode.NIGHT -> "NIGHT"
        }

    override var currentTheme: Theme
        get() = preferenceManager.getString(PREFERENCE_THEME)
            ?.toEnum<Theme>() { it.saveKey }
            ?: Theme.Default
        set(value) {
            preferenceManager.save(PREFERENCE_THEME, value.saveKey)
        }

    override var currentNightMode: NightMode
        get() = preferenceManager.getString(PREFERENCE_NIGHT_MODE)
            ?.toEnum<NightMode> { it.saveKey }
            ?: NightMode.DEFAULT
        set(value) {
            preferenceManager.save(PREFERENCE_NIGHT_MODE, value.saveKey)
            customLightMode = (value == NightMode.DAY).takeIf { value != NightMode.DEFAULT }
        }
}