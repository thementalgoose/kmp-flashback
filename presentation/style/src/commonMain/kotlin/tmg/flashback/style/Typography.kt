package tmg.flashback.style

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import flashback.presentation.style.generated.resources.Res
import flashback.presentation.style.generated.resources.closeness
import flashback.presentation.style.generated.resources.montserrat
import flashback.presentation.style.generated.resources.montserrat_semibold
import org.jetbrains.compose.resources.Font

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
        fontFamily = montserrat,
        fontWeight = FontWeight.SemiBold,
        fontSize = 36.sp
    ),
    h2 = TextStyle(
        fontFamily = montserrat,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp
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
    val title: TextStyle,
    val section: TextStyle,
    val body1: TextStyle,
    val body2: TextStyle,
    val caption: TextStyle,
    val block: TextStyle,
)