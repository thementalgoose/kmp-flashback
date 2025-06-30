package tmg.flashback.widgets.upnext.repositories

import tmg.flashback.preferences.manager.PreferenceManager

interface UpNextWidgetRepository {
    var showBackground: Boolean
    var deeplinkToEvent: Boolean
    var showWeather: Boolean
}

internal class UpNextWidgetRepositoryImpl(
    private val preferenceManager: PreferenceManager
): UpNextWidgetRepository {
    override var showBackground: Boolean
        get() = preferenceManager.getBoolean(keyWidgetShowBackground, true)
        set(value) = preferenceManager.save(keyWidgetShowBackground, value)

    override var deeplinkToEvent: Boolean
        get() = preferenceManager.getBoolean(keyWidgetDeeplinkToEvent, false)
        set(value) = preferenceManager.save(keyWidgetDeeplinkToEvent, value)

    override var showWeather: Boolean
        get() = preferenceManager.getBoolean(keyWidgetShowWeather, false)
        set(value) = preferenceManager.save(keyWidgetShowWeather, value)

    companion object {
        private const val keyWidgetShowBackground: String = "WIDGET_SHOW_BACKGROUND"
        private const val keyWidgetDeeplinkToEvent: String = "WIDGET_DEEPLINK_EVENT"
        private const val keyWidgetShowWeather: String = "WIDGET_SHOW_WEATHER"
    }
}