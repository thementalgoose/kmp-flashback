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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
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
import tmg.flashback.feature.drivers.presentation.stats.DriverSeasonFeed
import tmg.flashback.formula1.enums.RaceStatus
import tmg.flashback.formula1.enums.isStatusFinished
import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.DriverHistorySeasonRace
import tmg.flashback.formula1.model.RaceInfo
import tmg.flashback.formula1.preview.preview
import tmg.flashback.infrastructure.extensions.ordinalAbbreviation
import tmg.flashback.infrastructure.extensions.roundToHalf
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme
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
internal fun TeamHeader(
    entry: DriverSeasonFeed.TeamHeader,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier
        .fillMaxWidth()
        .edgeBar(entry.team.colour)
    ) {
        TextBody1(
            text = entry.team.name,
            modifier = Modifier.padding(
                horizontal = AppTheme.dimens.small,
                vertical = AppTheme.dimens.small
            )
        )
    }
}

@Composable
internal fun ResultRace(
    entry: DriverSeasonFeed.Weekend,
    clickResult: (RaceInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .edgeBar(entry.team.colour)
            .padding(
                vertical = AppTheme.dimens.xsmall
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = AppTheme.dimens.small
                )
                .clip(RoundedCornerShape(AppTheme.dimens.radiusSmall))
                .background(AppTheme.colors.surfaceContainer3)
                .clickable(onClick = { clickResult(entry.raceInfo) })
                .padding(
                    vertical = AppTheme.dimens.small
                )
        ) {
            RaceInfo(
                modifier = Modifier.weight(1f),
                raceName = entry.raceInfo.name,
                raceCountryISO = entry.raceInfo.circuit.countryISO,
                circuitName = entry.raceInfo.circuit.name,
                round = entry.raceInfo.round
            )
            if (entry.race != null) {
                Column(
                    Modifier.padding(end = AppTheme.dimens.small)
                ) {
                    AlignedResults(
                        qualified = entry.race.qualified,
                        finished = entry.race.finished,
                        points = entry.race.points,
                        status = entry.race.status,
                        highlight = true,
                    )
                }
            }
        }
        if (entry.sprint != null) {
            Sprint(
                sprint = entry.sprint,
                modifier = Modifier
                    .padding(end = AppTheme.dimens.medium)
                    .align(Alignment.End)
            )
        }
    }
}

@Composable
private fun Sprint(
    sprint: DriverHistorySeasonRace,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(
                bottomStart = AppTheme.dimens.radiusSmall,
                bottomEnd = AppTheme.dimens.radiusSmall
            ))
            .background(AppTheme.colors.surfaceContainer2),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.medium)
    ) {
        TextBody2(
            text = stringResource(string.nav_sprint),
            bold = true,
            modifier = Modifier.padding(
                horizontal = AppTheme.dimens.medium
            )
        )
        AlignedResults(
            qualified = sprint.qualified,
            finished = sprint.finished,
            points = sprint.points,
            status = sprint.status,
            highlight = false,
        )
    }
}

@Composable
private fun AlignedResults(
    qualified: Int?,
    finished: Int,
    points: Double,
    status: RaceStatus,
    highlight: Boolean = true,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
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
                if (highlight) {
                    TextBody1(
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        bold = false,
                        text = qualified?.ordinalAbbreviation ?: "-"
                    )
                } else {
                    TextBody2(
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        bold = false,
                        text = qualified?.ordinalAbbreviation ?: "-"
                    )
                }
                if (!status.isStatusFinished()) {
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
                if (highlight) {
                    TextBody1(
                        textColor = when (finished) {
                            1 -> AppTheme.colors.f1Podium1
                            2 -> AppTheme.colors.f1Podium2
                            3 -> AppTheme.colors.f1Podium3
                            else -> null
                        },
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        bold = true,
                        text = finished.ordinalAbbreviation
                    )
                } else {
                    TextBody2(
                        textColor = when (finished) {
                            1 -> AppTheme.colors.f1Podium1
                            2 -> AppTheme.colors.f1Podium2
                            3 -> AppTheme.colors.f1Podium3
                            else -> null
                        },
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        text = finished.ordinalAbbreviation
                    )
                }
                if (!status.isStatusFinished()) {
                    TextCaption(
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        text = status.label
                    )
                }
            }
        }
        Box(
            Modifier
                .width(resultColumnWidth)
                .padding(vertical = AppTheme.dimens.small)
        ) {
            if (highlight) {
                TextBody1(
                    text = points.roundToHalf(),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            } else {
                TextBody2(
                    text = points.roundToHalf(),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun RaceInfo(
    raceName: String,
    raceCountryISO: String,
    circuitName: String,
    round: Int?,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
    ) {
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
        }
    }
}

@PreviewTheme
@Composable
private fun Preview() {
    ApplicationThemePreview {
        Column {
            ResultHeader()
            TeamHeader(
                entry = fakeTeamHeader()
            )
            ResultRace(
                entry = fakeWeekendWithSprint(round = 1, finished = 1),
                clickResult = { }
            )
            ResultRace(
                entry = fakeWeekendWithSprint(round = 2, finished = 2, status = RaceStatus.WATER_PRESSURE),
                clickResult = { }
            )
            ResultRace(
                entry = fakeWeekendRace(round = 3, finished = 3),
                clickResult = { }
            )
            ResultRace(
                entry = fakeWeekendSprint(round = 4, finished = 4),
                clickResult = { }
            )
            ResultRace(
                entry = fakeWeekendSprint(round = 4, finished = 5),
                clickResult = { }
            )
        }
    }
}

private fun fakeTeamHeader(
    round: Int = 1,
    constructor: Constructor = Constructor.preview()
) = DriverSeasonFeed.TeamHeader(
    team = constructor,
    firstRound = round
)
private val fakeRace = DriverHistorySeasonRace(
    isSprint = false,
    status = RaceStatus.FINISHED,
    finished = 1,
    points = 25.0,
    qualified = 2,
    gridPos = 3,
    constructor = Constructor.preview(),
    raceInfo = RaceInfo.preview()
)
private fun fakeWeekendWithSprint(
    status: RaceStatus = RaceStatus.FINISHED,
    finished: Int = 1,
    round: Int = 1,
) = DriverSeasonFeed.Weekend(
    race = fakeRace.copy(
        isSprint = false,
        status = status,
        finished = finished,
        raceInfo = RaceInfo.preview(round = round)
    ),
    sprint = fakeRace.copy(
        isSprint = false,
        finished = finished,
        raceInfo = RaceInfo.preview(round = round)
    )
)
private fun fakeWeekendRace(
    status: RaceStatus = RaceStatus.FINISHED,
    finished: Int = 1,
    round: Int = 1,
) = DriverSeasonFeed.Weekend(
    race = fakeRace.copy(
        isSprint = false,
        status = status,
        finished = finished,
        raceInfo = RaceInfo.preview(round = round)
    ),
    sprint = null
)
private fun fakeWeekendSprint(
    status: RaceStatus = RaceStatus.FINISHED,
    finished: Int = 1,
    round: Int = 1,
) = DriverSeasonFeed.Weekend(
    race = null,
    sprint = fakeRace.copy(
        isSprint = false,
        status = status,
        finished = finished,
        raceInfo = RaceInfo.preview(round = round)
    )
)