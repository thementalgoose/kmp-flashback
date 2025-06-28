package tmg.flashback.webbrowser.repository

import tmg.flashback.preferences.manager.PreferenceManager

interface WebRepository {
    var enabled: Boolean
    var enableJavascript: Boolean
}

internal class WebRepositoryImpl(
    private val preferenceManager: PreferenceManager
): WebRepository {

    override var enabled: Boolean
        get() = preferenceManager.getBoolean(keyEnabled, false)
        set(value) {
            preferenceManager.save(keyEnabled, value)
        }

    override var enableJavascript: Boolean
        get() = preferenceManager.getBoolean(keyEnableJavascript, true)
        set(value) {
            preferenceManager.save(keyEnableJavascript, value)
        }

    companion object {
        private const val keyEnabled = "WEB_BROWSER_ENABLED"
        private const val keyEnableJavascript = "WEB_BROWSER_ENABLE_JAVASCRIPT"
    }
}