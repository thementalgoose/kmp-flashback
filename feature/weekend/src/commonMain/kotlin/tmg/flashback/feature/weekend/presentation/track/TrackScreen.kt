package tmg.flashback.feature.weekend.presentation.track

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import tmg.flashback.formula1.enums.TrackBreakdowns
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.style.text.TextTitle

@Composable
fun TrackScreen() {
//    ScreenView("Weekend - Track")

    Column(Modifier
        .clip(RoundedCornerShape(10.dp))
        .background(AppTheme.colors.surface)
        .fillMaxWidth()
    ) {
        Track(
            trackBreakdown = TrackBreakdowns.ALBERT_PARK,
        )
        TextTitle("INFO")
    }
}

@PreviewTheme
@Composable
private fun Preview() {
    ApplicationThemePreview {
        TrackScreen()
    }
}