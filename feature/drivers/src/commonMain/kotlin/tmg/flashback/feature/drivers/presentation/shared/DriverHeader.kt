package tmg.flashback.feature.drivers.presentation.shared

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.ab_back
import flashback.presentation.ui.generated.resources.Res
import flashback.presentation.ui.generated.resources.ic_back
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import tmg.flashback.formula1.model.Driver
import tmg.flashback.formula1.preview.preview
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider
import tmg.flashback.style.text.TextHeadline1
import tmg.flashback.ui.components.Refresh
import tmg.flashback.ui.components.driver.DriverIcon
import tmg.flashback.ui.components.season.PickerItem
import tmg.flashback.ui.components.season.Picker
import tmg.flashback.ui.components.season.string

private val backgroundHeight: Dp = 120.dp
private val iconSize: Dp = 120.dp
private val iconOffset: Dp = 48.dp

@Composable
fun DriverHeader(
    driverName: String,
    option: PickerItem,
    optionsToShow: List<PickerItem>,
    optionClicked: (PickerItem) -> Unit,
    driverImage: String?,
    backClicked: () -> Unit,
    showBack: Boolean,
    colour: Color,
    modifier: Modifier = Modifier,
    insetPadding: PaddingValues = PaddingValues(0.dp),
    overrideIcons: @Composable (RowScope.() -> Unit)? = null,
) {
    val direction = LocalLayoutDirection.current
    val insetTop = insetPadding.calculateTopPadding()
    val insetStart = insetPadding.calculateStartPadding(direction)
    val insetEnd = insetPadding.calculateStartPadding(direction)
    val colour = animateColorAsState(colour, label = "BackgroundColour")
    Box(modifier) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(backgroundHeight + insetTop)
            .background(colour.value))
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(backgroundHeight + insetTop)
            .background(
                brush = linearGradient(listOf(
                    AppTheme.colors.surfaceDim.copy(alpha = 0.9f),
                    AppTheme.colors.surfaceDim.copy(alpha = 0.3f)))
            ))

        if (showBack) {
            IconButton(
                onClick = backClicked,
                modifier = Modifier
                    .padding(top = insetTop, start = insetStart)
                    .align(Alignment.TopStart)
            ) {
                Icon(
                    painter = painterResource(resource = Res.drawable.ic_back),
                    contentDescription = stringResource(resource = string.ab_back),
                    tint = AppTheme.colors.onSurface
                )
            }
        }
        if (overrideIcons != null) {
            Row(modifier = Modifier
                .padding(top = insetTop, start = insetStart)
                .align(Alignment.TopEnd)
            ) {
                overrideIcons()
            }
        }

        DriverIcon(
            modifier = Modifier
                .padding(
                    top = iconOffset + insetTop,
                    start = AppTheme.dimens.medium,
                    end = AppTheme.dimens.medium + insetEnd,
                    bottom = AppTheme.dimens.medium
                )
                .align(Alignment.TopEnd),
            size = 100.dp,
            photoUrl = driverImage,
            constructorColor = colour.value
        )

        Column(
            modifier = Modifier
                .padding(
                    top = backgroundHeight + insetTop + AppTheme.dimens.medium,
                )
                .defaultMinSize(minHeight = iconOffset)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.xsmall)
        ) {
            Picker(
                option = option,
                optionsToShow = optionsToShow,
                optionUpdated = optionClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = insetStart + AppTheme.dimens.medium,
                        end = insetEnd + iconSize + AppTheme.dimens.medium
                    )
            )
            TextHeadline1(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = AppTheme.dimens.medium,
                    ),
                text = driverName
            )
        }
    }
}

@Preview
@Composable
private fun Preview(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    ApplicationThemePreview(previewConfig) {
        Column {
            val driver = Driver.preview()
            DriverHeader(
                option = PickerItem.Text("2020"),
                optionsToShow = listOf(),
                optionClicked = { },
                driverName = driver.name,
                driverImage = driver.photoUrl,
                backClicked = { },
                showBack = true,
                colour = AppTheme.colors.primary
            )
        }
    }
}

@Preview
@Composable
private fun PreviewAlt(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    ApplicationThemePreview(previewConfig) {
        Column {
            val driver = Driver.preview()
            DriverHeader(
                option = PickerItem.Text("2020"),
                optionsToShow = listOf(),
                optionClicked = { },
                driverName = driver.name,
                driverImage = driver.photoUrl,
                backClicked = { },
                showBack = false,
                colour = AppTheme.colors.primary
            )
        }
    }
}

@Preview
@Composable
private fun PreviewOverrideIcon(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    ApplicationThemePreview(previewConfig) {
        Column {
            val driver = Driver.preview()
            DriverHeader(
                option = PickerItem.Text("2020"),
                optionsToShow = listOf(),
                optionClicked = { },
                driverName = driver.name,
                driverImage = driver.photoUrl,
                backClicked = { },
                showBack = false,
                colour = AppTheme.colors.primary,
                overrideIcons = {
                    Refresh(onClick = { })
                }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewInset(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    ApplicationThemePreview(previewConfig) {
        val insetPadding = PaddingValues(
            top = 48.dp,
            start = 12.dp,
            end = 12.dp,
            bottom = 48.dp
        )
        Column {
            val driver = Driver.preview()
            DriverHeader(
                option = PickerItem.Text("2020"),
                optionsToShow = listOf(),
                optionClicked = { },
                driverName = driver.name,
                driverImage = driver.photoUrl,
                backClicked = { },
                showBack = true,
                insetPadding = insetPadding,
                colour = AppTheme.colors.primary
            )
        }
    }
}