package tmg.flashback.feature.drivers.presentation.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import flashback.domain.formula1.generated.resources.Res
import flashback.domain.formula1.generated.resources.ic_race_points
import flashback.domain.formula1.generated.resources.ic_status_finished
import flashback.domain.formula1.generated.resources.ic_status_results_qualifying
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.nav_sprint
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import tmg.flashback.feature.drivers.presentation.season.DriverSeasonRace
import tmg.flashback.formula1.enums.RaceStatus
import tmg.flashback.formula1.enums.isStatusFinished
import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.DriverHistorySeasonRace
import tmg.flashback.formula1.model.RaceInfo
import tmg.flashback.formula1.preview.preview
import tmg.flashback.infrastructure.extensions.ordinalAbbreviation
import tmg.flashback.infrastructure.extensions.roundToHalf
import tmg.flashback.style.AppTheme
import tmg.flashback.style.AppThemePreview
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider
import tmg.flashback.style.text.TextBody1
import tmg.flashback.style.text.TextBody2
import tmg.flashback.style.text.TextCaption
import tmg.flashback.style.text.TextTitle
import tmg.flashback.ui.components.edgeBar
import tmg.flashback.ui.components.flag.Flag

private val resultColumnFlexBorder: Dp = 300.dp
private val resultColumnWidth: Dp = 50.dp
private val headerImageSize: Dp = 120.dp

@Composable
internal fun ResultHeader(
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier
        .padding(horizontal = AppTheme.dimens.medium)) {
        Box(Modifier.weight(1f))
        Box(
            Modifier
                .width(resultColumnWidth)
                .padding(vertical = AppTheme.dimens.xsmall)
        ) {
            Icon(
                modifier = Modifier.align(Alignment.Center),
                painter = painterResource(resource = Res.drawable.ic_status_results_qualifying),
                contentDescription = null,
                tint = AppTheme.colors.onSurface
            )
        }
        Box(
            Modifier
                .width(resultColumnWidth)
                .padding(vertical = AppTheme.dimens.xsmall)
        ) {
            Icon(
                modifier = Modifier.align(Alignment.Center),
                painter = painterResource(resource = Res.drawable.ic_status_finished),
                contentDescription = null,
                tint = AppTheme.colors.onSurface
            )
        }
        Box(
            Modifier
                .width(resultColumnWidth)
                .padding(vertical = AppTheme.dimens.xsmall)
        ) {
            Icon(
                modifier = Modifier.align(Alignment.Center),
                painter = painterResource(resource = Res.drawable.ic_race_points),
                contentDescription = null,
                tint = AppTheme.colors.onSurface
            )
        }
    }
}

