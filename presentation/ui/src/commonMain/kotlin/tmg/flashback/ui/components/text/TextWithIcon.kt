package tmg.flashback.ui.components.text

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.style.text.TextHeadline2

@Composable
fun TextWithIcon(
    icon: Painter?,
    iconModifier: Modifier,
    content: @Composable () -> Unit,
) {
    Box {
        Row {
            content()
            Spacer(Modifier.width(10.dp))
        }
        icon?.let {
            Image(
                painter = it,
                contentDescription = null,
                modifier = iconModifier
                    .align(Alignment.TopEnd),
            )
        }
    }
}

@PreviewTheme
@Composable
private fun PreviewIcon() {
    ApplicationThemePreview {
        TextWithIcon(
            icon = rememberVectorPainter(Icons.Default.Home),
            iconModifier = Modifier.rotate(40f),
            content = {
                TextHeadline2("Headline 2")
            }
        )
    }
}