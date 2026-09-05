package tmg.flashback.feature.weekend.repositories

import tmg.flashback.preferences.manager.PreferenceManager

interface WeekendRepository {
    var weatherDetails: Boolean
}

class WeekendRepositoryImpl(
    private val preferenceManager: PreferenceManager
): WeekendRepository {

    override var weatherDetails: Boolean
        get() = preferenceManager.getBoolean(keyWeekendWeatherDetails, true)
        set(value) = preferenceManager.save(keyWeekendWeatherDetails, value)


    companion object {
        private const val keyWeekendWeatherDetails: String = "WEEKEND_WEATHER_DETAILS"
    }
}