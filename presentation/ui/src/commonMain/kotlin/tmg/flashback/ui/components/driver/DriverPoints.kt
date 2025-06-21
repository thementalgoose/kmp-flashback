package tmg.flashback.ui.components.driver

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import flashback.presentation.localisation.generated.resources.Res.plurals
import flashback.presentation.localisation.generated.resources.race_points
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import tmg.flashback.infrastructure.extensions.roundToHalf
import tmg.flashback.style.AppTheme
import tmg.flashback.style.AppThemePreview
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider
import tmg.flashback.style.text.TextBody2
import tmg.flashback.style.text.TextCaption
import tmg.flashback.ui.components.flag.Flag
import kotlin.math.roundToInt

@Composable
fun DriverPoints(
    name: String,
    nationality: String,
    nationalityISO: String,
    points: Double,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier
            .padding(
                vertical = AppTheme.dimens.xxsmall
            )
        ) {
            Flag(
                iso = nationalityISO,
                nationality = nationality,
                modifier = Modifier.size(16.dp)
            )
        }
        TextBody2(
            text = name,
            modifier = Modifier.padding(horizontal = AppTheme.dimens.xsmall)
        )
        val points = pluralStringResource(plurals.race_points, points.takeIf { !it.isNaN() }?.roundToInt() ?: 0, points.roundToHalf())
        TextCaption(text = "- $points")
    }
}

@Preview
@Composable
private fun Preview(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    AppThemePreview(previewConfig) {
        DriverPoints(
            name = "firstName lastName",
            nationality = "",
            nationalityISO = "",
            points = 3.0
        )
    }
}