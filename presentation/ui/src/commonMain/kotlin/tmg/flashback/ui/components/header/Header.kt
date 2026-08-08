package tmg.flashback.ui.components.header

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.*
import flashback.presentation.ui.generated.resources.Res
import flashback.presentation.ui.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.style.text.TextHeadline1

@Composable
fun Header(
    actionUpClicked: () -> Unit,
    modifier: Modifier = Modifier,
    topInset: Dp = 0.dp,
    scrim: Boolean = false,
    scrimColour: Color = AppTheme.colors.surface,
    action: HeaderAction? = null,
    actionModifier: Modifier = Modifier,
    overrideIcons: @Composable RowScope.(Modifier) -> Unit = { },
    contentSpacing: Dp = 0.dp,
    content: @Composable RowScope.() -> Unit
) {

    val scrimModifier = when (scrim) {
        true -> Modifier.background(
            brush = Brush.verticalGradient(listOf(Color.Transparent, scrimColour))
        )
        false -> Modifier
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .then(scrimModifier)
            .padding(top = AppTheme.dimens.xsmall)
    ) {
        Spacer(Modifier.height(topInset))
        if (action != null) {
            Row {
                IconButton(
                    modifier = actionModifier,
                    onClick = actionUpClicked,
                ) {
                    Icon(
                        painter = painterResource(resource = action.icon),
                        contentDescription = stringResource(resource = action.contentDescription),
                        tint = AppTheme.colors.onSurface
                    )
                }
                Spacer(Modifier.weight(1f))
                overrideIcons(Modifier)
            }
        }
        Spacer(Modifier.height(contentSpacing))
        Box {

            Row(
                verticalAlignment = Alignment.Top
            ) {
                Row(Modifier.weight(1f)) {
                    content()
                }
                if (action == null) {
                    Row(
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        overrideIcons(Modifier)
                    }
                }
            }
        }
    }
}

@Composable
fun Header(
    text: String,
    actionUpClicked: () -> Unit,
    topInset: Dp = 0.dp,
    scrim: Boolean = false,
    scrimColour: Color = AppTheme.colors.surface,
    modifier: Modifier = Modifier,
    action: HeaderAction? = null,
    actionModifier: Modifier = Modifier,
    contentSpacing: Dp = 0.dp,
    overrideIcons: @Composable RowScope.(Modifier) -> Unit = { },
) {
    Header(
        actionUpClicked = actionUpClicked,
        modifier = modifier,
        action = action,
        actionModifier = actionModifier,
        topInset = topInset,
        scrim = scrim,
        scrimColour = scrimColour,
        contentSpacing = contentSpacing,
        overrideIcons = overrideIcons,
        content = {
            TextHeadline1(
                text = text,
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        start = AppTheme.dimens.medium,
                        end = AppTheme.dimens.medium,
                        top = AppTheme.dimens.medium,
                        bottom = AppTheme.dimens.medium
                    )
            )
        }
    )
}

enum class HeaderAction(
    val icon: DrawableResource,
    val contentDescription: StringResource
) {
    MENU(
        icon = Res.drawable.ic_menu,
        contentDescription = string.ab_menu
    ),
    BACK(
        icon = Res.drawable.ic_back,
        contentDescription = string.ab_back
    ),
    CLOSE(
        icon = Res.drawable.ic_close,
        contentDescription = string.ab_close
    )
}

@PreviewTheme
@Composable
private fun Preview() {
    ApplicationThemePreview {
        Header(
            text = "2022",
            action = HeaderAction.MENU,
            actionUpClicked = { }
        )
    }
}

@PreviewTheme
@Composable
private fun PreviewWithOverride() {
    ApplicationThemePreview {
        Header(
            text = "2022",
            action = HeaderAction.MENU,
            actionUpClicked = { },
            overrideIcons = {
                IconButton(onClick = { }) {
                    Icon(
                        painter = painterResource(resource = Res.drawable.ic_close),
                        contentDescription = stringResource(resource = string.tyres_label),
                        tint = AppTheme.colors.onSurfaceVariant
                    )
                }
            }
        )
    }
}

@PreviewTheme
@Composable
private fun PreviewWithOverrideWithInsetAndScrim() {
    ApplicationThemePreview {
        Header(
            text = "2022",
            action = HeaderAction.MENU,
            topInset = 16.dp,
            scrim = true,
            scrimColour = Color.Magenta,
            actionUpClicked = { },
            overrideIcons = {
                IconButton(onClick = { }) {
                    Icon(
                        painter = painterResource(resource = Res.drawable.ic_close),
                        contentDescription = stringResource(resource = string.tyres_label),
                        tint = AppTheme.colors.onSurfaceVariant
                    )
                }
            }
        )
    }
}

@PreviewTheme
@Composable
private fun PreviewNoIcon() {
    ApplicationThemePreview {
        Header(
            text = "2022",
            action = null,
            actionUpClicked = { }
        )
    }
}


@PreviewTheme
@Composable
private fun PreviewNoIconAndScrim() {
    ApplicationThemePreview {
        Header(
            text = "2022",
            action = null,
            scrim = true,
            scrimColour = Color.Magenta,
            actionUpClicked = { }
        )
    }
}

@PreviewTheme
@Composable
private fun PreviewNoIconWithOverride() {
    ApplicationThemePreview {
        Header(
            text = "2022",
            action = null,
            actionUpClicked = { },
            overrideIcons = {
                IconButton(onClick = { }) {
                    Icon(
                        painter = painterResource(resource = Res.drawable.ic_close),
                        contentDescription = stringResource(resource = string.tyres_label),
                        tint = AppTheme.colors.onSurfaceVariant
                    )
                }
            }
        )
    }
}