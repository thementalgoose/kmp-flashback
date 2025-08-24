package tmg.flashback.feature.constructors.presentation.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.constructor_overview_stat_championship_standing
import flashback.presentation.localisation.generated.resources.constructor_overview_stat_points
import flashback.presentation.localisation.generated.resources.constructor_overview_stat_points_finishes
import flashback.presentation.localisation.generated.resources.constructor_overview_stat_qualifying_poles
import flashback.presentation.localisation.generated.resources.constructor_overview_stat_race_podiums
import flashback.presentation.localisation.generated.resources.constructor_overview_stat_race_wins
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import tmg.flashback.formula1.model.ConstructorHistorySeasonDriver
import tmg.flashback.infrastructure.extensions.ordinalAbbreviation
import tmg.flashback.infrastructure.extensions.roundToHalf
import tmg.flashback.style.AppTheme
import tmg.flashback.style.text.TextBody2
import tmg.flashback.style.text.TextTitle
import tmg.flashback.ui.components.driver.DriverIcon

private val driverImageSize = 64.dp

@Composable
internal fun ConstructorDriver(
    model: ConstructorHistorySeasonDriver,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier
        .height(IntrinsicSize.Min)
        .padding(
            horizontal = AppTheme.dimens.medium,
            vertical = AppTheme.dimens.small
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .height(IntrinsicSize.Max)
                .padding(start = driverImageSize / 2)
                .clip(RoundedCornerShape(AppTheme.dimens.radiusMedium))
                .background(AppTheme.colors.surfaceContainer3)
                .padding(
                    start = (driverImageSize / 2) + AppTheme.dimens.nsmall,
                    end = AppTheme.dimens.medium,
                    top = AppTheme.dimens.medium,
                    bottom = AppTheme.dimens.medium
                ),
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.xsmall)
        ) {
            TextTitle(
                text = model.driver.driver.name,
                bold = true
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 2.dp)
            )
            DriverStat(
                label = string.constructor_overview_stat_championship_standing,
                value = model.championshipStanding?.ordinalAbbreviation ?: "-"
            )
            DriverStat(
                label = string.constructor_overview_stat_race_wins,
                value = model.wins.toString()
            )
            DriverStat(
                label = string.constructor_overview_stat_race_podiums,
                value = model.podiums.toString()
            )
            DriverStat(
                label = string.constructor_overview_stat_qualifying_poles,
                value = model.polePosition.toString()
            )
            DriverStat(
                label = string.constructor_overview_stat_points,
                value = model.points.roundToHalf()
            )
            DriverStat(
                label = string.constructor_overview_stat_points_finishes,
                value = model.finishesInPoints.toString()
            )
        }
        DriverIcon(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = AppTheme.dimens.medium),
            photoUrl = model.driver.driver.photoUrl,
            size = driverImageSize,
            constructorColor = model.driver.constructor.colour
        )
    }
}


@Composable
private fun DriverStat(
    label: StringResource,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth()) {
        TextBody2(
            text = stringResource(label),
            modifier = Modifier.weight(1f)
        )
        TextBody2(
            text = value,
            bold = true
        )
    }
}