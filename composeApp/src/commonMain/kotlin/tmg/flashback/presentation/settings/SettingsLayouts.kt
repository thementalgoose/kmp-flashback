package tmg.flashback.presentation.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.isEnabled
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.settings_experimental
import flashback.presentation.localisation.generated.resources.settings_header_appearance
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import tmg.flashback.style.AppTheme
import tmg.flashback.style.AppThemePreview
import tmg.flashback.style.badge.Badge
import tmg.flashback.style.badge.BadgeView
import tmg.flashback.style.input.InputRadio
import tmg.flashback.style.input.InputSwitch
import tmg.flashback.style.modifiers.appearDisabled
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider
import tmg.flashback.style.text.TextBody2
import tmg.flashback.style.text.TextSection
import tmg.flashback.style.text.TextTitle

private val categoryIconSize: Dp = 36.dp

private val defaultSettingModifier = Modifier
    .padding(
        vertical = AppTheme.dimens.nsmall,
        horizontal = AppTheme.dimens.medium
    )

fun LazyListScope.PrefHeader(
    title: StringResource
) {
    item(title.key) {
        SettingHeader(title = title)
    }
}

fun LazyListScope.PrefCategory(
    item: Setting.Category,
    itemClicked: (Setting.Category) -> Unit,
) {
    item(item.id) {
        SettingCategory(
            item = item,
            itemClicked = itemClicked
        )
    }
}

fun LazyListScope.PrefLink(
    item: Setting.Link,
    itemClicked: (Setting.Link) -> Unit,
    isEnabled: Boolean = item.isEnabled
) {
    item(item.id) {
        SettingLink(
            item = item,
            itemClicked = itemClicked,
            isEnabled = isEnabled
        )
    }
}

fun LazyListScope.PrefRadio(
    item: Setting.Link,
    itemClicked: (Setting.Link) -> Unit,
    isChecked: Boolean,
    isEnabled: Boolean = item.isEnabled
) {
    item(item.id) {
        SettingRadio(
            item = item,
            itemClicked = itemClicked,
            isChecked = isChecked,
            isEnabled = isEnabled
        )
    }
}

fun LazyListScope.PrefSwitch(
    item: Setting.Link,
    itemClicked: (Setting.Link) -> Unit,
    isChecked: Boolean,
    isEnabled: Boolean = item.isEnabled
) {
    item(item.id) {
        SettingSwitch(
            item = item,
            itemClicked = itemClicked,
            isChecked = isChecked,
            isEnabled = isEnabled
        )
    }
}

@Composable
fun SettingHeader(
    title: StringResource,
    modifier: Modifier = defaultSettingModifier,
) {
    Box(modifier = modifier) {
        TextSection(
            brand = true,
            text = stringResource(title)
        )
    }
}

@Composable
fun SettingLink(
    item: Setting.Link,
    itemClicked: (Setting.Link) -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
) {
    SettingLink(
        item = item,
        isEnabled = isEnabled,
        modifier = modifier
            .clickable(
                enabled = isEnabled,
                onClick = {
                    itemClicked(item)
                }
            )
            .then(defaultSettingModifier),
        content = { }
    )
}

@Composable
fun SettingRadio(
    item: Setting.Link,
    itemClicked: (Setting.Link) -> Unit,
    isChecked: Boolean,
    isEnabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    SettingLink(
        item = item,
        isEnabled = isEnabled,
        modifier = modifier
            .toggleable(
                value = isChecked,
                onValueChange = { itemClicked(item) },
                enabled = isEnabled
            )
            .then(defaultSettingModifier),
        content = {
            InputRadio(
                isChecked = isChecked,
            )
        }
    )
}

@Composable
fun SettingSwitch(
    item: Setting.Link,
    itemClicked: (Setting.Link) -> Unit,
    isChecked: Boolean,
    isEnabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    SettingLink(
        item = item,
        isEnabled = isEnabled,
        modifier = modifier
            .toggleable(
                value = isChecked,
                onValueChange = { itemClicked(item) },
                enabled = isEnabled
            )
            .then(defaultSettingModifier),
        content = {
            InputSwitch(
                isChecked = isChecked,
            )
        }
    )
}

@Composable
private fun SettingLink(
    item: Setting.Link,
    isEnabled: Boolean,
    modifier: Modifier,
    content: @Composable () -> Unit = { }
) {
    Row(modifier = modifier
        .appearDisabled(isEnabled),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextTitle(
                    text = stringResource(resource = item.title)
                )
                if (item.isBeta) {
                    Spacer(Modifier.width(AppTheme.dimens.small))
                    ExperimentalLabel()
                }
            }
            item.subtitle?.let {
                TextBody2(
                    text = stringResource(resource = it),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp)
                )
            }
        }
        content()
    }
}

