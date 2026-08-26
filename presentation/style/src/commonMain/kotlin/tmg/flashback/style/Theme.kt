package tmg.flashback.style

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.key
import org.koin.compose.koinInject
import tmg.flashback.infrastructure.log.logDebug
import tmg.flashback.style.theme.LocalDarkMode
import tmg.flashback.style.theme.NightMode
import tmg.flashback.style.theme.Theme
import tmg.flashback.style.theme.ThemeManager
import tmg.flashback.style.theme.customLightMode
import tmg.flashback.xr.LocalXR
import tmg.flashback.xr.XR
import tmg.flashback.xr.noopXr
import tmg.flashback.xr.xr

object AppTheme {
    var appTheme: Theme = Theme.Default

    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: AppTypography
        @Composable
        get() = AppTypography()

    val dimens: AppDimensions = AppDimensions()

    internal val disabledAlpha = 0.4f
}

@Composable
fun ApplicationTheme(
    content: @Composable () -> Unit
) {
    val themeManager = koinInject<ThemeManager>()
    val theme = themeManager.currentTheme

    logDebug("Theme", "Theme: $theme")
    logDebug("Theme", "DarkMode: ${!LocalDarkMode.current}")

    customLightMode = when (themeManager.currentNightMode) {
        NightMode.DEFAULT -> null
        NightMode.DAY -> true
        NightMode.NIGHT -> false
    }

    AppTheme.appTheme = theme

    CompositionLocalProvider(
        LocalDarkMode provides customLightMode,
        LocalXR provides xr()
    ) {
        key(customLightMode) {
            setSystemBarsIconColours(LocalDarkMode.current)
            ApplicationTheme(
                isLight = LocalDarkMode.current,
                theme = theme,
                content = content
            )
        }
    }
}

@Composable
fun ApplicationTheme(
    isLight: Boolean,
    theme: Theme,
    xr: XR = xr(),
    content: @Composable () -> Unit
) {
    val appColours = getColours(theme)
    val colors = when (isLight) {
        true -> appColours.lightColours
        false -> appColours.darkColours
    }

    LocalColors.provides(colors)

    CompositionLocalProvider(
        LocalColors provides colors,
        LocalXR provides xr
    ) {
        MaterialTheme(
            colorScheme = colors.appColors
        ) {
            content()
        }
    }
}

@Composable
fun ApplicationThemePreview(
    isLight: Boolean = LocalDarkMode.current,
    theme: Theme = Theme.Default,
    content: @Composable () -> Unit,
) {
    return ApplicationTheme(
        isLight = isLight,
        theme = theme,
        xr = noopXr(),
        content = content
    )
}

data class ThemeColours(
    val lightColours: AppColors,
    val darkColours: AppColors,
)

expect fun getColours(theme: Theme): ThemeColours

@Composable
expect fun setSystemBarsIconColours(isLight: Boolean)