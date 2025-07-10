package tmg.flashback.style

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


val BRAND_PRIMARY = Color(0xFF0274D1)
val BRAND_SECONDARY = Color(0xFF00E2E4)

val SPLASH_SCREEN = Color(0xFF01A5D9)

val LOGO_GRADIENT_1 = Color(0xFF0274D1)
val LOGO_GRADIENT_2 = Color(0xFF00E2E4)

internal val LocalColors = staticCompositionLocalOf { lightColours }

// https://material-foundation.github.io/material-theme-builder/

data class AppColors(
    // Theme
    val primary: Color,
    val primaryInverse: Color,
    val onPrimary: Color,
    val secondary: Color,
    val onSecondary: Color,
    val tertiary: Color,
    val onTertiary: Color,

    val primaryContainer: Color,
    val onPrimaryContainer: Color,
    val secondaryContainer: Color,
    val onSecondaryContainer: Color,
    val tertiaryContainer: Color,
    val onTertiaryContainer: Color,

    val error: Color,
    val onError: Color,
    val errorContainer: Color,
    val onErrorContainer: Color,

    val surfaceDim: Color,
    val surface: Color,
    val surfaceBright: Color,
    val surfaceContainer1: Color,
    val surfaceContainer2: Color,
    val surfaceContainer3: Color,
    val surfaceContainer4: Color,
    val surfaceContainer5: Color,
    val onSurface: Color,
    val onSurfaceVariant: Color,
    val outline: Color,
    val outlineVariant: Color,

    val surfaceInverse: Color,
    val onSurfaceInverse: Color,

    val surfaceNav: Color = surfaceContainer3,

    // System
    val systemStatusBarColor: Color,
    val systemNavigationBarColor: Color,

    // F1
    val f1Podium1: Color,
    val f1Podium2: Color,
    val f1Podium3: Color,
    val f1DeltaPositive: Color,
    val f1DeltaNeutral: Color = onSurface,
    val f1DeltaNegative: Color,
    val f1ResultsFull: Color,
    val f1ResultsNeutral: Color = onSurface,
    val f1ResultsPartial: Color,
    val f1ResultsUpcoming: Color = primary,
    val f1FastestSector: Color,
    val f1FavouriteSeason: Color,
    val f1Championship: Color,
    val f1PipeColor: Color = onSurfaceVariant,
    val f1StartLightGreen: Color,
    val f1StartLightAmber: Color,
    val f1StartLightRed: Color,

    // RSS
    val rssAdd: Color,
    val rssRemove: Color,
    val isLight: Boolean
) {

    val appColors: ColorScheme by lazy {
        if (isLight) {
            lightColorScheme(
                primary = primary,
                inversePrimary = primaryInverse,
                onPrimary = onPrimary,
                secondary = secondary,
                onSecondary = onSecondary,
                tertiary = tertiary,
                onTertiary = onTertiary,

                primaryContainer = primaryContainer,
                onPrimaryContainer = onPrimaryContainer,
                secondaryContainer = secondaryContainer,
                onSecondaryContainer = onSecondaryContainer,
                tertiaryContainer = tertiaryContainer,
                onTertiaryContainer = onTertiaryContainer,

                error = error,
                onError = onError,
                errorContainer = errorContainer,
                onErrorContainer = onErrorContainer,

                surfaceDim = surfaceDim,
                surface = surface,
                surfaceBright = surfaceBright,
                surfaceContainerLowest = surfaceContainer1,
                surfaceContainerLow = surfaceContainer2,
                surfaceContainer = surfaceContainer3,
                surfaceContainerHigh = surfaceContainer4,
                surfaceContainerHighest = surfaceContainer5,
                onSurface = onSurface,
                onSurfaceVariant = onSurfaceVariant,
                outline = outline,
                outlineVariant = outlineVariant,

                inverseSurface = surfaceInverse,
                inverseOnSurface = onSurfaceInverse,

                background = surfaceContainer1,
                onBackground = onSurface
            )
        } else {
            darkColorScheme(
                primary = primary,
                inversePrimary = primaryInverse,
                onPrimary = onPrimary,
                secondary = secondary,
                onSecondary = onSecondary,
                tertiary = tertiary,
                onTertiary = onTertiary,

                primaryContainer = primaryContainer,
                onPrimaryContainer = onPrimaryContainer,
                secondaryContainer = secondaryContainer,
                onSecondaryContainer = onSecondaryContainer,
                tertiaryContainer = tertiaryContainer,
                onTertiaryContainer = onTertiaryContainer,

                error = error,
                onError = onError,
                errorContainer = errorContainer,
                onErrorContainer = onErrorContainer,

                surfaceDim = surfaceDim,
                surface = surface,
                surfaceBright = surfaceBright,
                surfaceContainerLowest = surfaceContainer1,
                surfaceContainerLow = surfaceContainer2,
                surfaceContainer = surfaceContainer3,
                surfaceContainerHigh = surfaceContainer4,
                surfaceContainerHighest = surfaceContainer5,
                onSurface = onSurface,
                onSurfaceVariant = onSurfaceVariant,
                outline = outline,
                outlineVariant = outlineVariant,

                inverseSurface = surfaceInverse,
                inverseOnSurface = onSurfaceInverse,

                background = surfaceContainer1,
                onBackground = onSurface
            )
        }
    }
}

