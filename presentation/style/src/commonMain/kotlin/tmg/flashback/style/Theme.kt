package tmg.flashback.style

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject
import tmg.flashback.infrastructure.log.logInfo
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.theme.Theme
import tmg.flashback.style.theme.ThemeManager

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
    isLight: Boolean = !isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val themeManager = koinInject<ThemeManager>()
    val theme = themeManager.currentTheme

    AppTheme.appTheme = theme

    ApplicationTheme(
        isLight = isLight,
        theme = theme,
        content = content
    )
}

@Composable
fun ApplicationTheme(
    isLight: Boolean = !isSystemInDarkTheme(),
    theme: Theme,
    content: @Composable () -> Unit
) {
    val appColours = getColours(theme)
    val colors = when (isLight) {
        true -> appColours.lightColours
        false -> appColours.darkColours
    }

    LocalColors.provides(colors)

    CompositionLocalProvider(
        LocalColors provides colors
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
    previewConfig: PreviewConfig?,
    content: @Composable () -> Unit,
) {
    ApplicationThemePreview(
        isLight = previewConfig?.isLightMode == true,
        theme = previewConfig?.theme ?: Theme.Default,
        content = content
    )
}

@Composable
fun ApplicationThemePreview(
    isLight: Boolean = !isSystemInDarkTheme(),
    theme: Theme = Theme.Default,
    content: @Composable () -> Unit,
) {
    return ApplicationTheme(
        isLight = isLight,
        theme = theme,
        content = {
            Box(modifier = Modifier
                .background(AppTheme.colors.surface)
            ) {
                content()
            }
        }
    )
}

data class ThemeColours(
    val lightColours: AppColors,
    val darkColours: AppColors,
)

expect fun getColours(theme: Theme): ThemeColours

//sealed class SupportedTheme(
//    val themePref: Theme
//){
//
//    data object Default: SupportedTheme(Theme.Default) {
//        val lightColors: AppColors = lightColours
//        val darkColors: AppColors = darkColours
//    }
//
//    object MaterialYou: SupportedTheme(Theme.MaterialYou) {
//        @RequiresApi(Build.VERSION_CODES.S)
//        fun lightColors(context: Context): AppColors {
//            return lightColours.dynamic(dynamicLightColorScheme(context), isLightMode = true)
//        }
//
//        @RequiresApi(Build.VERSION_CODES.S)
//        fun darkColors(context: Context): AppColors {
//            return darkColours.dynamic(dynamicDarkColorScheme(context), isLightMode = false)
//        }
//    }
//
//}