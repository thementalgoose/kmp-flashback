package tmg.flashback.ui.components.indicators

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import tmg.flashback.style.AppTheme

fun Modifier.stateBorder(color: Color): Modifier = this
    .border(
        width = 2.dp,
        color = color,
        shape = RoundedCornerShape(AppTheme.dimens.radiusSmall)
    )