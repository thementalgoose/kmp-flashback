package tmg.flashback.webbrowser.repository

import tmg.flashback.preferences.manager.PreferenceManager

interface WebRepository {
    var openInExternal: Boolean
    var enableJavascript: Boolean
}

internal class WebRepositoryImpl(
    private val preferenceManager: PreferenceManager
): WebRepository {

    override var openInExternal: Boolean
        get() = preferenceManager.getBoolean(keyOpenInExternal, true)
        set(value) {
            preferenceManager.save(keyOpenInExternal, value)
        }

    override var enableJavascript: Boolean
        get() = preferenceManager.getBoolean(keyEnableJavascript, true)
        set(value) {
            preferenceManager.save(keyEnableJavascript, value)
        }

    companion object {
        private const val keyOpenInExternal = "WEB_BROWSER_OPEN_IN_EXTERNAL"
        private const val keyEnableJavascript = "WEB_BROWSER_ENABLE_JAVASCRIPT"
    }
}