@Composable
internal fun ResultRace(
    model: DriverSeasonRace,
    clickResult: (DriverSeasonRace) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .alpha(
                when (model.result.status.isStatusFinished()) {
                    true -> 1.0f
                    false -> 0.7f
                }
            )
            .padding(
                horizontal = AppTheme.dimens.small,
                vertical = AppTheme.dimens.xsmall
            )
            .clickable(onClick = { clickResult(model) })
            .clip(RoundedCornerShape(AppTheme.dimens.radiusSmall))
            .background(AppTheme.colors.surfaceContainer3)
            .padding(AppTheme.dimens.small)
    ) {
        Column(
            Modifier.weight(1f)
        ) {
            if (model.result.isSprint) {
                SprintInfo(
                    raceName = model.result.raceInfo.name,
                    raceCountryISO = model.result.raceInfo.circuit.countryISO,
                    constructorColor = model.result.constructor.colour,
                    circuitName = model.result.raceInfo.circuit.name,
                    country = model.result.raceInfo.circuit.country,
                    constructor = model.result.constructor,
                    round = model.result.raceInfo.round,
                    showConstructorLabel = true
                )
            } else {
                RaceInfo(
                    raceName = model.result.raceInfo.name,
                    raceCountryISO = model.result.raceInfo.circuit.countryISO,
                    constructorColor = model.result.constructor.colour,
                    circuitName = model.result.raceInfo.circuit.name,
                    constructor = model.result.constructor,
                    round = model.result.raceInfo.round,
                    showConstructorLabel = true
                )
            }
        }
        Box(
            Modifier
                .width(resultColumnWidth)
                .padding(vertical = AppTheme.dimens.xsmall)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = AppTheme.dimens.xsmall)
            ) {
                TextBody2(
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    bold = false,
                    text = model.result.qualified?.ordinalAbbreviation ?: "-"
                )
                if (!model.result.status.isStatusFinished()) {
                    TextCaption(
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        text = ""
                    )
                }
            }
        }
        Box(
            Modifier
                .width(resultColumnWidth)
                .padding(vertical = AppTheme.dimens.xsmall)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = AppTheme.dimens.xsmall)
            ) {
                if (model.result.isSprint) {
                    TextBody2(
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        bold = true,
                        text = model.result.finished.ordinalAbbreviation
                    )
                } else {
                    TextBody1(
                        textColor = when (model.result.finished == 1) {
                            true -> AppTheme.colors.f1Championship
                            false -> null
                        },
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        bold = true,
                        text = model.result.finished.ordinalAbbreviation
                    )
                }
                if (!model.result.status.isStatusFinished()) {
                    TextCaption(
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        text = model.result.status.label
                    )
                }
            }
        }
        Box(
            Modifier
                .width(resultColumnWidth)
                .padding(vertical = AppTheme.dimens.small)
        ) {
            TextBody1(
                text = model.result.points.roundToHalf(),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun RaceInfo(
    raceName: String,
    raceCountryISO: String,
    circuitName: String,
    constructorColor: Color,
    constructor: Constructor,
    showConstructorLabel: Boolean,
    round: Int?,
    modifier: Modifier = Modifier,
) {

    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(6.dp)
                .background(constructorColor)
        )
        if (round != null) {
            TextTitle(
                modifier = Modifier
                    .width(36.dp)
                    .padding(horizontal = AppTheme.dimens.xsmall)
                    .align(Alignment.CenterVertically),
                bold = true,
                textAlign = TextAlign.Center,
                text = round.toString()
            )
        } else {
            Spacer(Modifier.width(AppTheme.dimens.medium - 6.dp))
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(
                    top = 3.dp
                )
        ) {
            Row(modifier = Modifier.height(IntrinsicSize.Min)) {
                TextTitle(
                    text = raceName,
                    bold = true
                )
            }
            Spacer(Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Flag(
                    iso = raceCountryISO,
                    modifier = Modifier
                        .size(16.dp)
                        .align(Alignment.CenterVertically),
                )
                Spacer(Modifier.width(AppTheme.dimens.small))
                TextBody2(text = circuitName)
            }

            if (showConstructorLabel) {
                Spacer(Modifier.height(AppTheme.dimens.xsmall))
                ConstructorBadge(constructor)
            }
        }
    }
}

@Composable
private fun SprintInfo(
    raceName: String,
    raceCountryISO: String,
    circuitName: String,
    country: String,
    constructorColor: Color,
    constructor: Constructor,
    showConstructorLabel: Boolean,
    round: Int?,
    modifier: Modifier = Modifier,
) {

    Row(
        modifier = modifier
            .edgeBar(constructorColor)
    ) {
        if (round != null) {
            TextTitle(
                modifier = Modifier
                    .width(36.dp)
                    .padding(horizontal = AppTheme.dimens.xsmall)
                    .align(Alignment.CenterVertically),
                bold = true,
                textAlign = TextAlign.Center,
                text = ""
            )
        } else {
            Spacer(Modifier.width(AppTheme.dimens.medium - 6.dp))
        }
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextTitle(text = stringResource(string.nav_sprint))
            }
            if (showConstructorLabel) {
                Spacer(Modifier.height(AppTheme.dimens.xsmall))
                ConstructorBadge(constructor)
            }
        }
    }
}


@Preview
@Composable
private fun Preview(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    AppThemePreview(previewConfig) {
        Column {
            ResultHeader()
            ResultRace(
                model = fakeModel,
                clickResult = { }
            )
            ResultRace(
                model = fakeModel.copy(result = fakeModel.result.copy(isSprint = true)),
                clickResult = { }
            )
            ResultRace(
                model = fakeModel,
                clickResult = { }
            )
        }
    }
}

private val fakeModel = DriverSeasonRace(
    result = DriverHistorySeasonRace(
        isSprint = false,
        status = RaceStatus.FINISHED,
        finished = 1,
        points = 25.0,
        qualified = 2,
        gridPos = 3,
        constructor = Constructor.preview(),
        raceInfo = RaceInfo.preview()
    )
)