internal val textDark: Color = Color(0xFF181818)
internal val textLight: Color = Color(0xFFF8F8F8)

val lightColours = AppColors(
    primary = Color(0xFF006879),
    primaryInverse = Color(0xFF84d2e5),
    onPrimary = Color(0xFFffffff),
    secondary = Color(0xFF1d6586),
    onSecondary = Color(0xFFffffff),
    tertiary = Color(0xFF3d5f90),
    onTertiary = Color(0xFFffffff),

    primaryContainer = Color(0xFFa8edff),
    onPrimaryContainer = Color(0xFF004e5b),
    secondaryContainer = Color(0xFFc4e7ff),
    onSecondaryContainer = Color(0xFF004c6a),
    tertiaryContainer = Color(0xFFdde1ff),
    onTertiaryContainer = Color(0xFF3e4565),

    error = Color(0xFFba1a1a),
    onError = Color(0xFFffffff),
    errorContainer = Color(0xFFffdad6),
    onErrorContainer = Color(0xFF93000a),

    surfaceDim = Color(0xffd5dbdd),
    surface = Color(0xfff2f8f9),
    surfaceBright = Color(0xfff5fafc),
    surfaceContainer1 = Color(0xfffdfeff),
    surfaceContainer2 = Color(0xffeff4f6),
    surfaceContainer3 = Color(0xffe9eff0),
    surfaceContainer4 = Color(0xffe3e9eb),
    surfaceContainer5 = Color(0xffdee3e5),
    onSurface = Color(0xff171d1e),
    onSurfaceVariant = Color(0xff3f484b),
    outline = Color(0xff6f797b),
    outlineVariant = Color(0xffbfc8cb),

    surfaceInverse = Color(0xff2b3133),
    onSurfaceInverse = Color(0xffecf2f3),

    systemStatusBarColor = Color(0xFF0274D1),
    systemNavigationBarColor = Color(0xFFFCFCFC),

    f1Podium1 = Color(0xFFD3BC4D),
    f1Podium2 = Color(0xFFC2C2C2),
    f1Podium3 = Color(0xFFD29342),
    f1DeltaPositive = Color(0xFFF44336),
//    f1DeltaNeutral = ,
    f1DeltaNegative = Color(0xFF4CAF50),
    f1ResultsFull = Color(0xFF4CAF50),
//    f1ResultsNeutral = ,
    f1ResultsPartial = Color(0xFFFFA000),
    f1FastestSector = Color(0xFF673AB7),
    f1FavouriteSeason = Color(0xFFA38B21),
    f1Championship = Color(0xFFA38B21),
    f1StartLightGreen = Color(0xFF4CAF50),
    f1StartLightAmber = Color(0xFFFFA000),
    f1StartLightRed = Color(0xFFF44336),
//    f1PipeColor = ,
    rssAdd = Color(0xFF4CAF50),
    rssRemove = Color(0xFFF44336),

    isLight = true
)

