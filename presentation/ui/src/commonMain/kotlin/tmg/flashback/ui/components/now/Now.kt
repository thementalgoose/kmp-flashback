package tmg.flashback.ui.components.now

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import flashback.presentation.ui.generated.resources.Res
import flashback.presentation.ui.generated.resources.ic_current_indicator
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview

@Composable
fun Now(
    modifier: Modifier = Modifier
) {
    Icon(
        modifier = modifier
            .size(12.dp)
            .alpha(0.4f),
        painter = painterResource(resource = Res.drawable.ic_current_indicator),
        contentDescription = null,
        tint = AppTheme.colors.onSurfaceVariant
    )
}

@Preview
@Composable
private fun Preview() {
    ApplicationThemePreview {
        Now()
    }
}