package tmg.flashback.feature.weekend.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import flashback.feature.weekend.generated.resources.Res
import flashback.feature.weekend.generated.resources.ic_collapsible
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.nav_race
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider
import tmg.flashback.style.text.TextHeadline2


@Composable
internal fun TypeHeader(
    resource: StringResource,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
    isCollapsed: MutableState<Boolean>? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = AppTheme.dimens.medium,
                vertical = AppTheme.dimens.small
            )
            .clickable(
                enabled = isCollapsed != null,
                onClick = onClick
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextHeadline2(
            text = stringResource(resource),
            modifier = Modifier.weight(1f)
        )
        if (isCollapsed != null) {
            val collapsingIconRotation = animateFloatAsState(when (isCollapsed.value) {
                true -> 0f
                false -> 90f
            })
            IconButton(
                onClick = {
                    isCollapsed.value = !isCollapsed.value
                },
                content = {
                    Icon(
                        modifier = Modifier.rotate(collapsingIconRotation.value),
                        contentDescription = null,
                        painter = painterResource(Res.drawable.ic_collapsible),
                        tint = AppTheme.colors.onSurface
                    )
                }
            )
        }
    }
}

@Composable
@Preview
private fun PreviewTypeHeaderRegular(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    ApplicationThemePreview(previewConfig) {
        TypeHeader(
            resource = string.nav_race
        )
    }
}

@Composable
@Preview
private fun PreviewTypeHeaderCollapsed(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    ApplicationThemePreview(previewConfig) {
        val isCollapsed = remember { mutableStateOf(false) }
        TypeHeader(
            isCollapsed = isCollapsed,
            resource = string.nav_race
        )
    }
}

@Composable
@Preview
private fun PreviewTypeHeaderExpanded(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    ApplicationThemePreview(previewConfig) {
        val isCollapsed = remember { mutableStateOf(true) }
        TypeHeader(
            isCollapsed = isCollapsed,
            resource = string.nav_race
        )
    }
}