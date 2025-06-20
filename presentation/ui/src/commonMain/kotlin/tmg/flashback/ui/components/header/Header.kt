package tmg.flashback.ui.components.header

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.*
import flashback.presentation.ui.generated.resources.Res
import flashback.presentation.ui.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import tmg.flashback.style.AppTheme
import tmg.flashback.style.AppThemePreview
import tmg.flashback.style.text.TextHeadline1

@Composable
fun Header(
    actionUpClicked: () -> Unit,
    modifier: Modifier = Modifier,
    action: HeaderAction? = null,
    overrideIcons: @Composable () -> Unit = { },
    content: @Composable RowScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = AppTheme.dimens.xsmall)
    ) {
        if (action != null) {
            Row {
                IconButton(onClick = actionUpClicked) {
                    Icon(
                        painter = painterResource(resource = action.icon),
                        contentDescription = stringResource(resource = action.contentDescription),
                        tint = AppTheme.colors.contentPrimary
                    )
                }
                Spacer(Modifier.weight(1f))
                overrideIcons()
            }
            Spacer(Modifier.height(AppTheme.dimens.large))
        }
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
                    overrideIcons()
                }
            }
        }
    }
}

@Composable
fun Header(
    text: String,
    actionUpClicked: () -> Unit,
    modifier: Modifier = Modifier,
    action: HeaderAction? = null,
    overrideIcons: @Composable () -> Unit = { },
) {
    Header(
        actionUpClicked = actionUpClicked,
        modifier = modifier,
        action = action,
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

@Preview
@Composable
private fun Preview() {
    AppThemePreview {
        Header(
            text = "2022",
            action = HeaderAction.MENU,
            actionUpClicked = { }
        )
    }
}

@Preview
@Composable
private fun PreviewWithOverrideLight() {
    AppThemePreview(isLight = true) {  }
}

@Preview
@Composable
private fun PreviewWithOverride() {
    AppThemePreview {
        Header(
            text = "2022",
            action = HeaderAction.MENU,
            actionUpClicked = { },
            overrideIcons = {
                IconButton(onClick = { }) {
                    Icon(
                        painter = painterResource(resource = Res.drawable.ic_close),
                        contentDescription = stringResource(resource = string.tyres_label),
                        tint = AppTheme.colors.contentSecondary
                    )
                }
            }
        )
    }
}

@Preview
@Composable
private fun PreviewNoIconLight() {
    AppThemePreview(isLight = true) {
        PreviewNoIcon()
    }
}

@Preview
@Composable
private fun PreviewNoIconDark() {
    AppThemePreview(isLight = false) {
        PreviewNoIcon()
    }
}

@Composable
private fun PreviewNoIcon() {
    Header(
        text = "2022",
        action = null,
        actionUpClicked = { }
    )
}


@Preview
@Composable
private fun PreviewNoIconWithOverrideLight() {
    AppThemePreview(isLight = true) {
        PreviewNoIconWithOverride()
    }
}

@Preview
@Composable
private fun PreviewNoIconWithOverrideDark() {
    AppThemePreview(isLight = false) {
        PreviewNoIconWithOverride()
    }
}

@Preview
@Composable
private fun PreviewNoIconWithOverride() {
    AppThemePreview {
        Header(
            text = "2022",
            action = null,
            actionUpClicked = { },
            overrideIcons = {
                IconButton(onClick = { }) {
                    Icon(
                        painter = painterResource(resource = Res.drawable.ic_close),
                        contentDescription = stringResource(resource = string.tyres_label),
                        tint = AppTheme.colors.contentSecondary
                    )
                }
            }
        )
    }
}