val darkColours = AppColors(
    primary = Color(0xFF84d2e5),
    primaryInverse = Color(0xFF006879),
    onPrimary = Color(0xFF00363f),
    secondary = Color(0xFF90cef4),
    onSecondary = Color(0xFF00344a),
    tertiary = Color(0xFFbec5eb),
    onTertiary = Color(0xFF272f4d),

    primaryContainer = Color(0xFF004e5b),
    onPrimaryContainer = Color(0xFFa8edff),
    secondaryContainer = Color(0xFF004c6a),
    onSecondaryContainer = Color(0xFFc4e7ff),
    tertiaryContainer = Color(0xFF3e4565),
    onTertiaryContainer = Color(0xFFdde1ff),

    error = Color(0xFFffb4ab),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000a),
    onErrorContainer = Color(0xFFffdad6),

    surfaceDim = Color(0xff0e1416),
    surface = Color(0xff0e1416),
    surfaceBright = Color(0xff343a3c),
    surfaceContainer1 = Color(0xff090f11),
    surfaceContainer2 = Color(0xff171d1e),
    surfaceContainer3 = Color(0xff1b2122),
    surfaceContainer4 = Color(0xff252b2c),
    surfaceContainer5 = Color(0xff303637),
    onSurface = Color(0xffdee3e5),
    onSurfaceVariant = Color(0xffbfc8cb),
    outline = Color(0xff899295),
    outlineVariant = Color(0xff3f484b),

    surfaceInverse = Color(0xffdee3e5),
    onSurfaceInverse = Color(0xff2b3133),

    systemStatusBarColor = Color(0xFF181818),
    systemNavigationBarColor = Color(0xFF383838),

    f1Podium1 = Color(0xFFD3BC4D),
    f1Podium2 = Color(0xFFC2C2C2),
    f1Podium3 = Color(0xFFD29342),
    f1DeltaPositive = Color(0xFFF44336),
//    f1DeltaNeutral = ,
    f1DeltaNegative = Color(0xFF4CAF50),
    f1ResultsFull = Color(0xFF4CAF50),
//    f1ResultsNeutral = ,
    f1ResultsPartial = Color(0xFFFFA000),
    f1FastestSector = Color(0xFF673AB7),
    f1FavouriteSeason = Color(0xFFE6CA4F),
    f1Championship = Color(0xFFE6CA4F),
    f1StartLightGreen = Color(0xFF4CAF50),
    f1StartLightAmber = Color(0xFFFFA000),
    f1StartLightRed = Color(0xFFF44336),
//    f1PipeColor = ,
    rssAdd = Color(0xFF4CAF50),
    rssRemove = Color(0xFFF44336),

    isLight = false
)

fun AppColors.dynamic(colorScheme: ColorScheme, isLightMode: Boolean) = copy(

    primary = colorScheme.primary,
    primaryInverse = colorScheme.inversePrimary,
    onPrimary = colorScheme.onPrimary,
    secondary = colorScheme.secondary,
    onSecondary = colorScheme.onSecondary,
    tertiary = colorScheme.tertiary,
    onTertiary = colorScheme.onTertiary,

    primaryContainer = colorScheme.primaryContainer,
    onPrimaryContainer = colorScheme.onPrimaryContainer,
    secondaryContainer = colorScheme.secondaryContainer,
    onSecondaryContainer = colorScheme.onSecondaryContainer,
    tertiaryContainer = colorScheme.tertiaryContainer,
    onTertiaryContainer = colorScheme.onTertiaryContainer,

    error = colorScheme.error,
    onError = colorScheme.onError,
    errorContainer = colorScheme.errorContainer,
    onErrorContainer = colorScheme.onErrorContainer,

    surfaceDim = colorScheme.surfaceDim,
    surface = colorScheme.surface,
    surfaceBright = colorScheme.surfaceBright,
    surfaceContainer1 = colorScheme.surfaceContainerLowest,
    surfaceContainer2 = colorScheme.surfaceContainerLow,
    surfaceContainer3 = colorScheme.surfaceContainer,
    surfaceContainer4 = colorScheme.surfaceContainerHigh,
    surfaceContainer5 = colorScheme.surfaceContainerHighest,
    onSurface = colorScheme.onSurface,
    onSurfaceVariant = colorScheme.onSurfaceVariant,
    outline = colorScheme.outline,
    outlineVariant = colorScheme.outlineVariant,

    surfaceInverse = colorScheme.inverseSurface,
    onSurfaceInverse = colorScheme.inverseOnSurface,

    systemStatusBarColor = when (isLightMode) {
        true -> colorScheme.background
        false -> colorScheme.background
    },
    systemNavigationBarColor = colorScheme.surfaceColorAtElevation(6.dp)
)