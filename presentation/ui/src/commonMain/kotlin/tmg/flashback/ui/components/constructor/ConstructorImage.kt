package tmg.flashback.ui.components.constructor

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import tmg.flashback.style.AppTheme

val constructorIconImageSize: Dp = 48.dp
val constructorIconBorderSize: Dp = 6.dp

@Composable
fun ConstructorImage(
    photoUrl: String?,
    constructorColor: Color,
    modifier: Modifier = Modifier,
    constructorClicked: (() -> Unit)? = null,
    size: Dp = constructorIconImageSize,
    borderSize: Dp = constructorIconBorderSize,
) {
    Box(
        modifier = modifier
            .size(size + borderSize)
            .clip(RoundedCornerShape(AppTheme.dimens.radiusMedium))
            .background(constructorColor)
            .clickable(
                enabled = constructorClicked != null,
                onClick = {
                    constructorClicked?.invoke()
                }
            )
    ) {
        AsyncImage(
            model = photoUrl,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(size)
                .align(Alignment.Center)
                .clip(RoundedCornerShape(AppTheme.dimens.radiusMedium))
                .background(AppTheme.colors.surface),
            contentDescription = null,
        )
    }
}