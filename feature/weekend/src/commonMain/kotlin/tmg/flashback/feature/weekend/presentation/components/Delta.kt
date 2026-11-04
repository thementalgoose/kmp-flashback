package tmg.flashback.feature.weekend.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.dp
import flashback.feature.weekend.generated.resources.Res
import flashback.feature.weekend.generated.resources.ic_pos_down
import flashback.feature.weekend.generated.resources.ic_pos_neutral
import flashback.feature.weekend.generated.resources.ic_pos_up
import flashback.presentation.localisation.generated.resources.Res as LocRes
import flashback.presentation.localisation.generated.resources.ab_positions_gained
import flashback.presentation.localisation.generated.resources.ab_positions_lost
import flashback.presentation.localisation.generated.resources.ab_positions_neutral
import flashback.presentation.localisation.generated.resources.ab_positions_unknown_grid
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.tooling.preview.Preview
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
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
        grid == null -> {
            Delta(
                modifier = modifier,
                diff = diff,
                color = AppTheme.colors.f1DeltaNeutral,
                icon = Res.drawable.ic_pos_neutral,
                contentDescription = stringResource(LocRes.string.ab_positions_unknown_grid, finish.toString())
            )
        }
        diff == 0 -> { // Equal
            Delta(
                modifier = modifier,
                diff = diff,
                color = AppTheme.colors.f1DeltaNeutral,
                icon = Res.drawable.ic_pos_neutral,
                contentDescription = stringResource(LocRes.string.ab_positions_neutral, grid.toString(), finish.toString())
            )
        }
        diff > 0 -> { // Gained
            Delta(
                modifier = modifier,
                diff = diff,
                color = AppTheme.colors.f1DeltaNegative,
                icon = Res.drawable.ic_pos_up,
                contentDescription = stringResource(LocRes.string.ab_positions_gained, grid.toString(), finish.toString(), abs(diff))
            )
        }
        else -> { // Lost
            Delta(
                modifier = modifier,
                diff = diff,
                color = AppTheme.colors.f1DeltaPositive,
                icon = Res.drawable.ic_pos_down,
                contentDescription = stringResource(LocRes.string.ab_positions_lost, grid.toString(), finish.toString(), abs(diff))
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
        modifier = modifier.clearAndSetSemantics {
            this.contentDescription = contentDescription
        },
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(resource = icon),
            modifier = Modifier.align(Alignment.CenterVertically),
            contentDescription = null,
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
    ApplicationThemePreview {
        Box(modifier = Modifier.padding(8.dp)) {
            Delta(grid = 3, finish = 2)
        }
    }
}

@Preview
@Composable
private fun PreviewNeutral() {
    ApplicationThemePreview {
        Box(modifier = Modifier.padding(8.dp)) {
            Delta(grid = 2, finish = 2)
        }
    }
}

@Preview
@Composable
private fun PreviewNegative() {
    ApplicationThemePreview {
        Box(modifier = Modifier.padding(8.dp)) {
            Delta(grid = 3, finish = 4)
        }
    }
}