package tmg.flashback.feature.weekend.repositories

import tmg.flashback.preferences.manager.PreferenceManager

interface WeatherRepository {
    var weatherTemperatureMetric: Boolean
    var weatherWindspeedMetric: Boolean
}

class WeatherRepositoryImpl(
    private val preferenceManager: PreferenceManager
): WeatherRepository {

    override var weatherTemperatureMetric: Boolean
        get() = preferenceManager.getBoolean(keyWeatherTemperatureMetric, true)
        set(value) = preferenceManager.save(keyWeatherTemperatureMetric, value)

    override var weatherWindspeedMetric: Boolean
        get() = preferenceManager.getBoolean(keyWeatherWindspeedMetric, false)
        set(value) = preferenceManager.save(keyWeatherWindspeedMetric, value)

    companion object {
        private const val keyWeatherTemperatureMetric: String = "WEATHER_TEMPERATURE_METRIC"
        private const val keyWeatherWindspeedMetric: String = "WEATHER_WINDSPEED_METRIC"
    }
}