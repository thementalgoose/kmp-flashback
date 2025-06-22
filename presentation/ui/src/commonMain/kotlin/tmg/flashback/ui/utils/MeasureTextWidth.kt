package tmg.flashback.ui.utils

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import tmg.flashback.style.AppTheme

@Composable
fun MeasureTextWidth(
    text: String,
    textStyle: TextStyle = AppTheme.typography.title,
    modifier: Modifier = Modifier,
    content: @Composable (width: Dp) -> Unit
) {
    SubcomposeLayout(
        modifier = modifier
    ) { constraints ->
        val textWidth = subcompose("sampleText") {
            Text(text, style = textStyle)
        }[0].measure(Constraints()).width.toDp()

        val contentPlaceable = subcompose("content") {
            content(textWidth)
        }[0].measure(constraints)

        layout(contentPlaceable.width, contentPlaceable.height) {
            contentPlaceable.place(0, 0)
        }
    }
}