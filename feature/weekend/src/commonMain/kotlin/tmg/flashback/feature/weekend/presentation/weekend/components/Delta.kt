package tmg.flashback.feature.weekend.presentation.weekend.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import flashback.feature.weekend.generated.resources.Res
import flashback.feature.weekend.generated.resources.ic_pos_down
import flashback.feature.weekend.generated.resources.ic_pos_neutral
import flashback.feature.weekend.generated.resources.ic_pos_up
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import tmg.flashback.style.AppTheme
import tmg.flashback.style.AppThemePreview
import tmg.flashback.style.text.TextBody2
import kotlin.math.abs

@Composable
fun Delta(
    grid: Int?,
    finish: Int,
    modifier: Modifier = Modifier
) {
    val diff = (grid ?: 0) - finish
    when {
        diff == 0 || grid == null -> { // Equal
            Delta(
                modifier = modifier,
                diff = diff,
                color = AppTheme.colors.f1DeltaNeutral,
                icon = Res.drawable.ic_pos_neutral,
                contentDescription = "" // stringResource(id = R.string.ab_positions_neutral, model.grid ?: "unknown", model.finish)
            )
        }
        diff > 0 -> { // Gained
            Delta(
                modifier = modifier,
                diff = diff,
                color = AppTheme.colors.f1DeltaNegative,
                icon = Res.drawable.ic_pos_up,
                contentDescription = "" // stringResource(id = R.string.ab_positions_gained, model.grid ?: "unknown", model.finish, abs(diff))
            )
        }
        else -> { // Lost
            Delta(
                modifier = modifier,
                diff = diff,
                color = AppTheme.colors.f1DeltaPositive,
                icon = Res.drawable.ic_pos_down,
                contentDescription = "" // stringResource(id = R.string.ab_positions_lost, model.grid ?: "unknown", model.finish, abs(diff))
            )
        }
    }
}

@Composable
private fun Delta(
    diff: Int,
    color: Color,
    icon: DrawableResource,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(resource = icon),
            modifier = Modifier.align(Alignment.CenterVertically),
            contentDescription = contentDescription,
            tint = color,
        )
        TextBody2(
            text = abs(diff).toString(),
            textColor = color,
            bold = true,
            modifier = Modifier.padding(
                start = AppTheme.dimens.xxsmall,
                end = AppTheme.dimens.xxsmall
            )
        )
    }
}

@Preview
@Composable
private fun PreviewPositive() {
    AppThemePreview {
        Box(modifier = Modifier.padding(8.dp)) {
            Delta(grid = 3, finish = 2)
        }
    }
}

@Preview
@Composable
private fun PreviewNeutral() {
    AppThemePreview {
        Box(modifier = Modifier.padding(8.dp)) {
            Delta(grid = 2, finish = 2)
        }
    }
}

@Preview
@Composable
private fun PreviewNegative() {
    AppThemePreview {
        Box(modifier = Modifier.padding(8.dp)) {
            Delta(grid = 3, finish = 4)
        }
    }
}