@Composable
fun SettingCategory(
    item: Setting.Category,
    itemClicked: (Setting.Category) -> Unit,
    isEnabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier
        .clickable(
            enabled = isEnabled,
            onClick = {
                itemClicked(item)
            }
        )
        .then(defaultSettingModifier)
        .appearDisabled(isEnabled)
    ) {
        if (item.icon != null) {
            Icon(
                modifier = Modifier
                    .size(categoryIconSize)
                    .align(Alignment.CenterVertically)
                    .padding(start = AppTheme.dimens.small),
                painter = painterResource(resource = item.icon),
                contentDescription = null,
                tint = AppTheme.colors.onSurface
            )
        } else {
            Spacer(Modifier.size(categoryIconSize))
        }
        Spacer(Modifier.width(AppTheme.dimens.medium))
        Column(Modifier.fillMaxWidth()) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextTitle(
                    text = stringResource(resource = item.title)
                )
                if (item.isBeta) {
                    Spacer(Modifier.width(AppTheme.dimens.small))
                    ExperimentalLabel()
                }
            }
            TextBody2(
                text = stringResource(resource = item.subtitle),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp)
            )
        }
    }
}

@Composable
private fun ExperimentalLabel(
    modifier: Modifier = Modifier
) {
    BadgeView(
        model = Badge(label = stringResource(string.settings_experimental)),
        modifier = modifier
    )
}

@Preview
@Composable
private fun PreviewHeader(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    AppThemePreview(previewConfig) {
        SettingHeader(string.settings_header_appearance)
    }
}


@Preview
@Composable
private fun PreviewCategory(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    AppThemePreview(previewConfig) {
        SettingCategory(
            item = Settings.AboutCategory,
            itemClicked = { }
        )
    }
}

@Preview
@Composable
private fun PreviewCategoryBeta(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    AppThemePreview(previewConfig) {
        SettingCategory(
            item = Settings.AboutCategory.copy(isBeta = true),
            itemClicked = { }
        )
    }
}


@Preview
@Composable
private fun PreviewCategoryDisabled(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    AppThemePreview(previewConfig) {
        SettingCategory(
            item = Settings.AboutCategory.copy(isEnabled = false),
            itemClicked = { }
        )
    }
}

@Preview
@Composable
private fun PreviewCategoryNoIcon(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    AppThemePreview(previewConfig) {
        SettingCategory(
            item = Settings.NotificationsUpcomingNotice,
            itemClicked = { }
        )
    }
}

@Preview
@Composable
private fun PreviewLink(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    AppThemePreview(previewConfig) {
        SettingLink(
            item = Settings.DarkMode.DarkModeAuto,
            itemClicked = { }
        )
    }
}

@Preview
@Composable
private fun PreviewLinkNoSubtitle(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    AppThemePreview(previewConfig) {
        SettingSwitch(
            item = Settings.DarkMode.DarkModeAuto.copy(subtitle = null),
            itemClicked = { },
            isChecked = true
        )
    }
}

@Preview
@Composable
private fun PreviewLinkRadioOption(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    AppThemePreview(previewConfig) {
        Column {
            SettingRadio(
                item = Settings.DarkMode.DarkModeDark,
                itemClicked = { },
                isChecked = false
            )
            SettingRadio(
                item = Settings.DarkMode.DarkModeLight,
                itemClicked = { },
                isChecked = true
            )
        }
    }
}

@Preview
@Composable
private fun PreviewLinkSwitch(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    AppThemePreview(previewConfig) {
        Column {
            SettingSwitch(
                item = Settings.DarkMode.DarkModeDark,
                itemClicked = { },
                isChecked = false
            )
            SettingSwitch(
                item = Settings.DarkMode.DarkModeLight,
                itemClicked = { },
                isChecked = true
            )
        }
    }
}

@Preview
@Composable
private fun PreviewLinkBeta(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    AppThemePreview(previewConfig) {
        SettingLink(
            item = Settings.Theme.ThemeMaterialYou.copy(isBeta = true),
            itemClicked = { }
        )
    }
}

@Preview
@Composable
private fun PreviewLinkDisabled(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    AppThemePreview(previewConfig) {
        SettingLink(
            item = Settings.Theme.ThemeMaterialYou.copy(isEnabled = false),
            itemClicked = { }
        )
    }
}