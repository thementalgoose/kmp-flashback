package tmg.flashback.feature.weekend.presentation.data.sprint_race

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.ab_scored
import flashback.presentation.localisation.generated.resources.nav_race
import flashback.presentation.localisation.generated.resources.nav_sprint
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import tmg.flashback.feature.weekend.presentation.WeekendUiState
import tmg.flashback.feature.weekend.presentation.components.DataMissing
import tmg.flashback.feature.weekend.presentation.components.DataUnavailable
import tmg.flashback.feature.weekend.presentation.components.DriverInfoWithIcon
import tmg.flashback.feature.weekend.presentation.components.DriverTeamSwitcher
import tmg.flashback.feature.weekend.presentation.components.PointsBox
import tmg.flashback.feature.weekend.presentation.components.RaceHeader
import tmg.flashback.feature.weekend.presentation.components.Time
import tmg.flashback.feature.weekend.presentation.components.TypeHeader
import tmg.flashback.feature.weekend.presentation.components.finishingPositionWidth
import tmg.flashback.feature.weekend.presentation.components.status
import tmg.flashback.feature.weekend.presentation.components.timeWidth
import tmg.flashback.feature.weekend.presentation.data.ResultType
import tmg.flashback.formula1.constants.Formula1
import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.DriverEntry
import tmg.flashback.formula1.model.SprintRaceResult
import tmg.flashback.formula1.preview.preview
import tmg.flashback.infrastructure.extensions.roundToHalf
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider
import tmg.flashback.style.text.TextTitle
import tmg.flashback.ui.components.driver.DriverPoints
import tmg.flashback.ui.components.driver.driverIconSize
import tmg.flashback.ui.components.edgeBar

fun LazyListScope.addSprintRaceData(
    uiState: WeekendUiState.Data,
    selectResultType: (ResultType) -> Unit,
    keyPrefix: String = ""
) {
    item("sprint_race_label") {
        Column {
            TypeHeader(
                resource = string.nav_sprint
            )
            DriverTeamSwitcher(
                modifier = Modifier.padding(horizontal = AppTheme.dimens.medium),
                isDrivers = uiState.resultType == ResultType.DRIVERS,
                driversClicked = { selectResultType(ResultType.DRIVERS) },
                teamsClicked = { selectResultType(ResultType.CONSTRUCTORS) }
            )
        }
    }
    if (uiState.sprintRaceResults.isEmpty()) {
        item("sprint_race_not_found") {
            if (Formula1.currentSeasonYear == uiState.season) {
                DataMissing()
            } else {
                DataUnavailable()
            }
        }
    } else {
        item("sprint_race_header") {
            RaceHeader()
        }
    }
    items(uiState.sprintRaceResults, key = { "${keyPrefix}-${it.id}" }) {
        when (it) {
            is SprintRaceModel.ConstructorResult -> {
                SprintRaceConstructorResult(
                    model = it,
                    constructorClicked = { }
                )
            }
            is SprintRaceModel.DriverResult -> {
                SprintRaceDriverResult(
                    model = it.result,
                    driverClicked = { }
                )
            }
        }
    }
}

@Composable
internal fun SprintRaceDriverResult(
    model: SprintRaceResult,
    driverClicked: (DriverEntry) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.status(model.status, AppTheme.colors.surfaceContainer4)) {
        DriverInfoWithIcon(
            modifier = Modifier
                .weight(1f),
            entry = model.entry,
            position = model.finish,
            driverClicked = driverClicked
        )
        Box(
            modifier = Modifier
                .width(timeWidth)
                .padding(top = AppTheme.dimens.medium + 2.dp)
        ) {
            Time(
                modifier = Modifier
                    .align(Alignment.Center),
                lapTime = model.time,
                status = model.status
            )
        }
        PointsBox(
            points = model.points,
            colour = model.entry.constructor.colour,
        )
    }
}


@Composable
internal fun SprintRaceConstructorResult(
    model: SprintRaceModel.ConstructorResult,
    constructorClicked: (Constructor) -> Unit,
    modifier: Modifier = Modifier,
) {
    val contentDescription = stringResource(string.ab_scored, model.constructor.name, model.points.roundToHalf())
    @Suppress("SimplifiableCallChain")
    val drivers = model.drivers
        .map {
            stringResource(resource = string.ab_scored, it.first.name, it.second.roundToHalf())
        }
        .joinToString(separator = ",")

    Row(
        modifier = modifier
            .edgeBar(model.constructor.colour)
            .semantics(mergeDescendants = true) { }
            .clearAndSetSemantics {
                this.contentDescription = contentDescription + drivers
            }
            .clickable(onClick = {
                constructorClicked(model.constructor)
            }),
        verticalAlignment = Alignment.Top,
    ) {
        Box(Modifier.size(finishingPositionWidth, driverIconSize)) {
            TextTitle(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(
                        horizontal = AppTheme.dimens.xsmall,
                        vertical = AppTheme.dimens.medium
                    ),
                bold = true,
                textAlign = TextAlign.Center,
                text = model.position.toString()
            )
        }
        Row(modifier = Modifier
            .padding(
                top = AppTheme.dimens.small,
                bottom = AppTheme.dimens.small
            )
        ) {
            Column(modifier = Modifier.weight(1f)) {
                TextTitle(
                    text = model.constructor.name,
                    bold = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(2.dp))
                model.drivers.forEach { (driver, points) ->
                    DriverPoints(
                        name = driver.name,
                        nationality = driver.nationality,
                        nationalityISO = driver.nationalityISO,
                        points = points,
                    )
                }
            }
            Spacer(Modifier.width(AppTheme.dimens.small))
            PointsBox(
                points = model.points,
                colour = model.constructor.colour
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
            SprintRaceDriverResult(
                model = SprintRaceResult.preview(),
                driverClicked = { }
            )
            SprintRaceConstructorResult(
                model = SprintRaceModel.ConstructorResult.preview(),
                constructorClicked = { }
            )
        }
    }
}