package tmg.flashback.eastereggs.repository

import tmg.flashback.configuration.manager.ConfigManager

internal interface EasterEggsRepository {
    val isSnowEnabled: Boolean
    val isSummerEnabled: Boolean
    val isUkraineEnabled: Boolean
}

internal class EasterEggsRepositoryImpl(
    private val configManager: ConfigManager
): EasterEggsRepository {

    companion object {
        private const val keySnow = "easteregg_snow"
        private const val keySummer = "easteregg_summer"
        private const val keyUkraine = "easteregg_ukraine"
    }

    override val isSnowEnabled: Boolean
        get() = configManager.getBoolean(keySnow)

    override val isSummerEnabled: Boolean
        get() = configManager.getBoolean(keySummer)

    override val isUkraineEnabled: Boolean
        get() = configManager.getBoolean(keyUkraine)
}