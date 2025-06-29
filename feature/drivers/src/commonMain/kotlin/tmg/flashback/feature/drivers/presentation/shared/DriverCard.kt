package tmg.flashback.feature.drivers.presentation.shared

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import flashback.domain.formula1.generated.resources.ic_driver_birthday
import flashback.domain.formula1.generated.resources.ic_driver_code
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.ab_back
import flashback.presentation.ui.generated.resources.Res
import flashback.presentation.ui.generated.resources.ic_back
import flashback.presentation.ui.generated.resources.ic_circle
import kotlinx.datetime.format
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.Driver
import tmg.flashback.formula1.preview.preview
import tmg.flashback.infrastructure.datetime.dateFormatDMMMYYYY
import tmg.flashback.style.AppTheme
import tmg.flashback.style.AppThemePreview
import tmg.flashback.style.badge.Badge
import tmg.flashback.style.badge.BadgeView
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider
import tmg.flashback.style.text.TextBody2
import tmg.flashback.style.text.TextHeadline2
import tmg.flashback.ui.components.driver.DriverIcon
import tmg.flashback.ui.components.flag.getFlagVector

private val backgroundHeight: Dp = 100.dp
private val iconSize: Dp = 120.dp
private val iconOffset: Dp = 24.dp

@Composable
fun DriverCard(
    label: String,
    driverImage: String?,
    backClicked: () -> Unit,
    showBack: Boolean,
    insetPadding: PaddingValues = PaddingValues(0.dp),
    modifier: Modifier = Modifier,
    colour: Color,
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
        
        Column(modifier = Modifier
            .padding(
                top = backgroundHeight + insetTop,
                end = iconSize + (AppTheme.dimens.medium * 2) + insetEnd,
                start = insetStart
            )
            .defaultMinSize(minHeight = iconOffset)
            .fillMaxWidth()
        ) {
            TextHeadline2(
                modifier = Modifier.padding(
                    top = AppTheme.dimens.medium,
                    start = AppTheme.dimens.medium,
                    end = AppTheme.dimens.medium,
                ),
                text = label
            )
        }
    }
}

@Composable
fun DriverBadges(
    driver: Driver,
    constructors: List<Constructor>
) {
    FlowRow(
        modifier = Modifier
            .padding(
                horizontal = AppTheme.dimens.medium,
                vertical = AppTheme.dimens.small
            )
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.small),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.small)
    ) {

        val flagVector = getFlagVector(driver.nationalityISO)
        NationalityBadge(
            label = driver.nationality,
            imageVector = flagVector,
        )

        val birthday = driver.dateOfBirth.format(dateFormatDMMMYYYY)
        BadgeView(model = Badge(label = birthday, icon = flashback.domain.formula1.generated.resources.Res.drawable.ic_driver_birthday))

        if (driver.code != null && driver.number != null) {
            BadgeView(
                model = Badge(
                    label = "${driver.code} ${driver.number}",
                    icon = flashback.domain.formula1.generated.resources.Res.drawable.ic_driver_code
                )
            )
        }

        constructors.forEach {
            BadgeView(
                model = Badge(
                    label = it.name,
                    icon = Res.drawable.ic_circle
                ),
                tintIcon = it.colour
            )
        }
    }
}



@Composable
private fun NationalityBadge(
    label: String,
    imageVector: ImageVector?,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier
        .clip(RoundedCornerShape(AppTheme.dimens.radiusSmall))
        .background(AppTheme.colors.surfaceContainer2)
        .border(
            1.dp,
            color = AppTheme.colors.outline.copy(alpha = 0.35f),
            shape = RoundedCornerShape(AppTheme.dimens.radiusSmall)
        )
        .padding(
            horizontal = AppTheme.dimens.small,
            vertical = AppTheme.dimens.xsmall
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (imageVector != null) {
            Image(
                imageVector = imageVector,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
            Spacer(Modifier.width(6.dp))
        }
        TextBody2(
            text = label,
            bold = true
        )
    }
}


@Preview
@Composable
private fun Preview(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    AppThemePreview(previewConfig) {
        Column {
            val driver = Driver.preview()
            DriverCard(
                label = driver.name,
                driverImage = driver.photoUrl,
                backClicked = { },
                showBack = true,
                colour = AppTheme.colors.primary
            )
            DriverBadges(
                driver = Driver.preview(),
                constructors = listOf(Constructor.preview())
            )
        }
    }
}

@Preview
@Composable
private fun PreviewAlt(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    AppThemePreview(previewConfig) {
        Column {
            val driver = Driver.preview()
            DriverCard(
                label = "${driver.name}\n2020",
                driverImage = driver.photoUrl,
                backClicked = { },
                showBack = false,
                colour = AppTheme.colors.primary
            )
            DriverBadges(
                driver = Driver.preview(),
                constructors = listOf(Constructor.preview())
            )
        }
    }
}

@Preview
@Composable
private fun PreviewInset(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    AppThemePreview(previewConfig) {
        val insetPadding = PaddingValues(
            top = 48.dp,
            start = 12.dp,
            end = 12.dp,
            bottom = 48.dp
        )
        Column {
            val driver = Driver.preview()
            DriverCard(
                label = "${driver.name}\n2020",
                driverImage = driver.photoUrl,
                backClicked = { },
                showBack = true,
                insetPadding = insetPadding,
                colour = AppTheme.colors.primary
            )
            DriverBadges(
                driver = Driver.preview(),
                constructors = listOf(Constructor.preview())
            )
        }
    }
}