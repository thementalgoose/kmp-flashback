package tmg.flashback.ui.components.driver

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider
import tmg.flashback.style.AppTheme
import tmg.flashback.style.AppThemePreview

@Composable
fun DriverNumber(
    number: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    small: Boolean = false,
    highlightNumber: Color = AppTheme.colors.onSurfaceVariant,
) {
    Text(
        modifier = modifier,
        text = number,
        maxLines = 1,
        textAlign = textAlign,
        style = AppTheme.typography.block.copy(
            fontSize = if (small) 12.sp else 16.sp
        ),
        color = highlightNumber
    )
}

@Preview
@Composable
private fun PreviewLight(
    @PreviewParameter(TeamColourProvider::class) teamExample: Pair<String, Color>
) {
    val (driverNumber, color) = teamExample
    AppThemePreview(isLight = true) {
        DriverNumber(
            number = driverNumber,
            highlightNumber = color
        )
    }
}

@Preview
@Composable
private fun PreviewDark(
    @PreviewParameter(TeamColourProvider::class) teamExample: Pair<String, Color>
) {
    val (driverNumber, color) = teamExample
    AppThemePreview(isLight = false) {
        DriverNumber(
            number = driverNumber,
            highlightNumber = color
        )
    }
}

class TeamColourProvider: PreviewParameterProvider<Pair<String, Color>> {
    override val values: Sequence<Pair<String, Color>> = sequenceOf(
        Pair("77", Color(0xFF900000)), // Alfa
        Pair("10", Color(0xFF2B4562)), // Alpha Tauri
        Pair("31", Color(0xFF0090FF)), // Alpine
        Pair("5", Color(0xFF006F62)), // Aston Martin
        Pair("16", Color(0xFFDC0000)), // Ferrari
        Pair("8", Color(0xFFd9d9d9)), // Haas
        Pair("3", Color(0xFFFF8700)), // McLaren
        Pair("63", Color(0xFF00D2BE)), // Mercedes
        Pair("33", Color(0xFF0600EF)), // Red Bull
        Pair("6", Color(0xFF005AFF)), // Williams
    )
}