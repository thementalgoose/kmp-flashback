package tmg.flashback.feature.weekend.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import flashback.domain.formula1.generated.resources.ic_race_finishes
import flashback.domain.formula1.generated.resources.ic_race_points
import flashback.feature.weekend.generated.resources.Res
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.ab_points
import flashback.presentation.localisation.generated.resources.ab_status
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import tmg.flashback.style.AppTheme
import tmg.flashback.style.AppThemePreview

@Composable
fun RaceHeader(
    modifier: Modifier = Modifier,
    showPoints: Boolean,
    showStatus: Boolean
) {
    Row(modifier) {

        Box(Modifier.weight(1f))

        if (showStatus) {
            Box(
                modifier = Modifier.Companion.width(timeWidth),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(resource = flashback.domain.formula1.generated.resources.Res.drawable.ic_race_finishes),
                    contentDescription = stringResource(resource = string.ab_status),
                    tint = AppTheme.colors.onSurface
                )
            }
        }

        if (showPoints) {
            Box(
                modifier = Modifier.width(pointsWidth),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(resource = flashback.domain.formula1.generated.resources.Res.drawable.ic_race_points),
                    contentDescription = stringResource(resource = string.ab_points),
                    tint = AppTheme.colors.onSurface
                )
            }
        }
    }
}

@Composable
@Preview
private fun Preview() {
    AppThemePreview {
        RaceHeader(showPoints = true, showStatus = true)
    }
}