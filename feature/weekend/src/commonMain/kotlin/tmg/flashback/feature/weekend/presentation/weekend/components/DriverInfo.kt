package tmg.flashback.feature.weekend.presentation.weekend.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import flashback.domain.formula1.generated.resources.ic_fastest_lap
import flashback.feature.weekend.generated.resources.Res
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.ab_fastest_lap
import flashback.presentation.localisation.generated.resources.ab_result_fastest_lap
import flashback.presentation.localisation.generated.resources.ab_result_race_overview
import org.jetbrains.compose.resources.stringResource
import tmg.flashback.formula1.model.DriverEntry
import tmg.flashback.style.AppTheme
import tmg.flashback.style.badge.Badge
import tmg.flashback.style.badge.BadgeView
import tmg.flashback.style.text.TextBody2
import tmg.flashback.style.text.TextTitle
import tmg.flashback.ui.components.driver.DriverIcon
import tmg.flashback.ui.components.driver.DriverName
import tmg.flashback.ui.components.driver.driverIconSize
import tmg.flashback.ui.components.edgeBar
import tmg.flashback.ui.components.flag.Flag

private val positionWidth: Dp = 32.dp

@Composable
private fun Position(
    position: Int?
) {
    Box(modifier = Modifier.Companion.size(finishingPositionWidth)) {
        TextTitle(
            modifier = Modifier.align(Alignment.Center),
            text = position?.toString() ?: "-"
        )
    }
}

@Composable
fun DriverInfoWithIcon(
    entry: DriverEntry,
    position: Int,
    fastestLap: Boolean = false,
    driverClicked: (DriverEntry) -> Unit,
    modifier: Modifier = Modifier
) {
    val fastestLapString = if (fastestLap) {
        ". ${stringResource(string.ab_result_fastest_lap)}"
    } else {
        "."
    }

    val contentDescription = stringResource(
        string.ab_result_race_overview,
        position,
        entry.driver.name,
        entry.constructor.name
    ) + fastestLapString

    Row(modifier = modifier
        .edgeBar(entry.constructor.colour)
        .semantics(mergeDescendants = true) { }
        .clickable(
            enabled = true,
            onClick = { driverClicked(entry) }
        )
        .clearAndSetSemantics { this.contentDescription = contentDescription }
        .padding(vertical = AppTheme.dimens.xsmall)
    ) {
        Box(Modifier.size(positionWidth, driverIconSize)) {
            TextTitle(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(
                        horizontal = AppTheme.dimens.xsmall,
                        vertical = AppTheme.dimens.medium
                    ),
                bold = true,
                textAlign = TextAlign.Center,
                text = position.toString()
            )
        }
        DriverIcon(
            photoUrl = entry.driver.photoUrl,
//            number = entry.driver.number,
//            code = entry.driver.code,
            constructorColor = entry.constructor.colour,
            driverClicked = null
        )
        Column(
            modifier = Modifier
                .padding(
                    top = AppTheme.dimens.small,
                    start = AppTheme.dimens.small,
                    end = AppTheme.dimens.small,
                )
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            DriverName(
                firstName = entry.driver.firstName,
                lastName = entry.driver.lastName
            )
            TextBody2(text = entry.constructor.name)
            if (fastestLap) {
                BadgeView(
                    model = Badge(
                        label = stringResource(resource = string.ab_fastest_lap),
                        icon = flashback.domain.formula1.generated.resources.Res.drawable.ic_fastest_lap
                    ),
                    tintIcon = AppTheme.colors.onSurface
                )
            }
        }
    }
}

@Composable
fun DriverInfo(
    driver: DriverEntry,
    position: Int?,
    modifier: Modifier = Modifier,
    extraContent: (@Composable RowScope.() -> Unit)? = null
) {
    DriverInfo(
        driverFirstName = driver.driver.firstName,
        driverLastName = driver.driver.lastName,
        driverNationalityISO = driver.driver.nationalityISO,
        constructorName = driver.constructor.name,
        constructorColor = driver.constructor.colour,
        position = position,
        modifier = modifier,
        extraContent = extraContent
    )
}

@Composable
private fun DriverInfo(
    driverFirstName: String,
    driverLastName: String,
    driverNationalityISO: String,
    constructorName: String,
    constructorColor: Color,
    position: Int?,
    modifier: Modifier = Modifier,
    extraContent: (@Composable RowScope.() -> Unit)?
) {
    Row(modifier = modifier
        .edgeBar(constructorColor)
    ) {
        if (position != null) {
            TextTitle(
                modifier = Modifier
                    .width(positionWidth)
                    .padding(horizontal = AppTheme.dimens.xsmall)
                    .align(Alignment.CenterVertically),
                bold = true,
                textAlign = TextAlign.Center,
                text = position.toString()
            )
        } else {
            Spacer(Modifier.width(AppTheme.dimens.medium))
        }
        Column(modifier = Modifier
            .weight(1f)
            .padding(vertical = 3.dp)
        ) {
            Row(modifier = Modifier.height(IntrinsicSize.Min)) {
                DriverName(firstName = driverFirstName, lastName = driverLastName)
            }
            Spacer(Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Flag(
                    iso = driverNationalityISO,
                    modifier = Modifier
                        .size(16.dp)
                        .align(Alignment.CenterVertically),
                )
                Spacer(Modifier.width(AppTheme.dimens.small))
                extraContent?.let { content ->
                    content()
                    Spacer(Modifier.width(AppTheme.dimens.xsmall))
                }
                TextBody2(text = constructorName)
            }
        }
    }
}