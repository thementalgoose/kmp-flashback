package tmg.flashback.ui.components.driver

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import flashback.presentation.ui.generated.resources.Res
import flashback.presentation.ui.generated.resources.unknown_avatar
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import tmg.flashback.style.AppTheme
import tmg.flashback.style.AppThemePreview
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider


val driverIconImageSize: Dp = 48.dp
val driverIconBorderSize: Dp = 6.dp

val driverIconSize: Dp
    get() = driverIconImageSize + driverIconBorderSize

@Composable
fun DriverIcon(
    photoUrl: String?,
    modifier: Modifier = Modifier,
    driverClicked: (() -> Unit)? = null,
    size: Dp = driverIconImageSize,
    borderSize: Dp = driverIconBorderSize,
    constructorColor: Color? = null,
) {
    Box(
        modifier = modifier
            .size(size + borderSize)
            .clip(CircleShape)
            .background(constructorColor ?: AppTheme.colors.surfaceContainer5)
            .clickable(
                enabled = driverClicked != null,
                onClick = {
                    driverClicked?.invoke()
                }
            )
    ) {
        AsyncImage(
            model = photoUrl,
            error = painterResource(resource = Res.drawable.unknown_avatar),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(size)
                .align(Alignment.Center)
                .clip(CircleShape)
                .background(AppTheme.colors.tertiaryContainer),
            contentDescription = null,
        )
    }
}

@Composable
fun DriverImage(
    photoUrl: String?,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(AppTheme.dimens.radiusSmall)),
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            model = photoUrl,
            contentDescription = null,
            error = painterResource(resource = Res.drawable.unknown_avatar)
        )
    }
}

@Preview
@Composable
private fun Preview(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    AppThemePreview(previewConfig) {
        DriverIcon(
            photoUrl = "",
            constructorColor = Color.Red
        )
    }
}