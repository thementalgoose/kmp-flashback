package tmg.flashback.style

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import flashback.presentation.style.generated.resources.Res
import flashback.presentation.style.generated.resources.closeness
import flashback.presentation.style.generated.resources.montserrat
import flashback.presentation.style.generated.resources.montserrat_semibold
import org.jetbrains.compose.resources.Font
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.style.text.TextBody1
import tmg.flashback.style.text.TextBody2
import tmg.flashback.style.text.TextCaption
import tmg.flashback.style.text.TextHeadline1
import tmg.flashback.style.text.TextHeadline2
import tmg.flashback.style.text.TextHeadline3
import tmg.flashback.style.text.TextHeadline4
import tmg.flashback.style.text.TextSection
import tmg.flashback.style.text.TextTitle

@get:Composable
private val montserrat
    get() = FontFamily(
        Font(Res.font.montserrat, weight = FontWeight.Normal),
        Font(Res.font.montserrat_semibold, weight = FontWeight.SemiBold)
    )

@get:Composable
private val closeness
    get() = FontFamily(
        Font(Res.font.closeness, weight = FontWeight.Bold)
    )

@Composable
fun AppTypography() = AppTypography(
    h1 = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold,
        letterSpacing = (-1.5).sp,
        fontSize = 32.sp
    ),
    h2 = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Normal,
        letterSpacing = (-1.5).sp,
        fontSize = 24.sp
    ),
    h3 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontFamily = FontFamily.SansSerif,
        letterSpacing = 0.5.sp,
        fontSize = 24.sp,
    ),
    h4 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontFamily = FontFamily.SansSerif,
        letterSpacing = 0.5.sp,
        fontSize = 20.sp,
    ),
    title = TextStyle(
        fontSize = 16.sp
    ),
    section = TextStyle(
        fontFamily = montserrat,
        fontWeight = FontWeight.SemiBold,
        fontSize = 13.sp
    ),
    body1 = TextStyle(
        fontSize = 14.sp
    ),
    body2 = TextStyle(
        fontSize = 12.sp
    ),
    caption = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 11.sp
    ),

    block = TextStyle(
        fontFamily = closeness,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    )
)

data class AppTypography(
    val h1: TextStyle,
    val h2: TextStyle,
    val h3: TextStyle,
    val h4: TextStyle,
    val title: TextStyle,
    val section: TextStyle,
    val body1: TextStyle,
    val body2: TextStyle,
    val caption: TextStyle,
    val block: TextStyle,
)

@PreviewTheme
@Composable
private fun Preview() {
    ApplicationThemePreview {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            TextHeadline1("headline1")
            TextHeadline2("headline2")
            TextHeadline3("headline3")
            TextHeadline4("headline4")
            TextTitle("title")
            TextSection("section")
            TextBody1("body1")
            TextBody2("body2")
            TextCaption("caption")
            Text(
                text = "block",
                color = AppTheme.colors.onSurface,
                style = AppTheme.typography.block
            )
        }
    }
}