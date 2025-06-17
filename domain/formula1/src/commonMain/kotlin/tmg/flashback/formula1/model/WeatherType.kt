package tmg.flashback.formula1.model

import flashback.domain.formula1.generated.resources.*
import flashback.domain.formula1.generated.resources.Res
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

enum class WeatherType(
    val label: StringResource,
    val icon: DrawableResource
) {
    CLEAR_SKY(
        label = string.weather_type_clear_sky,
        icon = Res.drawable.weather_clear_sky
    ),
    CLOUDS_LIGHT(
        label = string.weather_type_clouds_light,
        icon = Res.drawable.weather_clouds_light
    ),
    CLOUDS_SCATTERED(
        label = string.weather_type_clouds_scattered,
        icon = Res.drawable.weather_clouds_scattered
    ),
    CLOUDS_BROKEN(
        label = string.weather_type_clouds_broken,
        icon = Res.drawable.weather_clouds_broken
    ),
    CLOUDS_OVERCAST(
        label = string.weather_type_clouds_overcast,
        icon = Res.drawable.weather_overcast
    ),
    RAIN_LIGHT(
        label = string.weather_type_rain_light,
        icon = Res.drawable.weather_rain_light
    ),
    RAIN_MODERATE(
        label = string.weather_type_rain_moderate,
        icon = Res.drawable.weather_rain_moderate
    ),
    RAIN_HEAVY(
        label = string.weather_type_rain_heavy,
        icon = Res.drawable.weather_rain_heavy
    ),
    THUNDERSTORM(
        label = string.weather_type_thunderstorm,
        icon = Res.drawable.weather_thunderstorms
    ),
    SNOW(
        label = string.weather_type_snow,
        icon = Res.drawable.weather_snow
    ),
    SAND(
        label = string.weather_type_sand,
        icon = Res.drawable.weather_sand
    ),
    FOG(
        label = string.weather_type_fog,
        icon = Res.drawable.weather_fog
    ),
    MIST(
        label = string.weather_type_mist,
        icon = Res.drawable.weather_mist
    );
}