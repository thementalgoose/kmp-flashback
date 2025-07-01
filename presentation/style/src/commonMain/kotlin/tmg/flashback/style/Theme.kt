package tmg.flashback.style

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import flashback.presentation.style.generated.resources.Res
import flashback.presentation.style.generated.resources.ic_preview_icon
import flashback.presentation.style.generated.resources.preview
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import tmg.flashback.style.buttons.ButtonItem
import tmg.flashback.style.buttons.ButtonPrimary
import tmg.flashback.style.buttons.ButtonSecondary
import tmg.flashback.style.buttons.ButtonTertiary
import tmg.flashback.style.buttons.Segments
import tmg.flashback.style.textinput.TextInput
import tmg.flashback.style.input.InputRadio
import tmg.flashback.style.input.InputSelection
import tmg.flashback.style.input.InputSwitch
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.text.TextBody1
import tmg.flashback.style.text.TextBody2
import tmg.flashback.style.text.TextCaption
import tmg.flashback.style.text.TextHeadline1
import tmg.flashback.style.text.TextHeadline2
import tmg.flashback.style.text.TextTitle
import tmg.flashback.style.theme.NightMode
import tmg.flashback.style.theme.ThemeManager

object AppTheme {
    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    var appTheme: SupportedTheme = SupportedTheme.Default

    var isLight: Boolean = true

    val typography: AppTypography
        @Composable
        get() = AppTypography()

    val dimens: AppDimensions = AppDimensions()

    internal val disabledAlpha = 0.4f
}

val Dimens = AppTheme.dimens

@Composable
fun AppTheme(
    isLight: Boolean = !isSystemInDarkTheme(),
    theme: SupportedTheme = AppTheme.appTheme,
    content: @Composable () -> Unit
) {
//    val themeManager = koinInject<ThemeManager>()
//    val isLightMode = when (themeManager.currentNightMode) {
//        NightMode.DEFAULT -> isSystemInDarkTheme()
//        NightMode.DAY -> true
//        NightMode.NIGHT -> false
//    }

    AppTheme.appTheme = theme
    AppTheme.isLight = isLight

    val colors = when {
//        Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && theme == SupportedTheme.MaterialYou && isLight -> {
//            SupportedTheme.MaterialYou.lightColors(LocalContext.current)
//        }
//        Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && theme == SupportedTheme.MaterialYou && !isLight -> {
//            SupportedTheme.MaterialYou.darkColors(LocalContext.current)
//        }
        theme == SupportedTheme.Default && isLight -> {
            SupportedTheme.Default.lightColors
        }
        theme == SupportedTheme.Default && !isLight -> {
            SupportedTheme.Default.darkColors
        }
        else -> SupportedTheme.Default.lightColors
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
fun AppThemePreview(
    previewConfig: PreviewConfig?,
    content: @Composable () -> Unit,
) {
    AppThemePreview(
        isLight = previewConfig?.isLightMode ?: false,
        theme = previewConfig?.theme ?: SupportedTheme.Default,
        content = content
    )
}

@Composable
fun AppThemePreview(
    isLight: Boolean = !isSystemInDarkTheme(),
    theme: SupportedTheme = SupportedTheme.Default,
    content: @Composable () -> Unit,
) {
    return AppTheme(
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

sealed class SupportedTheme{

    data object Default: SupportedTheme() {
        val lightColors: AppColors = lightColours
        val darkColors: AppColors = darkColours
    }
//
//    object MaterialYou: SupportedTheme() {
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

}