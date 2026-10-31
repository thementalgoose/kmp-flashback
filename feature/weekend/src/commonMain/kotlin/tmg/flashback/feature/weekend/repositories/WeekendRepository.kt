package tmg.flashback.feature.weekend.repositories

import tmg.flashback.preferences.manager.PreferenceManager
import org.koin.core.annotation.Single

interface WeekendRepository {
    var weatherDetails: Boolean
}

@Single
class WeekendRepositoryImpl(
    private val preferenceManager: PreferenceManager
): WeekendRepository {

    override var weatherDetails: Boolean
        get() = preferenceManager.getBoolean(keyWeekendWeatherDetails, false)
        set(value) = preferenceManager.save(keyWeekendWeatherDetails, value)


    companion object {
        private const val keyWeekendWeatherDetails: String = "WEEKEND_WEATHER_DETAILS"
    }
}