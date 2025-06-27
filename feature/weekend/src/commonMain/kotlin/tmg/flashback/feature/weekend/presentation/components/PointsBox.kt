package tmg.flashback.feature.weekend.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import flashback.presentation.localisation.generated.resources.Res.plurals
import flashback.presentation.localisation.generated.resources.race_points
import org.jetbrains.compose.resources.pluralStringResource
import tmg.flashback.infrastructure.extensions.roundToHalf
import tmg.flashback.style.AppTheme
import tmg.flashback.style.text.TextBody1
import kotlin.math.roundToInt


internal val pointsWidth: Dp = 64.dp

@Composable
fun PointsBox(
    points: Double,
    colour: Color,
    modifier: Modifier = Modifier,
    maxPoints: Double = 60.0
) {
    val pointsLabel =
        pluralStringResource(resource = plurals.race_points, quantity = points.roundToInt(), points.roundToHalf())
    Box(modifier = modifier
        .width(pointsWidth)
        .padding(top = AppTheme.dimens.medium)
        .clearAndSetSemantics {
            this.contentDescription = pointsLabel
        }
    ) {
        TextBody1(
            modifier = modifier.align(Alignment.Center),
            text = points.roundToHalf(),
        )
    }
}